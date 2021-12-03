package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.GameState;
import ooga.model.interfaces.Agent;
import org.json.JSONObject;


public class GameSaver {

  private static int counter = 1;
  private static StringBuilder path = new StringBuilder();

  //json values
  private JSONObject player;
  private JSONObject numberOfLives;
  private JSONObject difficultyLevel;
  private JSONObject wallMap;

  //arraylist of Agents
  private List<Agent> agentArray;

  //for constructor
  private GameState state;
  private ResourceBundle agentTranslator;

  public GameSaver(GameState currentState) {
    state = currentState;
    agentTranslator = ResourceBundle.getBundle(String.format("%s%s", "AgentsToJSONObjects"));
  }

  /**
   * for now - handles all json & broader file responsibiilites
   * @throws IOException
   */
  public void saveGame() throws IOException {
    clearBuilders();
    path.append("data/user_files/user_file");
    path.append("_"+ String.valueOf(counter));
    path.append(".json");
    counter++;
    File jsonFile = new File(String.valueOf(path));

    try {
      FileWriter fileToSave = new FileWriter(jsonFile);
      JSONObject configObject = new JSONObject();
      //numberOfLives = "Test-Number-Of-Lives";
      configObject.put("NumberOfLives", numberOfLives);
      //difficultyLevel = "Test-Difficulty-Level";
      configObject.put("WallMap", difficultyLevel);
      //wallMap = currentState.getMyWalls().toString();
      configObject.put("WallMap", wallMap);
      fileToSave.write(String.valueOf(configObject));
      fileToSave.close();
    } catch (IOException e) {
      System.out.println("SaveGame Exception");
    }


  }

  private void clearBuilders() {
    path = new StringBuilder();
  }

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

  private void setObjectByAgent(Agent agent) {
    //for (Key key: )
  }

  private void setJSONObjects() {
    Agent P  = state.getMyPlayer();

    //numberOfLives;
    //difficultyLevel;
    //wallMap;
  }

  private void getSortedAgentArray() {

  }




}
