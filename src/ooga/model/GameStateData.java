package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

public class GameStateData {

  private boolean isWin;
  private boolean isLose;
  private boolean isSuper;
  private int myPacScore;
  private int myGhostScore;
  private int foodLeft;
  private final AgentFactory agentFactory = new AgentFactory();
  private List<Agent> myAgentStates;
  private List<Agent> myPelletStates;
  private boolean[][] myWallMap;


  public GameStateData() {
    isLose = false;
    isWin = false;
    myPacScore = 0;
    myGhostScore = 0;
    myAgentStates = new ArrayList<>();
    myPelletStates = new ArrayList<>();

  }

  public GameStateData(GameStateData previous) {
    isWin = previous.isWin;
    isLose = previous.isLose;
    myPacScore = previous.myPacScore;
    myGhostScore = previous.myGhostScore;
    isSuper = previous.isSuper;
    foodLeft = previous.foodLeft;
    myAgentStates = previous.myAgentStates;
    myPelletStates = previous.myPelletStates;
    myWallMap = previous.myWallMap;

  }

  public void initialize(Map<String, List<Position>> gameDict, Map<String, Boolean> pelletInfo) {
    int rows = calculateDimension(gameDict, 1) + 1;
    int cols = calculateDimension(gameDict, 0) + 1;
    isWin = false;
    isLose = false;
    isSuper = false;
    myPacScore = 0;
    myGhostScore = 0;
    myAgentStates = new ArrayList<>();
    myWallMap = new boolean[cols][rows];
    createWallMap(gameDict, rows, cols);
    createAgentList(gameDict);
    createRequiredPelletList(gameDict, pelletInfo);
  }

  public int getFoodLeft() {
    foodLeft = myPelletStates.size();
    return foodLeft;
  }

  public List<Agent> getMyAgentStates() {
    return myAgentStates;
  }

  public List<Agent> getMyPelletStates() {
    return myPelletStates;
  }

  public boolean isWin() {
    return isWin;
  }

  public boolean isLose() {
    return isLose;
  }

  public int getMyPacScore() {
    return myPacScore;
  }

  public int getMyGhostScore() {
    return myGhostScore;
  }

  public boolean isWall(int x, int y) {
    return myWallMap[x][y];
  }

  public List<Agent> getAgents() {
    return myAgentStates;
  }

  //TODO: to complete refactor, need to fix this, but walls aren't agents anymore?
  public Agent findAgent(Position pos) {
    Agent potentialAgent = null;
    for (Agent agent : myAgentStates) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }

    for (Agent agent : myPelletStates) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }

    for (Agent agent : myWallMap) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }
    return potentialAgent;
  }

  private void createRequiredPelletList(Map<String, List<Position>> gameDict,
      Map<String, Boolean> pelletInfo) {
    for (String key : pelletInfo.keySet()) {
      if (pelletInfo.get(key)) {
        List<Position> tempPellets = gameDict.get(key);
        for (Position dot : tempPellets) {
          int x = dot.getCoords()[0];
          int y = dot.getCoords()[1];
          myPelletStates.add(agentFactory.createAgent(key, x, y));
        }
      }
    }
    foodLeft = myPelletStates.size();
  }

  private void createAgentList(Map<String, List<Position>> gameDict) {
    for (Position agentPos : gameDict.get("Pacman")) {
      int x = agentPos.getCoords()[0];
      int y = agentPos.getCoords()[1];
      myAgentStates.add(agentFactory.createAgent("Pacman", x, y));
    }

    // if file doesn't have a ghost on it / not sure if best design in terms of flexibility?
    if (gameDict.get("Ghost") != null) {
      for (Position agentPos : gameDict.get("Ghost")) {
        int x = agentPos.getCoords()[0];
        int y = agentPos.getCoords()[1];
        myAgentStates.add(agentFactory.createAgent("Ghost", x, y));
      }
    }
  }

  private void createWallMap(Map<String, List<Position>> gameDict, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        myWallMap[j][i] = false;
      }
    }
    List<Position> walls = gameDict.get("Wall");

    if (walls != null) {
      for (Position wall : walls) {
        myWallMap[wall.getCoords()[0]][wall.getCoords()[1]] = true;
      }
    }
  }

  private int calculateDimension(Map<String, List<Position>> initialStates, int dim) {
    int maxCol = 0;
    for (String key : initialStates.keySet()) {
      for (Position position : initialStates.get(key)) {
        maxCol = Math.max(position.getCoords()[dim], maxCol);
      }
    }
    return maxCol;
  }

}
