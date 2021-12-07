package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import ooga.controller.IO.utils.JSONObjectParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreferencesParserTest {

  private PreferencesParser preferencesParser;

  @BeforeEach
  void setUp() {
    preferencesParser = new PreferencesParser();
  }

  @Test
  void invalidKey() {
    try {
      preferencesParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/preferences/invalidKey.json")));
    } catch (InputMismatchException | IOException | NoSuchMethodException e) {
      Assertions.assertEquals("The uploaded file does not have enough keys", e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/invalidKey.json"))));
  }

  @Test
  void invalidImagePath() {
    try {
      preferencesParser.parseJSON(JSONObjectParser.parseJSONObject(
          new File("data/tests/preferences/invalidImagePath.json")));
    } catch (InputMismatchException | IOException | NoSuchMethodException e) {
      Assertions.assertEquals(
          String.format("Invalid image path %s was passed in for key %s", "bad", "Pacman"),
          e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(
            new File("data/tests/preferences/invalidImagePath.json"))));
  }

  @Test
  void invalidColor() {
    try {
      preferencesParser.parseJSON(
          JSONObjectParser.parseJSONObject(new File("data/tests/preferences/invalidColor.json")));
    } catch (InputMismatchException | IOException | NoSuchMethodException e) {
      Assertions.assertEquals(String.format("Invalid rgb value of %s", "2.0"), e.getMessage());
    }
    Assertions.assertThrows(InputMismatchException.class, () -> preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/invalidColor.json"))));
  }

  @Test
  void getImagePathsPacman()
      throws IOException, NoSuchMethodException {
    String expected = "data/images/fruit.png";
    preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/pacmanImagePath.json")));
    Assertions.assertEquals(expected, preferencesParser.getImagePaths().get("Pacman"));
  }

  @Test
  void getColorDot()
      throws IOException, NoSuchMethodException {
    List<Double> expected = List.of(new Double[]{0.0, 0.5, 0.25});
    preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/dotColor.json")));
    for (int index = 0; index < expected.size(); index++) {
      Assertions.assertEquals(expected.get(index),
          preferencesParser.getColors().get("Dot").get(index));
    }
  }

  @Test
  void getStyleLight()
      throws IOException, NoSuchMethodException {
    String expected = "Light";
    preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/lightStyle.json")));
    Assertions.assertEquals(expected, preferencesParser.getStyle());
  }

  @Test
  void getStartingConfig()
      throws IOException, NoSuchMethodException {
    String expected = "test_implementation.json";
    preferencesParser.parseJSON(
        JSONObjectParser.parseJSONObject(new File("data/tests/preferences/startingConfig.json")));
    Assertions.assertEquals(expected, preferencesParser.getStartingConfig().getName());
  }

  // Did not add a test for NoSuchMethodException since this will never occur, as props file only
  // contains valid method names
}