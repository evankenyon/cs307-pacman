package ooga.model;

import static ooga.model.agents.consumables.Ghost.AFRAID_STATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import ooga.factories.AgentFactory;
import ooga.factories.ConsumableFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;


public class GameStateData {


  private Agent myPlayer;
  private boolean isSuper;
  private int foodLeft;
  private final AgentFactory agentFactory = new AgentFactory();
  private final ConsumableFactory consumableFactory = new ConsumableFactory();
  private final List<Consumable> myOptionalPelletStates;

  private List<Agent> myAgentStates;
  private List<Position> myInitAgentPositions;
  private List<Consumable> myRequiredPelletStates;

  private List<Agent> myWallStates;
  private List<Agent> myDoorStates;
  private boolean[][] myWallMap;
  private int pacmanLives;

  /**
   * Constructor for GameStateData, object holding useful data structures
   */
  public GameStateData() {
    myAgentStates = new ArrayList<>();
    myRequiredPelletStates = new ArrayList<>();
    myOptionalPelletStates = new ArrayList<>();
  }

  /**
   * Initializes a GameStateData object
   *
   * @param data from controller
   */
  public void initialize(GameData data) {
    Map<String, List<Position>> gameDict = data.wallMap();
    Map<String, Boolean> pelletInfo = data.pelletInfo();
    int rows = calculateDimension(gameDict, 1) + 1;
    int cols = calculateDimension(gameDict, 0) + 1;
    isSuper = false;
    myAgentStates = new ArrayList<>();
    myDoorStates = new ArrayList<>();
    myRequiredPelletStates = new ArrayList<>();
    myWallStates = new ArrayList<>();
    myInitAgentPositions = new ArrayList<>();
    myWallMap = new boolean[cols][rows];
    pacmanLives = data.numLives();
    createWallMap(gameDict, rows, cols);
    createAgentList(gameDict, data.player());
    createWallList(gameDict);
    createPelletLists(gameDict, pelletInfo);
    createEmptySpots(gameDict);
  }

  /**
   * @return the number of food left to win
   */
  public int getFoodLeft() {
    foodLeft = myRequiredPelletStates.size();
    return foodLeft;
  }

  /**
   * @return list of consumables that are required
   */
  public List<Consumable> getMyRequiredPelletStates() {
    return myRequiredPelletStates;
  }

  /**
   * @return list of walls
   */
  public List<Agent> getMyWallStates() {
    return myWallStates;
  }

  /**
   * @return whether game is super
   */
  public boolean isSuper() {
    return isSuper;
  }

  /**
   * set game state to super
   */
  public void setSuper() {
    isSuper = true;
    attachSuperTimer();
  }

  private void attachSuperTimer() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        isSuper = false;
      }
    }, 5000);
  }

  /**
   * @return controllable player
   */
  public Agent getMyPlayer() {
    return myPlayer;
  }

  /**
   * if given x,y is a wall
   *
   * @param x
   * @param y
   * @return whether it's a wall
   */
  public boolean isWall(int x, int y) {
    try {
      return myWallMap[x][y];
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @return list of optional consumables
   */
  public List<Consumable> getMyOptionalPelletStates() {
    return myOptionalPelletStates;
  }

  /**
   * @return list of moving agents
   */
  public List<Agent> getAgents() {
    return myAgentStates;
  }

  /**
   * Finds agent with position
   *
   * @param pos
   * @return agent object
   */
  public Agent findAgent(Position pos) {
    Agent potentialAgent = null;
    List<Agent> allAgents = new ArrayList<>(myAgentStates);
    allAgents.addAll(myWallStates);
    allAgents.addAll(myOptionalPelletStates);
    allAgents.addAll(myRequiredPelletStates);
    for (Agent agent : allAgents) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }
    return potentialAgent;
  }

  private void createPelletLists(Map<String, List<Position>> gameDict,
      Map<String, Boolean> pelletInfo) {
    for (String key : pelletInfo.keySet()) {
      if (pelletInfo.get(key)) {
        List<Position> tempPellets = gameDict.get(key);
        if (tempPellets != null) {
          for (Position dot : tempPellets) {
            int x = dot.getCoords()[0];
            int y = dot.getCoords()[1];
            Consumable consumable = consumableFactory.createConsumable(key, x, y);

            if (key.equals("Super")) {
              consumable.addRunnable(this::setSuperState);
            } else if (key.equals("Key")) {
              consumable.addRunnable(this::openDoors);
            }
            myRequiredPelletStates.add(consumable);
          }
        } else {
          throw new IllegalArgumentException("We can't win without required pellets!");
        }
      } else {
        List<Position> tempPellets = gameDict.get(key);
        if (tempPellets != null) {
          for (Position dot : tempPellets) {
            int x = dot.getCoords()[0];
            int y = dot.getCoords()[1];
            myOptionalPelletStates.add(consumableFactory.createConsumable(key, x, y));
          }
        }
      }
    }
    foodLeft = myRequiredPelletStates.size();
  }

  private void setSuperState() {
    this.setSuper();
    for (Agent ghost : getGhosts()) {
      ghost.setState(AFRAID_STATE);
    }
  }

  private void openDoors() {
    int count = 0;
    for (Agent door : myDoorStates) {
      if (door.getState() == 0) {
        door.setState(1);
        count++;
      }
      if (count == 2) {
        break;
      }
    }
  }

  private List<Agent> getGhosts() {
    System.out.println(this.getAgents().subList(1, this.getAgents().size()).size());
    return this.getAgents().subList(1, this.getAgents().size());
  }

  private void createEmptySpots(Map<String, List<Position>> gameDict) {
    if (gameDict.get("Empty") != null) {
      for (Position emptyPos : gameDict.get("Empty")) {
        int x = emptyPos.getCoords()[0];
        int y = emptyPos.getCoords()[1];
        myInitAgentPositions.add(new Position(x, y));
        myWallStates.add(agentFactory.createAgent("Empty", x, y));
      }
    }
  }

  /**
   * @return number of pacman lives
   */
  public int getPacmanLives() {
    return pacmanLives;
  }

  private void createAgentList(Map<String, List<Position>> gameDict, String player) {
    for (Position agentPos : gameDict.get("Pacman")) {
      int x = agentPos.getCoords()[0];
      int y = agentPos.getCoords()[1];
      myInitAgentPositions.add(new Position(x, y));
      myAgentStates.add(agentFactory.createAgent("Pacman", x, y));
      //TODO - convert to properties
      if (player.equals("Pacman")) {
        myPlayer = myAgentStates.get(0);
      }
    }
    if (gameDict.get("Ghost") != null) {
      for (Position agentPos : gameDict.get("Ghost")) {
        int x = agentPos.getCoords()[0];
        int y = agentPos.getCoords()[1];
        myInitAgentPositions.add(new Position(x, y));
        myInitAgentPositions.add(new Position(x, y));
        myAgentStates.add(agentFactory.createAgent("Ghost", x, y));
        if (player.equals("Ghost")) {
          myPlayer = myAgentStates.get(1);
        }
      }
    }
  }

  private void createWallList(Map<String, List<Position>> gameDict) {
    if (gameDict.get("Wall") != null || gameDict.get("Door") != null) {
      for (Position wallPos : gameDict.get("Wall")) {
        int x = wallPos.getCoords()[0];
        int y = wallPos.getCoords()[1];
        myWallStates.add(agentFactory.createAgent("Wall", x, y));
      }
      if (gameDict.containsKey("Door")) {
        for (Position wallPos : gameDict.get("Door")) {
          int x = wallPos.getCoords()[0];
          int y = wallPos.getCoords()[1];
          Agent door = agentFactory.createAgent("Wall", x, y);
          myDoorStates.add(door);
          myWallStates.add(door);
        }
      }
    }
  }

  /**
   * decrease pacman lives
   */
  public void decreaseLives() {
    pacmanLives--;
  }

  /**
   * @return List of initial agent positions
   */
  public List<Position> getMyInitAgentPositions() {
    return myInitAgentPositions;
  }

  private void createWallMap(Map<String, List<Position>> gameDict, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        myWallMap[j][i] = false;
      }
    }
    List<Position> walls = new ArrayList<>(gameDict.get("Wall"));
    if (gameDict.containsKey("Door")) {
      walls.addAll(gameDict.get("Door"));
    }

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
