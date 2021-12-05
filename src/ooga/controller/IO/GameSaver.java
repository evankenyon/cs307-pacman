package ooga.controller.IO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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


public class GameSaver {

  private int counter = 1;
  private StringBuilder path = new StringBuilder();

  private JSONObject config;

  private List<Agent> agentArray = new ArrayList<>();
  private Map<String, Boolean> pelletInfoMap;

  //for constructor
  private GameState state;
  private VanillaGame myVanillaGame;
  private GameBoard board;
  private ResourceBundle agentNames;

  public GameSaver(VanillaGame vanillaGame) {
    myVanillaGame = vanillaGame;
    board = myVanillaGame.getBoard();
    state = board.getGameState();
    agentNames =  ResourceBundle.getBundle("ooga.controller.IO.resources.agentNamesForWallMap");
  }

  private void setConfig() {
    JSONObject configBuilder = new JSONObject();
    String playerString = makeStringFromAgent(state.getMyPlayer());
    configBuilder.put("Player", playerString);
    configBuilder.put("RequiredPellets", buildPelletArray(true));
    configBuilder.put("OptionalPellets", buildPelletArray(false));
    configBuilder.put("NumberOfLives", setNumberOfLives()); // TODO: add accurate num lives remaining
    configBuilder.put("PlayerScore", setPlayerScore(playerString));
    configBuilder.put("WallMap", buildWallMap());
    config = configBuilder;
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


  /**
   * for now - handles all json & broader file responsibilities
   * @throws IOException
   */
  public void saveGame() throws IOException {
    clearBuilders();
    path.append("data/user_files/user_file");
    path.append("_"+ String.valueOf(counter));
    path.append(".json");
    counter++;

    setConfig();

    File jsonFile = new File(String.valueOf(path));
    try {
      FileWriter fileToSave = new FileWriter(jsonFile);
      fileToSave.write(String.valueOf(config));

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


  private String makeStringFromAgent(Agent agent) {
    String agentString = agent.toString();
    return agentNames.getString(agentString.substring(0,agentString.indexOf("@")));
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


  private JSONArray buildWallMap() {
    sortAgentArray();
    JSONArray overallWallArray = new JSONArray();
    int numCols = agentArray.get(agentArray.size()-1).getPosition().getCoords()[0] + 1;
    int numRows = agentArray.get(agentArray.size()-1).getPosition().getCoords()[1] + 1;
    int arrayIndex = 0;
    for (int i=0; i < numRows; i++) {
      JSONArray rowWallArray = new JSONArray();
      for (int j=0; j < numCols; j++) {
        rowWallArray.put(makeStringFromAgent(agentArray.get(arrayIndex)));
        arrayIndex ++;
      }
      overallWallArray.put(rowWallArray);
    }
    //System.out.println(overallWallArray);
    return overallWallArray;

  }



}
