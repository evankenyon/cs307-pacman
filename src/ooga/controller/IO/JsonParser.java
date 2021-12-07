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
import ooga.model.GameData;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Purpose: This class's purpose is to do high level parsing of starting config files that are first
 * converted into a JSONObject (either from a local file or from the database)
 * Dependencies: java-json, Position, GameData, JSONObjectParser, Consumer, Set, ResourceBundle,
 * Map, List, InputMismatchException, HashMap, ArrayList, IOException, File
 * Example: Instantiate this class in a Controller to take in a JSONObject that represents starting
 * config data (either from a local file or a database file) in order to instantiate a GameEngine
 * which represents the backend for a Pacman game
 *
 * @author Evan Kenyon
 */
public class JsonParser implements JsonParserInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      JsonParser.class.getPackageName() + ".resources.";
  private static final String REQUIRED_KEYS_FILENAME = "RequiredKeys";
  private static final String REQUIRED_VALUES_FILENAME = "RequiredValues";
  private static final String EXCEPTION_MESSAGES_FILENAME = "Exceptions";
  private static final String MAGIC_VALUES_FILENAME = "JsonParserMagicValues";

  private int numLives;
  private int playerScore;

  private Map<String, List<Position>> wallMap;
  private int mapCols;
  private int mapRows;
  private Map<String, Boolean> pelletInfo;
  private String player;
  private List<Consumer<GameData>> vanillaGameDataConsumers;

  private ResourceBundle requiredKeys;
  private ResourceBundle exceptionMessages;
  private ResourceBundle magicValues;

  /**
   * Purpose: Instantiate a JsonParser class by instantiating data structures as needed
   */
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

  /**
   * Purpose: Parse a JSONObject into a GameData object containing a Wall map, a player, the number
   * of lives, the starting score, the information about which pellets are required and which are
   * not, the number of columns, and the number of rows. Updates the GameData consumers with
   * created GameData object.
   * @param json the JSONObject which represents a starting config file
   * @throws InputMismatchException thrown if any keys or starting data in the starting config json
   * are incorrect, with specific error messages based on the issue
   */
  @Override
  public void parseJSON(JSONObject json) throws InputMismatchException {
    reset();
    checkForRequiredKeys(json.keySet());
    setupPlayer(json.getString(magicValues.getString("PlayerKey")));
    setupPelletInfo(json.getJSONArray(magicValues.getString("RequiredPelletsKey")),
        json.getJSONArray(magicValues.getString("OptionalPelletsKey")));
    setupNumLives(json.getInt(magicValues.getString("NumberOfLivesKey")));
    setupWallMap(json.getJSONArray(magicValues.getString("WallMapKey")));
    checkWallMapForRequirements();
    updateConsumers(
        new GameData(wallMap, player, playerScore, numLives, pelletInfo, mapCols, mapRows));
  }

  @Deprecated
  @Override
  public void uploadFile(File file) throws IOException, InputMismatchException, JSONException {
    reset();
    JSONObject json = JSONObjectParser.parseJSONObject(file);
    checkForRequiredKeys(json.keySet());
    setupPlayer(json.getString(magicValues.getString("PlayerKey")));
    setupPelletInfo(json.getJSONArray(magicValues.getString("RequiredPelletsKey")),
        json.getJSONArray(magicValues.getString("OptionalPelletsKey")));
    setupNumLives(json.getInt(magicValues.getString("NumberOfLivesKey")));
    setupPlayerScore(json.getInt(magicValues.getString("PlayerScoreKey")));
    setupWallMap(json.getJSONArray(magicValues.getString("WallMapKey")));
    checkWallMapForRequirements();
    updateConsumers(
        new GameData(wallMap, player, playerScore, numLives, pelletInfo, mapCols, mapRows));
  }

  /**
   * Purpose: Add a consumer for the GameData object which is created by parseJSON
   * @param consumer consumer for the GameData object
   */
  @Override
  public void addVanillaGameDataConsumer(Consumer<GameData> consumer) {
    vanillaGameDataConsumers.add(consumer);
  }

  /**
   * Purpose: Get the number of rows in the starting config
   * @return the number of rows in the starting config
   */
  public int getRows() {
    return mapRows;
  }

  /**
   * Purpose: Get the number of columns in the starting config
   * Assumptions: the number of columns in the starting config
   * @return
   */
  public int getCols() {
    return mapCols;
  }

  private void checkForRequiredKeys(Set<String> keySet) throws InputMismatchException {
    List<String> requiredKeysList = List.of(
        requiredKeys.getString("RequiredKeys").split(magicValues.getString("Delimiter")));
    int keysRequired = requiredKeysList.size();
    int numKeys = keySet.size();
    for (String key : keySet) {
      if (!requiredKeysList.contains(key)) {
        throw new InputMismatchException(
            String.format(exceptionMessages.getString("UnexpectedKey"), key));
      }
    }
    if (keysRequired != numKeys) {
      throw new InputMismatchException(exceptionMessages.getString("NotEnoughKeys"));
    }
  }

  private void setupPlayer(String player) {
    this.player = player;
  }

  private void setupNumLives(int numLives) {
    this.numLives = numLives;
  }

  private void setupPlayerScore(int score) {
    this.playerScore = score;
  }


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
    List<String> ghosts = List.of(
        requiredValues.getString("Ghosts").split(magicValues.getString("Delimiter")));
    for (String key : wallMap.keySet()) {
      if (ghosts.contains(key)) {
        if (wallMap.get(key).size() > 1) {
          throw new InputMismatchException(exceptionMessages.getString("DuplicateGhosts"));
        }
      }
    }
  }

  private void updateConsumers(GameData vanillaGameData) {
    for (Consumer<GameData> consumer : vanillaGameDataConsumers) {
      consumer.accept(vanillaGameData);
    }
  }

  private void reset() {
    wallMap = new HashMap<>();
    pelletInfo = new HashMap<>();
  }
}
