package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
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