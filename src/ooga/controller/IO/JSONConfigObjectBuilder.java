package ooga.controller.IO;

import com.google.protobuf.DescriptorProtos.EnumDescriptorProto.EnumReservedRange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.GameBoard;
import ooga.model.GameState;
import ooga.model.VanillaGame;
import ooga.model.interfaces.Agent;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONConfigObjectBuilder {

  private ResourceBundle agentNames;
  private VanillaGame myVanillaGame;
  private GameBoard board;
  private GameState state;
  private List<Agent> agentArray = new ArrayList<>();


  public JSONConfigObjectBuilder(VanillaGame vanillaGame) {
    agentNames =  ResourceBundle.getBundle("ooga.controller.IO.resources.agentNamesForWallMap");
    myVanillaGame = vanillaGame;
    board = myVanillaGame.getBoard();
    state = board.getGameState();
  }

  public JSONObject setConfig() {
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
    Map<String, Boolean> pelletMap = myVanillaGame.getPelletInfo();

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
    //System.out.println(numCols);
    int numRows = agentArray.get(agentArray.size()-1).getPosition().getCoords()[1] + 1;
    //System.out.println(numRows);
    int arrayIndex = 0;
    for (int i=0; i < numRows; i++) {
      JSONArray rowWallArray = new JSONArray();
      for (int j=0; j < numCols; j++) {
        Agent currentAgent = agentArray.get(arrayIndex);
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
    //for (Agent a: agentArray) {
    // System.out.println(a);
    //}
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
