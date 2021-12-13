package ooga.controller.IO;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import ooga.model.GameData;
import ooga.model.GameEngine;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FirebaseWriterTest {

  private FirebaseWriter firebaseWriter;
  private GameEngine gameEngine;


  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, FirebaseException, UnsupportedEncodingException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("Dot", List.of(new Position(0, 0)),
        "Pacman", List.of(new Position(1, 0)), "Wall",
        List.of(new Position(2, 0)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    Map<String, Boolean> emptyPelletInfo = new HashMap<>();
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 2);
    gameEngine = new GameEngine(vanillaGameData);
    firebaseWriter = new FirebaseWriter(gameEngine, "TEST-OBJECT");
  }

  @Test
  void testSaveObject()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JacksonUtilityException, FirebaseException, UnsupportedEncodingException {
    //map of only pacman and dot to its right
    firebaseWriter.saveObject();
    FirebaseReader firebaseReader = new FirebaseReader();
    JSONObject savedObject = firebaseReader.getFile("TEST-OBJECT");
    Assertions.assertEquals("Pacman", savedObject.getString("Player"));
    Assertions.assertEquals(3, savedObject.getInt("NumberOfLives"));
    Assertions.assertEquals(0, savedObject.getInt("PlayerScore"));
    JSONArray expectedRequiredPellets = new JSONArray();
    expectedRequiredPellets.put("Dot");
    Assertions.assertEquals(String.valueOf(expectedRequiredPellets),
        String.valueOf(savedObject.getJSONArray("RequiredPellets")));
    JSONArray expectedOptionalPellets = new JSONArray();
    expectedOptionalPellets.put("empty");
    Assertions.assertEquals(String.valueOf(expectedOptionalPellets),
        String.valueOf(savedObject.getJSONArray("OptionalPellets")));
  }
}

