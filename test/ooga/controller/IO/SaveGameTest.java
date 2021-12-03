package ooga.controller.IO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.Data;
import ooga.model.GameState;
import ooga.model.interfaces.Agent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SaveGameTest {



  @Test
void testGameSaver()
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
  //map of only pacman and dot to its right
  Map<String, List<Position>> wallMap = Map.of( "Ghost", List.of(new Position(1, 0)),
      "Pacman", List.of(new Position(1, 1)), "Wall",
      List.of(new Position(2, 0)));
  Map<String, Boolean> pelletInfo = Map.of("Dot", true);
  Data vanillaGameData = new Data(wallMap, "Pacman", pelletInfo, 1, 2);
  GameState currentState = new GameState(vanillaGameData);

  Agent player = currentState.getMyPlayer();
  System.out.println(player);
  List<Agent> otherAgents = currentState.getMyOtherAgents();
  System.out.println(otherAgents);
  List<Agent> walls = currentState.getMyWalls();
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


 class RowComparator implements Comparator<Agent> {

   @Override
   public int compare(Agent a1, Agent a2) {
     if (a1.getPosition().getCoords()[0] == a2.getPosition().getCoords()[0]) {
       return 0;
     }
     else if (a1.getPosition().getCoords()[1] > a2.getPosition().getCoords()[1]) {
       return 1;
     }
     else {
      return -1;
     }
   }
 }

  class ColComparator implements Comparator<Agent> {

    @Override
    public int compare(Agent a1, Agent a2) {
      if (a1.getPosition().getCoords()[0] == a2.getPosition().getCoords()[0]) {
        return 0;
      }
      else if (a1.getPosition().getCoords()[1] > a2.getPosition().getCoords()[1]) {
        return 1;
      }
      else {
        return -1;
      }
    }
  }

  Collections.sort(agentArray, new RowComparator()
      .thenComparing(new ColComparator()));


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


}
