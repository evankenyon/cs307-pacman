package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.GameState;
import ooga.model.interfaces.Agent;
import org.json.JSONArray;
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
      setJSONObjects();
      fileToSave.write(String.valueOf(player));
      fileToSave.write(String.valueOf(wallMap));
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


  private String makeStringFromAgent(Agent agent) {
    String agentString = agent.toString();
    String cutAgentString = agentString.substring(agentString.indexOf("@"));
    if (cutAgentString.contains("consumables")) {
      return agentString.replace("ooga.model.agents.consumables.", "");
    }
    else {
      return agentString.replace("ooga.model.agents.players.", "");
    }
  }

  private void setJSONObjects() {
    String playerString  = makeStringFromAgent(state.getMyPlayer());
    player.put("Player", playerString);
    buildWallMap();
    wallMap.put("WallMap", wallMap);
    //difficultyLevel
    //numLives
  }

  private void sortAgentArray() {
    agentArray.addAll(state.getMyWalls());
    agentArray.addAll(state.getMyOtherAgents());
    agentArray.add(state.getMyPlayer());
    Collections.sort(agentArray, new RowComparator()
        .thenComparing(new ColComparator()));
  }

  private void buildWallMap() {
    sortAgentArray();

    JSONArray overallWallArray = new JSONArray();

    int numRows = agentArray.get(-1).getPosition().getCoords()[1] + 1;
    int numCols = agentArray.get(-1).getPosition().getCoords()[0] + 1;

    int arrayIndex = 0;
    for (int i=0; i < numRows; i++) {
      JSONArray rowWallArray = new JSONArray();
      for (int j=0; j < numCols; j++) {
        rowWallArray.put(makeStringFromAgent(agentArray.get(arrayIndex)));
        arrayIndex ++;
      }
      overallWallArray.put(rowWallArray);
    }

  }



}
