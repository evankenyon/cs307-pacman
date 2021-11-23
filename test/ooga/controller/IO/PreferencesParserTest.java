package ooga.controller.IO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
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
    Assertions.assertThrows(InputMismatchException.class, () -> preferencesParser.uploadFile(new File("data/tests/preferences/invalidKey.json")));
  }

  @Test
  void invalidImagePath() {
    Assertions.assertThrows(InputMismatchException.class, () -> preferencesParser.uploadFile(new File("data/tests/preferences/invalidImagePath.json")));
  }

  @Test
  void invalidColor() {
    Assertions.assertThrows(InputMismatchException.class, () -> preferencesParser.uploadFile(new File("data/tests/preferences/invalidColor.json")));
  }

  @Test
  void getImagePathsPacman()
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String expected = "images/fruit.png";
    preferencesParser.uploadFile(new File("data/tests/preferences/pacmanImagePath.json"));
    Assertions.assertEquals(expected, preferencesParser.getImagePaths().get("Pacman"));
  }

  @Test
  void getColorDot()
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    List<Double> expected = List.of(new Double[]{0.0, 0.5, 0.25});
    preferencesParser.uploadFile(new File("data/tests/preferences/dotColor.json"));
    for (int index = 0; index < expected.size(); index++) {
      Assertions.assertEquals(expected.get(index), preferencesParser.getColors().get("Dot").get(index));
    }

  }

  @Test
  void getStyleLight()
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String expected = "Light";
    preferencesParser.uploadFile(new File("data/tests/preferences/lightStyle.json"));
    Assertions.assertEquals(expected, preferencesParser.getStyle());
  }

  @Test
  void getStartingConfig()
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String expected = "test_implementation.json";
    preferencesParser.uploadFile(new File("data/tests/preferences/startingConfig.json"));
    Assertions.assertEquals(expected, preferencesParser.getStartingConfig().getName());
  }
}