package ooga.controller.IO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.GameData;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FIrebaseWriterTest {

  private FirebaseWriter writer;
  private VanillaGame myGame;
  JSONConfigObjectBuilder builder;


  @BeforeEach
  void setUp() throws IOException {
    writer = new FirebaseWriter();
  }

  @Test
  void testSaveObject()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, FileNotFoundException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(0, 0)), "Dot",
        List.of(new Position(1, 0)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);
    myGame = new VanillaGame(vanillaGameData);
    builder = new JSONConfigObjectBuilder(myGame);
    System.out.println(String.valueOf(builder.setConfig()));
    writer.saveObject(myGame);
  }

}

