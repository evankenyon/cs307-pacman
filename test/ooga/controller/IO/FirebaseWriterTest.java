package ooga.controller.IO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import ooga.model.GameData;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FirebaseWriterTest {

  private FirebaseWriter writer;
  private VanillaGame myGame;
  JSONConfigObjectBuilder builder;


  @BeforeEach
  void setUp() throws FirebaseException, UnsupportedEncodingException {
    writer = new FirebaseWriter();
  }

  @Test
  void testSaveObject()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JacksonUtilityException, FirebaseException, UnsupportedEncodingException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(0, 0)), "Dot",
        List.of(new Position(1, 0)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", false);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);
    myGame = new VanillaGame(vanillaGameData);
    builder = new JSONConfigObjectBuilder(myGame);
    System.out.println(String.valueOf(builder.setConfig()));
    writer.saveObject(myGame);

    FirebaseReader firebaseReader = new FirebaseReader();
    JSONObject savedObject = firebaseReader.getFile("user-file-1");
    Assertions.assertEquals("Pacman", savedObject.getString("Player"));
    Assertions.assertEquals(3, savedObject.getInt("NumberOfLives"));
    Assertions.assertEquals(0, savedObject.getInt("PlayerScore"));
    JSONArray expectedOptionalPellets = new JSONArray();
    expectedOptionalPellets.put("Dot");
    Assertions.assertEquals(String.valueOf(expectedOptionalPellets), String.valueOf(savedObject.getJSONArray("OptionalPellets")));
    JSONArray expectedRequiredPellets = new JSONArray();
    Assertions.assertEquals(String.valueOf(expectedRequiredPellets), String.valueOf(savedObject.getJSONArray("RequiredPellets")));
  }

}

