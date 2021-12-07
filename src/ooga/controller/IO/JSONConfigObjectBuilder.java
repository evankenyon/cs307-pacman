package ooga.controller.IO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.GameBoard;
import ooga.model.GameEngine;
import ooga.model.GameState;
import ooga.model.interfaces.Agent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Dania Fernandez
 * Builds a JSONObject corresponding to the game's current configuration
 * Is used by GameSaver and FirebaseWriter to build a configuration JSONObject to save
 */
public class JSONConfigObjectBuilder {

  private ResourceBundle agentNames;
  private GameEngine myGameEngine;
  private GameBoard board;
  private GameState state;
  private List<Agent> agentArray = new ArrayList<>();


  /**
   * Sets up agentNames resource bundle to be used to get Strings from agents
   * @param gameEngine, current GameEngine from which to get board and state
   */
  public JSONConfigObjectBuilder(GameEngine gameEngine) {
    agentNames =  ResourceBundle.getBundle("ooga.controller.IO.resources.agentNamesForWallMap");
    myGameEngine = gameEngine;
    board = myGameEngine.getBoard();
    state = board.getGameState();
  }

  /**
   * Sets configuration of JSONObject according to the current game configuration
   * @return JSONObject of current game configuration
   */
  public JSONObject setConfig() {
    agentArray = new ArrayList<>();
    JSONObject configBuilder = new JSONObject();
    String playerString = makeStringFromAgent(state.getMyPlayer());
    configBuilder.put("Player", playerString);
    configBuilder.put("RequiredPellets", buildPelletArray(true));
    configBuilder.put("OptionalPellets", buildPelletArray(false));
    configBuilder.put("NumberOfLives", setNumberOfLives()); // TODO: add accurate num lives remaining
    configBuilder.put("PlayerScore", setPlayerScore(playerString));
    configBuilder.put("WallMap", buildWallMap());
    return configBuilder;
  }

  private String makeStringFromAgent(Agent agent) {
    String agentString = agent.toString();
    return agentNames.getString(agentString.substring(0,agentString.indexOf("@")));
  }

  private JSONArray buildPelletArray(Boolean isRequired) {
    Map<String, Boolean> pelletMap = myGameEngine.getPelletInfo();

    JSONArray pelletArray = new JSONArray();
    for (String key: pelletMap.keySet()) {
      if (pelletMap.get(key) == isRequired) {
        pelletArray.put(key);
      }
    }
    return pelletArray;
  }

  //TODO: account for when ghost is player
  private int setNumberOfLives() {
    return state.getLives();
  }

  private int setPlayerScore(String playerAgentString) {
    if (playerAgentString.contains("Pacman")){
      return board.getMyPacScore();
    }
    else {
      return board.getMyGhostScore();
    }
  }

  private JSONArray buildWallMap() {
    sortAgentArray();
    JSONArray overallWallArray = new JSONArray();
    int numCols = agentArray.get(agentArray.size()-1).getPosition().getCoords()[0] + 1;
    int numRows = agentArray.get(agentArray.size()-1).getPosition().getCoords()[1] + 1;
    int arrayIndex = 0;
    for (int i=0; i < numRows; i++) {
      JSONArray rowWallArray = new JSONArray();
      for (int j=0; j < numCols; j++) {
        Agent currentAgent = agentArray.get(arrayIndex);
        //if (String.valueOf(currentAgent).contains("Pellet")) {
          //check Pellet state
          //Pellet p = (Pellet) currentAgent;
          //if (p.getState() == 0) {
            //rowWallArray.put("Empty");
          //}
          //else {
            //rowWallArray.put(makeStringFromAgent(currentAgent));
          //}
        //}
        //else {
          //rowWallArray.put(makeStringFromAgent(currentAgent));
        //}
        rowWallArray.put(makeStringFromAgent(currentAgent));
        arrayIndex ++;
      }
      overallWallArray.put(rowWallArray);
    }
    return overallWallArray;

  }

  private void sortAgentArray() {
    agentArray.addAll(state.getWalls());
    agentArray.addAll(state.getGhosts());
    agentArray.addAll(state.getFood());
    agentArray.add(state.getMyPlayer());

    Collections.sort(agentArray, new RowComparator()
        .thenComparing(new ColComparator()));

  }

  class RowComparator implements Comparator<Agent> {

    @Override
    public int compare(Agent a1, Agent a2) {
      if (a1.getPosition().getCoords()[1] == a2.getPosition().getCoords()[1]) {
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
      else if (a1.getPosition().getCoords()[0] > a2.getPosition().getCoords()[0]) {
        return 1;
      }
      else {
        return -1;
      }
    }
  }





}
