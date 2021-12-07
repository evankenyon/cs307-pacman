package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameState {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";
  private static final int ALIVE_STATE = 1;

  private GameStateData myGameStateData;
  private final int DX = 1;

  private final int myRows;
  private final int myCols;
  private final AgentFactory agentFactory;
  private static final Logger LOG = LogManager.getLogger(GameState.class);

  public GameState(GameData vanillaGameData)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//    System.out.println(vanillaGameData.wallMap().toString());

    myGameStateData = new GameStateData();
    myGameStateData.initialize(vanillaGameData);
    implementRunnables();

    myRows = calculateDimension(vanillaGameData.wallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.wallMap(), 0) + 1;

    agentFactory = new AgentFactory();
  }

  public boolean isInBounds(int x, int y) {
    if (x > myRows || y > myCols) {
      return false;
    } else if (x < 0 || y < 0) {
      return false;
    }
    return true;
  }

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

//  private void populateLists(Map<String, List<Position>> initialStates)
//      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//    for (String state : initialStates.keySet()) {
//      for (Position position : initialStates.get(state)) {
//        addAgentToSpecificList(state, position.getCoords()[0],
//            position.getCoords()[1]);
//      }
//    }
//  }

//  private void addAgentToSpecificList(String agent, int x, int y)
//      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//    ResourceBundle types = ResourceBundle.getBundle(
//        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, TYPES_FILENAME));
//    Method method = this.getClass()
//        .getDeclaredMethod(String.format("addTo%s", types.getString(agent)), String.class,
//            int.class, int.class);
//    method.setAccessible(true);
//    method.invoke(this, agent, x, y);
//  }

//  private void addToOtherAgents(String agent, int x, int y) {
//    myOtherAgents.add(agentFactory.createAgent(agent, x, y));
//  }


  private void implementRunnables() {
    for (Agent a : getFood()) {
      Runnable r = () -> setSuperState();
      a.addRunnable(r);
    }
  }

//  private void processPlayerRunnable() {
//    //TODO: Change for when player is a ghost not Pacman
//    myPlayer.setState(SUPER_STATE);
//  }

  private void setSuperState() {
    myGameStateData.setSuper();
    for (Agent ghost : getGhosts()) {
      ghost.setState(2);
    }
  }


  public Agent findAgent(Position pos) {
    return myGameStateData.findAgent(pos);
  }

  public void setPlayerDirection(String direction) {
    getPacman().setDirection(direction);
  }

  public boolean isWall(int x, int y) {
    return myGameStateData.isWall(x, y);
  }

  public List<Position> getPotentialMoveTargets(Position pos) {
    List<Position> potentialSpots = new ArrayList<>();
    if (isInBounds(pos.getCoords()[0] + 1,
        pos.getCoords()[1]) && !isWall(pos.getCoords()[0] + 1, pos.getCoords()[1])) {
      potentialSpots.add(new Position(pos.getCoords()[0] + 1, pos.getCoords()[1]));
    }
    if (isInBounds(pos.getCoords()[0] - 1,
        pos.getCoords()[1]) && !isWall(pos.getCoords()[0] - 1, pos.getCoords()[1])) {
      potentialSpots.add(new Position(pos.getCoords()[0] - 1, pos.getCoords()[1]));
    }
    if (isInBounds(pos.getCoords()[0],
        pos.getCoords()[1] + 1) && !isWall(pos.getCoords()[0], pos.getCoords()[1] + 1)) {
      potentialSpots.add(new Position(pos.getCoords()[0], pos.getCoords()[1] + 1));
    }
    if (isInBounds(pos.getCoords()[0],
        pos.getCoords()[1] - 1) && !isWall(pos.getCoords()[0], pos.getCoords()[1] - 1)) {
      potentialSpots.add(new Position(pos.getCoords()[0], pos.getCoords()[1] - 1));
    }
    return potentialSpots;
  }

//  public List<Agent> getMyOtherAgents() {
//    return myOtherAgents;
//  }

  public Agent getMyPlayer() {
    return getPacman();
  }

  public void updateHandlers() {
    getPacman().updateConsumer();
//    for (Agent a : myOtherAgents) a.updateConsumer();
//    for (Agent wall : myWalls) wall.updateConsumer();
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

  public void deleteFoods(List<Position> positions) {
    myGameStateData.getMyRequiredPelletStates()
        .removeIf(food -> positions.contains(food.getPosition()));
  }

  public int getRequiredPelletsLeft() {
    return myGameStateData.getFoodLeft();
  }

  public boolean isSuper() {
    return myGameStateData.isSuper();
  }

  public Agent getPacman() {
    return myGameStateData.getAgents().get(0);
  }

  public List<Agent> getGhosts() {
    return myGameStateData.getAgents().subList(1, myGameStateData.getAgents().size());
  }

  public List<Consumable> getFood() {
    return myGameStateData.getMyRequiredPelletStates();
  }

  public List<Agent> getWalls() {
    return myGameStateData.getMyWallStates();
  }

  public int getLives() {
    return myGameStateData.getPacmanLives();
  }

  public void decreaseLives() {
    myGameStateData.decreaseLives();
  }

  public void resetGhosts() {
    int i = 1;
    for (Agent a : getGhosts()) {
      a.setCoords(myGameStateData.getMyInitAgentPositions().get(i));
      a.setState(ALIVE_STATE);
      i++;
    }
  }

  public void resetPacman() {
    getPacman().setState(ALIVE_STATE);
    getPacman().setCoords(myGameStateData.getMyInitAgentPositions().get(0));
  }
}
