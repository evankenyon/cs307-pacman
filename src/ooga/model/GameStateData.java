package ooga.model;

import static ooga.model.agents.consumables.Ghost.AFRAID_STATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.factories.ConsumableFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;


public class GameStateData {

  private boolean isWin;
  private boolean isLose;
  private boolean isSuper;
  private int myPacScore;
  private int myGhostScore;
  private int foodLeft;
  private final AgentFactory agentFactory = new AgentFactory();
  private final ConsumableFactory consumableFactory = new ConsumableFactory();
  private List<Agent> myAgentStates;
  private List<Agent> myInitAgentStates;
  private List<Consumable> myRequiredPelletStates;
  private List<Agent> myWallStates;
  private boolean[][] myWallMap;
  private int pacmanLives;


  public GameStateData() {
    isLose = false;
    isWin = false;
    myPacScore = 0;
    myGhostScore = 0;
    myAgentStates = new ArrayList<>();
    myRequiredPelletStates = new ArrayList<>();

  }

  public GameStateData(GameStateData previous) {
    isWin = previous.isWin;
    isLose = previous.isLose;
    myPacScore = previous.myPacScore;
    myGhostScore = previous.myGhostScore;
    isSuper = previous.isSuper;
    foodLeft = previous.foodLeft;
    myAgentStates = previous.myAgentStates;
    myRequiredPelletStates = previous.myRequiredPelletStates;
    myWallMap = previous.myWallMap;
    myWallStates = previous.myWallStates;
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
    myRequiredPelletStates = new ArrayList<>();
    myWallStates = new ArrayList<>();
    myWallMap = new boolean[cols][rows];
    createWallMap(gameDict, rows, cols);
    createAgentList(gameDict);
    createWallList(gameDict);
    createRequiredPelletList(gameDict, pelletInfo);
  }

  public int getFoodLeft() {
    foodLeft = myRequiredPelletStates.size();
    return foodLeft;
  }

  public List<Consumable> getMyRequiredPelletStates() {
    return myRequiredPelletStates;
  }

  public List<Agent> getMyWallStates() {
    return myWallStates;
  }

  public boolean isWin() {
    return isWin;
  }

  public boolean isSuper() {
    return isSuper;
  }

  public void setSuper() {
    isSuper = true;
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
    try {
      return myWallMap[x][y];
    } catch (Exception e) {
      return false;
    }
  }

  public List<Agent> getAgents() {
    return myAgentStates;
  }

  public Agent findAgent(Position pos) {
    Agent potentialAgent = null;
    for (Agent agent : myAgentStates) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }

    for (Agent agent : myRequiredPelletStates) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }

    for (Agent agent : myWallStates) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }
    return potentialAgent;
  }

  private void setSuperStates() {
    for (Agent agent : myAgentStates) {
      agent.setState(AFRAID_STATE);
    }
  }

  private void createRequiredPelletList(Map<String, List<Position>> gameDict,
      Map<String, Boolean> pelletInfo) {
    for (String key : pelletInfo.keySet()) {
      if (pelletInfo.get(key)) {
        List<Position> tempPellets = gameDict.get(key);
        for (Position dot : tempPellets) {
          int x = dot.getCoords()[0];
          int y = dot.getCoords()[1];
          myRequiredPelletStates.add(consumableFactory.createConsumable(key, x, y));
        }
      }
    }
    foodLeft = myRequiredPelletStates.size();
  }

  public int getPacmanLives() {
    return pacmanLives;
  }

  private void createAgentList(Map<String, List<Position>> gameDict) {
    for (Position agentPos : gameDict.get("Pacman")) {
      int x = agentPos.getCoords()[0];
      int y = agentPos.getCoords()[1];
      myAgentStates.add(agentFactory.createAgent("Pacman", x, y));
      pacmanLives = 3;
    }

    if (gameDict.get("Ghost") != null) {
      for (Position agentPos : gameDict.get("Ghost")) {
        int x = agentPos.getCoords()[0];
        int y = agentPos.getCoords()[1];
        myAgentStates.add(agentFactory.createAgent("Ghost", x, y));
      }
    }
  }

  private void createWallList(Map<String, List<Position>> gameDict) {
    if (gameDict.get("Wall") != null) {
      for (Position wallPos : gameDict.get("Wall")) {
        int x = wallPos.getCoords()[0];
        int y = wallPos.getCoords()[1];
        myWallStates.add(agentFactory.createAgent("Wall", x, y));
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
