package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameState {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";

  private GameStateData myGameStateData;
  private final int DX = 1;

  private final int myRows;
  private final int myCols;

  private final AgentFactory agentFactory;
  private static final Logger LOG = LogManager.getLogger(GameState.class);
  private int myPacScore;
  private int myGhostScore;

  public GameState(Data vanillaGameData)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    System.out.println(vanillaGameData.wallMap().toString());

    myGameStateData = new GameStateData();
    myGameStateData.initialize(vanillaGameData.wallMap(), vanillaGameData.pelletInfo());

    myRows = calculateDimension(vanillaGameData.wallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.wallMap(), 0) + 1;
//    myOtherAgents = new ArrayList<>();
    agentFactory = new AgentFactory();
//    populateLists(vanillaGameData.wallMap());
  }

  public boolean isInBounds(int x, int y) {
    if (x >= myRows || y >= myCols) {
      return false;
    } else if (x < 0 || y < 0) {
      return false;
    }
    return true;
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


  public Agent findAgent(Position pos) {
    return myGameStateData.findAgent(pos);
  }

  public void setPlayerDirection(String direction) {
    getPacman().setDirection(direction);
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

  public boolean isWall(int x, int y) {
    return myGameStateData.isWall(x, y);
  }

  public Agent getMyPlayer() {
    return getPacman();
  }

  public int getPacmanLives() {
    return myGameStateData.getPacmanLives();
  }

  public void updateHandlers() {
    getPacman().updateConsumer();
//    for (Agent a : myOtherAgents) a.updateConsumer();
//    for (Agent wall : myWalls) wall.updateConsumer();
  }

  public boolean isFood(int x, int y) {
    for (Agent pellet : myGameStateData.getMyPelletStates()) {
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

  public Pacman getPacman() {
    return (Pacman) myGameStateData.getAgents().get(0);
  }

  public List<Agent> getGhosts() {
    return myGameStateData.getAgents().subList(1, myGameStateData.getAgents().size());
  }

  public List<Consumable> getFood() {
    return myGameStateData.getMyPelletStates();
  }

  public List<Agent> getWalls() {
    return myGameStateData.getMyWallStates();
  }

  public boolean isSuper() {
    return myGameStateData.isSuper();
  }

  public void updateGameStateData(GameStateData newData) {
    myGameStateData = newData;
  }
}
