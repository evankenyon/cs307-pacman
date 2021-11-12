package ooga.controller.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import ooga.model.util.Position;
import org.apache.commons.io.IOUtils;
// Decided to use this library after reading article from
// https://coderolls.com/parse-json-in-java/
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser implements JsonParserInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      JsonParser.class.getPackageName() + ".resources.";
  private static final String REQUIRED_KEYS_FILENAME = "RequiredKeys";
  private static final String REQUIRED_VALUES_FILENAME = "RequiredValues";
  private List<Consumer<Map<String, List<Position>>>> wallMapConsumers;
  private List<Consumer<String>> playerConsumers;
  private List<Consumer<Map<String, Boolean>>> pelletsConsumers;
  private Map<String, List<Position>> wallMap;
  private Map<String, Boolean> pelletInfo;
  private String player;

  private ResourceBundle requiredKeys;

  public JsonParser() {
    wallMap = new HashMap<>();
    pelletInfo = new HashMap<>();
    wallMapConsumers = new ArrayList<>();
    playerConsumers = new ArrayList<>();
    pelletsConsumers = new ArrayList<>();
    requiredKeys = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, REQUIRED_KEYS_FILENAME));
  }

  @Override
  public void uploadFile(File file) throws IOException, InputMismatchException {
    // Borrowed code to read in a json file from
    // https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file

    InputStream is = this.getClass().getClassLoader()
        .getResourceAsStream(file.getPath());
    if (is == null) {
      throw new FileNotFoundException();
    }
    String jsonTxt = IOUtils.toString(is, "UTF-8");
    JSONObject json = new JSONObject(jsonTxt);
    checkForRequiredKeys(json.keySet());
    setupPlayer(json.getString("Player"));
    setupPelletInfo(json.getJSONArray("RequiredPellets"), json.getJSONArray("OptionalPellets"));
    setupWallMap(json.getJSONArray("WallMap"));
    checkWallMapForRequirements();
    updateConsumers();
  }

  @Override
  public void addWallMapConsumer(Consumer<Map<String, List<Position>>> consumer) {
    wallMapConsumers.add(consumer);
  }

  @Override
  public void addPlayerConsumer(Consumer<String> consumer) {
    playerConsumers.add(consumer);
  }

  @Override
  public void addPelletsConsumer(Consumer<Map<String, Boolean>> consumer) {
    pelletsConsumers.add(consumer);
  }

  private void checkForRequiredKeys(Set<String> keySet) throws InputMismatchException {
    List<String> requiredKeysList = List.of(requiredKeys.getString("RequiredKeys").split(","));
    int keysRequired = requiredKeysList.size();
    int numKeys = keySet.size();
    if (keysRequired != numKeys) {
      throw new InputMismatchException("The uploaded file does not have enough keys");
    }
    for (String key : keySet) {
      if (!requiredKeysList.contains(key)) {
        throw new InputMismatchException(
            String.format("Unexpected key %s was found in json file", key));
      }
    }
  }

  private void setupPlayer(String player) {
    this.player = player;
  }

  private void setupPelletInfo(JSONArray requiredPellets, JSONArray optionalPellets) {
    requiredPellets.iterator().forEachRemaining(pellet -> pelletInfo.put((String) pellet, true));
    optionalPellets.iterator().forEachRemaining(pellet -> pelletInfo.put((String) pellet, false));
  }

  private void setupWallMap(JSONArray wallMapArr) {
    for (int row = 0; row < wallMapArr.length(); row++) {
      for (int col = 0; col < wallMapArr.getJSONArray(row).length(); col++) {
        wallMap.putIfAbsent(wallMapArr.getJSONArray(row).getString(col), new ArrayList<>());
        wallMap.get(wallMapArr.getJSONArray(row).getString(col)).add(new Position(col, row));
      }
    }
  }

  private void checkWallMapForRequirements() throws InputMismatchException {
    checkForOnlyOnePlayer();
    checkForAllRequiredPellets();
    checkForOneOfEachGhost();
  }

  private void checkForOnlyOnePlayer() throws InputMismatchException {
    if (wallMap.get(player).size() > 1) {
      throw new InputMismatchException("You should only have one player in your game.");
    }
  }

  private void checkForAllRequiredPellets() throws InputMismatchException {
    for (String pellet : pelletInfo.keySet()) {
      if (pelletInfo.get(pellet)) {
        if (!wallMap.containsKey(pellet)) {
          throw new InputMismatchException(
              String.format("Game does not contain any of the %s required pellet", pellet));
        }
      }
    }
  }

  private void checkForOneOfEachGhost() throws InputMismatchException {
    ResourceBundle requiredValues = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, REQUIRED_VALUES_FILENAME));
    List<String> ghosts = List.of(requiredValues.getString("Ghosts").split(","));
    for (String key : wallMap.keySet()) {
      if (ghosts.contains(key)) {
        if (wallMap.get(key).size() > 1) {
          throw new InputMismatchException("Game cannot have more than one of each type of ghost");
        }
      }
    }
  }

  private void updateConsumers() {
    for (Consumer<Map<String, Boolean>> consumer : pelletsConsumers) {
      consumer.accept(pelletInfo);
    }
    for (Consumer<String> consumer : playerConsumers) {
      consumer.accept(player);
    }
    for (Consumer<Map<String, List<Position>>> consumer : wallMapConsumers) {
      consumer.accept(wallMap);
    }
  }
}
