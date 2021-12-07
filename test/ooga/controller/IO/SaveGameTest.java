package ooga.controller.IO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.GameData;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.GameData;
import ooga.model.GameState;
import ooga.model.VanillaGame;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Game;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;


public class SaveGameTest {

  private JSONConfigObjectBuilder builder;
  private VanillaGame vanillaGame;
  private JSONObject object;
  private GameSaver saver;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of( "pellet", List.of(new Position(0, 0)),
        "Pacman", List.of(new Position(1, 0)), "Wall",
        List.of(new Position(2, 0)));
    Map<String, Boolean> pelletInfo = Map.of("pellet", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 2);
    vanillaGame = new VanillaGame(vanillaGameData);
    saver = new GameSaver(vanillaGame);
  }

  @Test
  void testGameSaver() throws IOException {
    saver.saveGame();
    JsonParser parser = new JsonParser();
  }





@Test
void testJSonFile() throws IOException {
  String path = "data/user_files/TESTSAVEFILE.json";
  File jsonFile = new File(String.valueOf(path));
  FileWriter fileToSave = new FileWriter(jsonFile);
  JSONObject configObject = new JSONObject();
  JSONArray jsonArray = new JSONArray();
  jsonArray.put("Super");
  jsonArray.put("Energizer");
  configObject.put("PowerUps", jsonArray);
  fileToSave.write(configObject.toString());
  fileToSave.close();

}

@Test
  void testAgentToString()
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
  //map of only pacman and dot to its right
  Map<String, List<Position>> wallMap = Map.of( "Ghost", List.of(new Position(1, 0)),
      "Pacman", List.of(new Position(1, 1)), "Wall",
      List.of(new Position(2, 0)));
  Map<String, Boolean> pelletInfo = Map.of("Dot", true);
  GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 2);
  GameState currentState = new GameState(vanillaGameData);
  Agent agent = currentState.getGhosts().get(0);
  String agentString = agent.toString();
  String cutAgentString = agentString.substring(0,agentString.indexOf("@"));
  if (cutAgentString.contains("consumables")) {
    System.out.println(cutAgentString.replace("ooga.model.agents.consumables.", "").strip());
  }
  else {
    System.out.println(cutAgentString.replace("ooga.model.agents.players.", "").strip());
  }
}


}
