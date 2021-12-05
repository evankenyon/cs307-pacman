package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.Data;
import ooga.model.util.Position;
// Decided to use this library after reading article from
// https://coderolls.com/parse-json-in-java/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser implements JsonParserInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      JsonParser.class.getPackageName() + ".resources.";
  private static final String REQUIRED_KEYS_FILENAME = "RequiredKeys";
  private static final String REQUIRED_VALUES_FILENAME = "RequiredValues";
  private static final String EXCEPTION_MESSAGES_FILENAME = "Exceptions";
  private static final String MAGIC_VALUES_FILENAME = "JsonParserMagicValues";

  private int numLives;

  private Map<String, List<Position>> wallMap;
  private int mapCols;
  private int mapRows;
  private Map<String, Boolean> pelletInfo;
  private String player;
  private List<Consumer<Data>> vanillaGameDataConsumers;

  private ResourceBundle requiredKeys;
  private ResourceBundle exceptionMessages;
  private ResourceBundle magicValues;

  public JsonParser() {
    reset();
    vanillaGameDataConsumers = new ArrayList<>();
    requiredKeys = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, REQUIRED_KEYS_FILENAME));
    exceptionMessages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, EXCEPTION_MESSAGES_FILENAME));
    magicValues = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, MAGIC_VALUES_FILENAME));
  }

  private void reset() {
    wallMap = new HashMap<>();
    pelletInfo = new HashMap<>();
  }


  @Override
  public void parseJSON(JSONObject json) throws InputMismatchException, JSONException {
    reset();
    checkForRequiredKeys(json.keySet());
    setupPlayer(json.getString(magicValues.getString("PlayerKey")));
    setupPelletInfo(json.getJSONArray(magicValues.getString("RequiredPelletsKey")), json.getJSONArray(magicValues.getString("OptionalPelletsKey")));
    setupNumLives(json.getInt(magicValues.getString("NumberOfLivesKey")));
    setupWallMap(json.getJSONArray(magicValues.getString("WallMapKey")));
    checkWallMapForRequirements();
    updateConsumers(new Data(wallMap, player, numLives, pelletInfo, mapCols, mapRows));
  }

  @Deprecated
  @Override
  public void uploadFile(File file) throws IOException, InputMismatchException, JSONException {
    reset();
    JSONObject json = JSONObjectParser.parseJSONObject(file);
    checkForRequiredKeys(json.keySet());
    setupPlayer(json.getString(magicValues.getString("PlayerKey")));
    setupPelletInfo(json.getJSONArray(magicValues.getString("RequiredPelletsKey")), json.getJSONArray(magicValues.getString("OptionalPelletsKey")));
    setupNumLives(json.getInt(magicValues.getString("NumberOfLivesKey")));
    setupWallMap(json.getJSONArray(magicValues.getString("WallMapKey")));
    checkWallMapForRequirements();
    updateConsumers(new Data(wallMap, player, numLives, pelletInfo, mapCols, mapRows));
  }

  @Override
  public void addVanillaGameDataConsumer(Consumer<Data> consumer) {
    vanillaGameDataConsumers.add(consumer);
  }

  public int getRows() {
    return mapRows;
  }

  public int getCols() {
    return mapCols;
  }

  private void checkForRequiredKeys(Set<String> keySet) throws InputMismatchException {
    List<String> requiredKeysList = List.of(requiredKeys.getString("RequiredKeys").split(magicValues.getString("Delimiter")));
    int keysRequired = requiredKeysList.size();
    int numKeys = keySet.size();
    if (keysRequired != numKeys) {
      throw new InputMismatchException(exceptionMessages.getString("NotEnoughKeys"));
    }
    for (String key : keySet) {
      if (!requiredKeysList.contains(key)) {
        throw new InputMismatchException(
            String.format(exceptionMessages.getString("UnexpectedKey"), key));
      }
    }
  }

  private void setupPlayer(String player) {
    this.player = player;
  }

  private void setupNumLives(int numLives) {this.numLives = numLives;}


  private void setupPelletInfo(JSONArray requiredPellets, JSONArray optionalPellets) {
    requiredPellets.iterator().forEachRemaining(pellet -> pelletInfo.put((String) pellet, true));
    optionalPellets.iterator().forEachRemaining(pellet -> pelletInfo.put((String) pellet, false));
  }

  //check that all rows are same length and all columns are same length
  private void setupWallMap(JSONArray wallMapArr) {
    int expectedNumRows = wallMapArr.length();
    int expectedNumCols = wallMapArr.getJSONArray(0).length(); //count as magic #?
    for (int row = 0; row < expectedNumRows; row++) {
      for (int col = 0; col < expectedNumCols; col++) {
        wallMap.putIfAbsent(wallMapArr.getJSONArray(row).getString(col), new ArrayList<>());
        wallMap.get(wallMapArr.getJSONArray(row).getString(col)).add(new Position(col, row));
      }
    }
    mapCols = expectedNumCols;
    mapRows = expectedNumRows;
  }


  private void checkWallMapForRequirements() throws InputMismatchException {
    checkForOnlyOnePlayer();
    checkForAllRequiredPellets();
    checkForOneOfEachGhost();
  }

  private void checkForOnlyOnePlayer() throws InputMismatchException {
    if (wallMap.get(player).size() > 1) {
      throw new InputMismatchException(exceptionMessages.getString("MultiplePlayers"));
    }
  }

  private void checkForAllRequiredPellets() throws InputMismatchException {
    for (String pellet : pelletInfo.keySet()) {
      if (pelletInfo.get(pellet)) {
        if (!wallMap.containsKey(pellet)) {
          throw new InputMismatchException(
              String.format(exceptionMessages.getString("MissingRequiredPellet"), pellet));
        }
      }
    }
  }

  private void checkForOneOfEachGhost() throws InputMismatchException {
    ResourceBundle requiredValues = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, REQUIRED_VALUES_FILENAME));
    List<String> ghosts = List.of(requiredValues.getString("Ghosts").split(magicValues.getString("Delimiter")));
    for (String key : wallMap.keySet()) {
      if (ghosts.contains(key)) {
        if (wallMap.get(key).size() > 1) {
          throw new InputMismatchException(exceptionMessages.getString("DuplicateGhosts"));
        }
      }
    }
  }

  private void updateConsumers(Data vanillaGameData) {
    for (Consumer<Data> consumer : vanillaGameDataConsumers) {
      consumer.accept(vanillaGameData);
    }
  }

}
