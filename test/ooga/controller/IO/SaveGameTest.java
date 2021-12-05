package ooga.controller.IO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
<<<<<<< HEAD
import java.util.ArrayList;
import ooga.model.GameData;
=======
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.Data;
>>>>>>> 3f4f19f7bb1cbdb49c0fd6223091f2c97054c31f
import ooga.model.GameState;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
=======
>>>>>>> 3f4f19f7bb1cbdb49c0fd6223091f2c97054c31f


public class SaveGameTest {



  @Test
void testGameSaver()
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
  //map of only pacman and dot to its right
  Map<String, List<Position>> wallMap = Map.of( "pellet", List.of(new Position(1, 0)),
      "Pacman", List.of(new Position(1, 1)), "Wall",
      List.of(new Position(2, 0)));
  Map<String, Boolean> pelletInfo = Map.of("pellet", true);
  GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 2);
  GameState currentState = new GameState(vanillaGameData);

  Agent player = currentState.getMyPlayer();
  System.out.println(player);
  List<Agent> otherAgents = currentState.getGhosts();
  System.out.println(otherAgents);
  List<Agent> walls = currentState.getWalls();
  List<Agent> agentArray = new ArrayList<>();

  //int x = wall.getPosition().getCoords()[0];
  //int y = wall.getPosition().getCoords()[1];
  //System.out.println(wall.getPosition().getCoords()[0]);
  //System.out.println(wall);
  agentArray.addAll(walls);
  //System.out.println(other);
  agentArray.addAll(otherAgents);
  agentArray.add(player);


  List<List<Agent>> orderedList = new ArrayList<List<Agent>>();

  //Controller.getMapRows;
  //Controller.getMapCols;
  int mapCols = 3;
  int mapRows = 1;




  for (Agent a: agentArray) {
    System.out.println(a.getPosition().getCoords()[0]);
    System.out.println(a.getPosition().getCoords()[1]);
  }

  //CORRECTLY ORDERED ARRAY! -> NOW MUST PUT INTO STRING

  StringBuilder wallMapJsonKey = new StringBuilder();
  wallMapJsonKey.append("[\n");

  int j = 0;
  for (int i=0; i < mapRows; i++) {
    wallMapJsonKey.append("[");
    for (int k=0; k < mapCols; k++) {
      wallMapJsonKey.append("[");
      if (agentArray.get(j).toString().contains("Wall")) {
        wallMapJsonKey.append("Wall"+", ");
      }
      j ++;
    }

  }


  String reduced = player.toString().replace("ooga.model.agents.players.", "");
  int atIndex = reduced.indexOf("@");
  System.out.println(reduced.substring(0,atIndex));



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
