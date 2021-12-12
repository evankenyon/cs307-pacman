package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import net.thegreshams.firebase4j.error.FirebaseException;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.GameData;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.GameState;
import ooga.model.GameEngine;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SaveGameTest {

  private GameEngine gameEngine;
  private GameSaver saver;
  private JsonParser parser;
  private JSONObject object;
  private static final String MAGIC_VALUES_FILENAME = "JsonParserMagicValues";
  private ResourceBundle magicValues;
  private static final String DEFAULT_RESOURCE_PACKAGE =
      JsonParser.class.getPackageName() + ".resources.";



  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, FirebaseException, UnsupportedEncodingException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("Dot", List.of(new Position(0, 0)),
        "Ghost", List.of(new Position(1, 0)), "Wall",
        List.of(new Position(2, 0)));
    //Map<String, Boolean> pelletInfo = Map.of("Dot", true, "Super", false);
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Ghost", 0, 3, pelletInfo, 1, 2);
    gameEngine = new GameEngine(vanillaGameData);
    saver = new GameSaver(gameEngine, "TEST-FILE");
    parser = new JsonParser();
    magicValues = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, MAGIC_VALUES_FILENAME));
  }

  @Test
  void testGameSaver() throws IOException {
    //actual
    saver.saveGame();
    object = JSONObjectParser.parseJSONObject(new File("data/user_files/TEST-FILE.json"));
    Assertions.assertEquals("Ghost", object.getString(magicValues.getString("PlayerKey")));
    Assertions.assertEquals(3, object.getInt(magicValues.getString("NumberOfLivesKey")));
    Assertions.assertEquals(0, object.getInt(magicValues.getString("PlayerScoreKey")));
    JSONArray expectedRequiredPellets = new JSONArray();
    expectedRequiredPellets.put("Dot");
    Assertions.assertEquals(String.valueOf(expectedRequiredPellets),
        String.valueOf(object.getJSONArray("RequiredPelletsKey")));
    JSONArray expectedOptionalPellets = new JSONArray();
    //expectedRequiredPellets.put("Dot");
    Assertions.assertEquals(String.valueOf(expectedOptionalPellets),
        String.valueOf(object.getJSONArray("OptionalPelletsKey")));

  }


}
