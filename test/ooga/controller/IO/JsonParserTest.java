package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import ooga.model.util.AgentInfo;
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
  void properWallMapCreation() {
    Map<String, List<AgentInfo>> expected  = new HashMap<>();

    expected.put("Wall", new ArrayList<>());
    for(int x = 0; x < 5; x++) {
      expected.get("Wall").add(new AgentInfo(x, 0, 1));
      expected.get("Wall").add(new AgentInfo(x, 2, 1));
    }
    expected.get("Wall").add(new AgentInfo(0, 1, 1));
    expected.get("Wall").add(new AgentInfo(0, 4, 1));

    expected.put("Pacman", new ArrayList<>());
    expected.get("Pacman").add(new AgentInfo(1, 1, 1));

    expected.put("Dot", new ArrayList<>());
    expected.get("Dot").add(new AgentInfo(2, 1, 1));

    expected.put("Pinky", new ArrayList<>());
    expected.get("Pinky").add(new AgentInfo(3, 1, 1));

    jsonParser.addWallMapConsumer(wallMap -> compareMaps(wallMap, expected));
  }

  private void compareMaps(Map<String, List<AgentInfo>> actual, Map<String, List<AgentInfo>> expected) {
    for (String key : actual.keySet()) {
      for (AgentInfo agentInfo : actual.get(key)) {
        Assertions.assertTrue(expected.get(key).contains(agentInfo));
      }
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