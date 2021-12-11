package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.util.Position;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonParserTest {

  private JsonParser jsonParser;

  @BeforeEach
  void setUp() {
    jsonParser = new JsonParser();
  }

  @Test
  void properWallMapCreation() throws IOException {
    Map<String, List<Position>> expected = properWallMapCreationSetup();
  }

  private void compareWallMaps(Map<String, List<Position>> actual,
      Map<String, List<Position>> expected) {
    for (String key : actual.keySet()) {
      for (Position position : actual.get(key)) {
        Assertions.assertTrue(containsPosition(position, expected.get(key)));
      }
    }
  }

  private boolean containsPosition(Position a, List<Position> list) {
    for (Position position : list) {
      if (a.getCoords()[0] == position.getCoords()[0]
          && a.getCoords()[1] == position.getCoords()[1]) {
        return true;
      }
    }
    return false;
  }

  @Test
  void properPlayerCreation() throws IOException {
    String expected = "Pacman";
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> comparePlayers(vanillaGameDataInterface.player(), expected));
    jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/basicWallMap.json")));
  }

  private void comparePlayers(String actual, String expected) {
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void properRequirePelletCreation() throws IOException {
    Map<String, Boolean> expected = new HashMap<>();
    expected.put("Dot", Boolean.TRUE);
    expected.put("Fruit", Boolean.FALSE);
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> comparePelletMaps(vanillaGameDataInterface.pelletInfo(),
            expected));
    jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/basicWallMap.json")));
  }

  private void comparePelletMaps(Map<String, Boolean> actual, Map<String, Boolean> expected) {
    for (String pellet : actual.keySet()) {
      Assertions.assertEquals(actual.get(pellet), expected.get(pellet));
    }
  }

  @Test
  void parseJSONObjectNotAllRequiredKeys() {
    try {
      jsonParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/notEnoughKeys.json")));
    } catch (Exception e) {
      Assertions.assertEquals("The uploaded file does not have enough keys", e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/notEnoughKeys.json"))));
  }

  @Test
  void parseJSONObjectExtraKeys() {
    try {
      jsonParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/extraKeys.json")));
    } catch (Exception e) {
      Assertions.assertEquals(String.format("Unexpected key %s was found in file", "BadKey"),
          e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/extraKeys.json"))));
  }

  @Test
  void uploadBadFile() {
    Assertions.assertThrows(IOException.class,
        () -> jsonParser.parseJSON(
            JSONObjectParser.parseJSONObject(new File("./doc/plan/data/nonexistent.json"))));
  }

  @Test
  void parseJSONObjectTwoPlayers() {
    try {
      jsonParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/twoPlayers.json")));
    } catch (Exception e) {
      Assertions.assertEquals("You should only have one player in your game.", e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/twoPlayers.json"))));
  }

  @Test
  void parseJSONObjectDuplicateGhosts() {
    try {
      jsonParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/duplicateGhosts.json")));
    } catch (Exception e) {
      Assertions.assertEquals("Game cannot have more than one of each type of ghost",
          e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/duplicateGhosts.json"))));
  }

  @Test
  void parseJSONObjectMissingRequiredPellet() {
    try {
      jsonParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/missingRequiredPellet.json")));
    } catch (Exception e) {
      Assertions.assertEquals(
          String.format("Game does not contain any of the %s required pellets", "Dot"),
          e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/missingRequiredPellet.json"))));
  }

  @Test
  void getStartingConfigFromPreferences()
      throws IOException, NoSuchMethodException, IllegalAccessException {
    PreferencesParser preferencesParser = new PreferencesParser();
    preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/simpleConfig.json")));

    jsonParser.addVanillaGameDataConsumer(vanillaGame -> comparePlayers("Pacman",
        vanillaGame.player()));

    Map<String, Boolean> expectedPelletMap = new HashMap<>();
    expectedPelletMap.put("Dot", Boolean.TRUE);
    jsonParser.addVanillaGameDataConsumer(vanillaGame -> comparePelletMaps(expectedPelletMap,
        vanillaGame.pelletInfo()));

    Map<String, List<Position>> expectedWallMap = new HashMap<>();
    expectedWallMap.put("Dot", new ArrayList<>());
    expectedWallMap.put("Pacman", new ArrayList<>());
    expectedWallMap.put("Wall", new ArrayList<>());
    expectedWallMap.get("Dot").add(new Position(2, 0));
    expectedWallMap.get("Pacman").add(new Position(1, 0));
    expectedWallMap.get("Wall").add(new Position(0, 0));
    jsonParser.addVanillaGameDataConsumer(vanillaGame -> compareWallMaps(expectedWallMap,
        vanillaGame.wallMap()));

    jsonParser.parseJSON(JSONObjectParser.parseJSONObject(preferencesParser.getStartingConfig()));
  }

  @Test
  void properDimensions() throws IOException {
    int expectedRows = 3;
    int expectedCols = 5;
    jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/basicWallMap.json")));
    Assertions.assertEquals(expectedRows, jsonParser.getRows());
    Assertions.assertEquals(expectedCols, jsonParser.getCols());
  }

  @Test
  void badJsonThrowsJSONException() {
    Assertions.assertThrows(JSONException.class, () -> jsonParser.parseJSON(new JSONObject("bad")));
  }

  @Test
  void properReset() throws IOException {
    Map<String, List<Position>> expected = properWallMapCreationSetup();

    expected.clear();
    expected.put("Wall", new ArrayList<>());
    expected.get("Wall").add(new Position(0, 0));

    expected.put("Pacman", new ArrayList<>());
    expected.get("Pacman").add(new Position(1, 0));

    expected.put("Dot", new ArrayList<>());
    expected.get("Dot").add(new Position(2, 0));

    jsonParser.parseJSON(JSONObjectParser.parseJSONObject(new File("data/tests/simple.json")));
  }

  private Map<String, List<Position>> properWallMapCreationSetup() throws IOException {
    Map<String, List<Position>> expected = new HashMap<>();

    expected.put("Wall", new ArrayList<>());
    for (int x = 0; x < 5; x++) {
      expected.get("Wall").add(new Position(x, 0));
      expected.get("Wall").add(new Position(x, 2));
    }
    expected.get("Wall").add(new Position(0, 1));
    expected.get("Wall").add(new Position(4, 1));

    expected.put("Pacman", new ArrayList<>());
    expected.get("Pacman").add(new Position(1, 1));

    expected.put("Dot", new ArrayList<>());
    expected.get("Dot").add(new Position(2, 1));

    expected.put("Pinky", new ArrayList<>());
    expected.get("Pinky").add(new Position(3, 1));

    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> compareWallMaps(vanillaGameDataInterface.wallMap(), expected));

    jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/basicWallMap.json")));
    return expected;
  }

  @Test
  void setInitialPlayerScoreTest() throws IOException {
    int expected = 1000000;
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> Assertions.assertEquals(expected,
            vanillaGameDataInterface.playerScore()));
    jsonParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/basic_examples/high_score.json")));
  }


}