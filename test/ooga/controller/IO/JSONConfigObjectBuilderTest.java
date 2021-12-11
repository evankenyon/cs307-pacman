package ooga.controller.IO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.GameData;
import ooga.model.GameEngine;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JSONConfigObjectBuilderTest {

  private JSONConfigObjectBuilder builder;
  private GameEngine gameEngine;
  private JSONObject object;


  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("pellet", List.of(new Position(0, 0)),
        "Pacman", List.of(new Position(1, 0)), "Wall",
        List.of(new Position(2, 0)));
    Map<String, Boolean> pelletInfo = Map.of("pellet", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 2);
    gameEngine = new GameEngine(vanillaGameData);
    builder = new JSONConfigObjectBuilder(gameEngine);
    object = builder.setConfig();
  }

  @Test
  void JSONConfigObjectBuilderTest() {
    Assertions.assertEquals("Pacman", object.getString("Player"));
    JSONArray optionalPelletsArray = new JSONArray();
    Assertions.assertEquals(String.valueOf(optionalPelletsArray),
        String.valueOf(object.getJSONArray("OptionalPellets")));
    JSONArray requiredPelletsArray = new JSONArray();
    requiredPelletsArray.put("pellet");
    Assertions.assertEquals(String.valueOf(requiredPelletsArray),
        String.valueOf(object.getJSONArray("RequiredPellets")));
    Assertions.assertEquals(3, object.getInt("NumberOfLives"));
    Assertions.assertEquals(0, object.getInt("PlayerScore"));
    JSONArray wallMap = new JSONArray();
    JSONArray rowArray = new JSONArray();
    rowArray.put("Dot");
    rowArray.put("Pacman");
    rowArray.put("wall");
    wallMap.put(rowArray);
    Assertions.assertEquals(String.valueOf(wallMap),
        String.valueOf(object.getJSONArray("WallMap")));
  }


}
