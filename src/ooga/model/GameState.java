package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.BFS;
import ooga.model.movement.Controllable;
import ooga.model.movement.greedyBFS;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameState {

  private static final int ALIVE_STATE = 1;

  private final GameStateData myGameStateData;
  private final int myRows;
  private final int myCols;
  private static final Logger LOG = LogManager.getLogger(GameState.class);

  /**
   * Constructor for GameState holding useful methods to interact with GameStateData
   *
   * @param vanillaGameData from Controller's parsed file
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   */
  public GameState(GameData vanillaGameData)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

    myGameStateData = new GameStateData();
    myGameStateData.initialize(vanillaGameData);
    getPacman().setStrategy(new greedyBFS());

    for (Agent a : getGhosts()) {
      a.setStrategy(new BFS());
    }
    myGameStateData.getMyPlayer().setStrategy(new Controllable());

    myRows = calculateDimension(vanillaGameData.wallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.wallMap(), 0) + 1;

    AgentFactory agentFactory = new AgentFactory();

  }

  /**
   * Checks if a given x,y coordinate is in bounds of the board.
   *
   * @param x
   * @param y
   * @return boolean for bounds
   */
  public boolean isInBounds(int x, int y) {
    if (x > myRows || y > myCols) {
      return false;
    } else {
      return x >= 0 && y >= 0;
    }
  }

  /**
   * Calculates portalling positions between two different portals.
   *
   * @param oldPosition of agent moving
   * @return new position across the map
   */
  public Position portal(Position oldPosition) {
    if (atXEdge(oldPosition) && !isWall(0, oldPosition.getCoords()[1])) {
      return new Position(0, oldPosition.getCoords()[1]);
    } else if (atYEdge(oldPosition) && !isWall(oldPosition.getCoords()[0], 0)) {
      return new Position(oldPosition.getCoords()[0], 0);
    } else if (atXZero(oldPosition) && !isWall(myCols - 1, oldPosition.getCoords()[1])) {
      return new Position(myCols - 1, oldPosition.getCoords()[1]);
    } else if (atYZero(oldPosition) && !isWall(oldPosition.getCoords()[0], myRows - 1)) {
      System.out.println(!isWall(oldPosition.getCoords()[0], myRows - 1));
      return new Position(oldPosition.getCoords()[0], myRows - 1);
    }
    return oldPosition;
  }

  private boolean atXZero(Position position) {
    return position.getCoords()[0] == -1;
  }

  private boolean atYZero(Position position) {
    return position.getCoords()[1] == -1;
  }

  private boolean atXEdge(Position position) {
    return position.getCoords()[0] == myCols;
  }

  private boolean atYEdge(Position position) {
    return position.getCoords()[1] == myRows;
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



  /**
   * Finds agent with position.
   *
   * @param pos
   * @return agent object.
   */
  public Agent findAgent(Position pos) {
    return myGameStateData.findAgent(pos);
  }


  /**
   * Sets player direction.
   *
   * @param direction string
   */
  public void setPlayerDirection(String direction) {
    getMyPlayer().setDirection(direction);
  }

  /**
   * Checks if given x,y coordinate is a wall.
   *
   * @param x
   * @param y
   * @return boolean for whether it's a wall
   */
  public boolean isWall(int x, int y) {
    return myGameStateData.isWall(x, y);
  }

  /**
   * @param pos current position to search around
   * @return list of available move targets
   */
  public List<Position> getPotentialMoveTargets(Position pos) {
    List<Position> potentialSpots = new ArrayList<>();
    if (isInBounds(pos.getCoords()[0] + 1,
        pos.getCoords()[1]) && (!isWall(pos.getCoords()[0] + 1, pos.getCoords()[1]) || isWall(pos.getCoords()[0] + 1, pos.getCoords()[1]) && findAgent(new Position(pos.getCoords()[0] + 1, pos.getCoords()[1])).getState() == 1)) {
      potentialSpots.add(new Position(pos.getCoords()[0] + 1, pos.getCoords()[1]));
    }
    if (isInBounds(pos.getCoords()[0] - 1,
        pos.getCoords()[1]) && (!isWall(pos.getCoords()[0] - 1, pos.getCoords()[1]) || isWall(pos.getCoords()[0] - 1, pos.getCoords()[1]) && findAgent(new Position(pos.getCoords()[0] - 1, pos.getCoords()[1])).getState() == 1)) {
      potentialSpots.add(new Position(pos.getCoords()[0] - 1, pos.getCoords()[1]));
    }
    if (isInBounds(pos.getCoords()[0],
        pos.getCoords()[1] + 1) && (!isWall(pos.getCoords()[0], pos.getCoords()[1] + 1) || isWall(pos.getCoords()[0], pos.getCoords()[1] + 1) && findAgent(new Position(pos.getCoords()[0], pos.getCoords()[1] + 1)).getState() == 1)) {
      potentialSpots.add(new Position(pos.getCoords()[0], pos.getCoords()[1] + 1));
    }
    if (isInBounds(pos.getCoords()[0],
        pos.getCoords()[1] - 1) && (!isWall(pos.getCoords()[0], pos.getCoords()[1] - 1) || isWall(pos.getCoords()[0], pos.getCoords()[1] - 1) && findAgent(new Position(pos.getCoords()[0], pos.getCoords()[1] - 1)).getState() == 1)) {
      potentialSpots.add(new Position(pos.getCoords()[0], pos.getCoords()[1] - 1));
    }
    return potentialSpots;
  }

  /**
   * @return controllable player.
   */
  public Agent getMyPlayer() {
    return myGameStateData.getMyPlayer();
  }

  /**
   * Updates view consumers for pacman.
   */
  public void updateHandlers() {
    getPacman().updateConsumer();
//    for (Agent a : myOtherAgents) a.updateConsumer();
    for (Agent wall : myGameStateData.getMyWallStates()) wall.updateConsumer();
  }

  public boolean isFood(int x, int y) {
    for (Agent pellet : myGameStateData.getMyRequiredPelletStates()) {
      //if not consumed yet
      if (pellet.getState() == 1) {
        //if collides
        if (pellet.getPosition().getCoords()[0] == x && pellet.getPosition().getCoords()[1] == y) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Deletes foods from the GameStateData object
   *
   * @param positions food positions
   */
  public void deleteFoods(List<Position> positions) {
    myGameStateData.getMyRequiredPelletStates()
        .removeIf(food -> positions.contains(food.getPosition()));
    myGameStateData.getMyOptionalPelletStates()
        .removeIf(food -> positions.contains(food.getPosition()));
  }

  /**
   * @return number of required pellets left
   */
  public int getRequiredPelletsLeft() {
    return myGameStateData.getFoodLeft();
  }

  /**
   * @return whether game is in super state
   */
  public boolean isSuper() {
    return myGameStateData.isSuper();
  }

  /**
   * @return pacman
   */
  public Agent getPacman() {
    return myGameStateData.getAgents().get(0);
  }

  /**
   * @return list of ghosts
   */
  public List<Agent> getGhosts() {
    return myGameStateData.getAgents().subList(1, myGameStateData.getAgents().size());
  }

  /**
   * @return list of consumables
   */
  public List<Consumable> getFood() {
    List<Consumable> allFoods = new ArrayList<>(myGameStateData.getMyRequiredPelletStates());
    allFoods.addAll(myGameStateData.getMyOptionalPelletStates());
    return allFoods;
  }

  /**
   * @return list of walls
   */
  public List<Agent> getWalls() {
    return myGameStateData.getMyWallStates();
  }

  /**
   * @return number of pacman lives
   */
  public int getLives() {
    return myGameStateData.getPacmanLives();
  }

  /**
   * decreases number of pacman lives
   */
  public void decreaseLives() {
    myGameStateData.decreaseLives();
  }

  /**
   * resets ghost position to initial states
   */
  public void resetGhosts() {
    int i = 1;
    for (Agent a : getGhosts()) {
      a.setCoords(myGameStateData.getMyInitAgentPositions().get(i));
      a.setState(ALIVE_STATE);
      i++;
    }
    getMyPlayer().setStrategy(new Controllable());
  }

  /**
   * resets pacman to intitial states
   */
  public void resetPacman() {
    getPacman().setState(ALIVE_STATE);
    getPacman().setCoords(myGameStateData.getMyInitAgentPositions().get(0));
  }
}
