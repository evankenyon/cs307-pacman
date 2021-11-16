package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
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
    Map<String, List<Position>> expected  = new HashMap<>();

    expected.put("Wall", new ArrayList<>());
    for(int x = 0; x < 5; x++) {
      expected.get("Wall").add(new Position(x, 0));
      expected.get("Wall").add(new Position(x, 2));
    }
    expected.get("Wall").add(new Position(0, 1));
    expected.get("Wall").add(new Position(4,  1));

    expected.put("Pacman", new ArrayList<>());
    expected.get("Pacman").add(new Position(1, 1));

    expected.put("Dot", new ArrayList<>());
    expected.get("Dot").add(new Position(2, 1));

    expected.put("Pinky", new ArrayList<>());
    expected.get("Pinky").add(new Position(3, 1));

    jsonParser.addVanillaGameDataConsumer(vanillaGameDataInterface -> compareWallMaps(vanillaGameDataInterface.getWallMap(), expected));

    jsonParser.uploadFile(new File("tests/basicWallMap.json"));
  }

  private void compareWallMaps(Map<String, List<Position>> actual, Map<String, List<Position>> expected) {
    for (String key : actual.keySet()) {
      for (Position position : actual.get(key)) {
        Assertions.assertTrue(containsPosition(position, expected.get(key)));
      }
    }
  }

  private boolean containsPosition(Position a, List<Position> list) {
    for(Position position : list) {
      if(a.getCoords()[0] == position.getCoords()[0] && a.getCoords()[1] == position.getCoords()[1]) {
        return true;
      }
    }
    return false;
  }

  @Test
  void properPlayerCreation() throws IOException {
    String expected = "Pacman";
    jsonParser.addVanillaGameDataConsumer(vanillaGameDataInterface -> comparePlayers(vanillaGameDataInterface.getPlayer(), expected));
    jsonParser.uploadFile(new File("tests/basicWallMap.json"));
  }

  private void comparePlayers(String actual, String expected) {
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void properRequirePelletCreation() throws IOException {
    Map<String, Boolean> expected = new HashMap<>();
    expected.put("Dot", Boolean.TRUE);
    expected.put("Fruit", Boolean.FALSE);
    jsonParser.addVanillaGameDataConsumer(vanillaGameDataInterface -> comparePelletMaps(vanillaGameDataInterface.getPelletInfo(), expected));
    jsonParser.uploadFile(new File("tests/basicWallMap.json"));
  }

  private void comparePelletMaps(Map<String, Boolean> actual, Map<String, Boolean> expected) {
    for (String pellet : actual.keySet()) {
      Assertions.assertEquals(actual.get(pellet), expected.get(pellet));
    }
  }

  @Test
  void uploadFileNotAllRequiredKeys() {
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.uploadFile(
        new File("tests/notEnoughKeys.json")));
  }

  @Test
  void uploadFileExtraKeys() {
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.uploadFile(
        new File("tests/extraKeys.json")));
  }

  @Test
  void uploadBadFile() {
    Assertions.assertThrows(IOException.class,
        () -> jsonParser.uploadFile(new File("./doc/plan/data/example1.json")));
  }

  @Test
  void uploadFileTwoPlayers() {
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.uploadFile(
        new File("tests/twoPlayers.json")));
  }

  @Test
  void uploadFileDuplicateGhosts() {
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.uploadFile(
        new File("tests/duplicateGhosts.json")));
  }

  @Test
  void uploadFileMissingRequiredPellet() {
    Assertions.assertThrows(InputMismatchException.class, () -> jsonParser.uploadFile(
        new File("tests/missingRequiredPellet.json")));
  }
}