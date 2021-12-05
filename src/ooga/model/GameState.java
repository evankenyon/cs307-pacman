package ooga.model;

import static ooga.model.agents.consumables.Ghost.AFRAID_STATE;
import static ooga.model.agents.players.Pacman.SUPER_STATE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.factories.AgentFactory;
import ooga.model.agents.consumables.Ghost;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameState {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";

  private final int myRows;
  private final int myCols;
  private List<Agent> myOtherAgents;
  private List<Agent> myGhostAgents;

  private Agent myPlayer;
  private List<Agent> myWalls;
  private List<Consumable> myConsumables;
  private final AgentFactory agentFactory;
  private static final Logger LOG = LogManager.getLogger(GameState.class);

  public GameState(GameData vanillaGameData)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    System.out.println(vanillaGameData.wallMap().toString());
    myRows = calculateDimension(vanillaGameData.wallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.wallMap(), 0) + 1;
    myOtherAgents = new ArrayList<>();
    myGhostAgents = new ArrayList<>();
    myWalls = new ArrayList<>();
    agentFactory = new AgentFactory();
    populateLists(vanillaGameData.wallMap());
  }

  public boolean checkGridBounds(int x, int y) {
    if (x > myRows || y > myCols) {
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

  private void populateLists(Map<String, List<Position>> initialStates)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    for (String state : initialStates.keySet()) {
      for (Position position : initialStates.get(state)) {
        addAgentToSpecificList(state, position.getCoords()[0],
            position.getCoords()[1]);
      }
    }
    implementRunnables();
  }

  private void addAgentToSpecificList(String agent, int x, int y)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ResourceBundle types = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, TYPES_FILENAME));
    Method method = this.getClass()
        .getDeclaredMethod(String.format("addTo%s", types.getString(agent)), String.class,
            int.class, int.class);
    method.setAccessible(true);
    method.invoke(this, agent, x, y);
  }

  private void implementRunnables() {
    for (Agent a : myOtherAgents) {
      Runnable r = () -> processOtherAgentRunnable();
      a.addRunnable(r);
      if (a instanceof Ghost) myGhostAgents.add(a);
    }
    Runnable r = () -> processPlayerRunnable();
    myPlayer.addRunnable(r);
  }

  private void processPlayerRunnable() {
    //TODO: Change for when player is a ghost not Pacman
    myPlayer.setState(SUPER_STATE);
  }

  private void processOtherAgentRunnable() {
    for (Agent a : myGhostAgents) {
      a.setState(AFRAID_STATE);
    }
  }

  private void addToOtherAgents(String agent, int x, int y) {
    myOtherAgents.add(agentFactory.createAgent(agent, x, y));
  }

  private void addToWalls(String agent, int x, int y) {
    myWalls.add(agentFactory.createAgent(agent, x, y));
  }

  private void addToPlayer(String agent, int x, int y) {
    myPlayer = agentFactory.createAgent(agent, x, y);
  }

  public Agent findAgent(Position pos) {
    if (myPlayer.getPosition().getCoords()[0] == pos.getCoords()[0]
        && myPlayer.getPosition().getCoords()[1] == pos.getCoords()[1]) {
      return myPlayer;
    }
    Agent potentialAgent = null;
    for (Agent agent : myOtherAgents) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }

    for (Agent agent : myWalls) {
      if (agent.getPosition().getCoords()[0] == pos.getCoords()[0]
          && agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }
    return potentialAgent;
  }

  //TODO : actually implement
//  public List<Consumable> getAllConsumables() {
//    return new ArrayList<>();
//  }

  public void setPlayerDirection(String direction) {
    myPlayer.setDirection(direction);
  }

  public boolean checkWallCollision(int x, int y) {
    for (Agent wall : myWalls) {
      //if wall unpassable
      if (wall.getState() == 0) {
        //if collides
        if (wall.getPosition().getCoords()[0] == x && wall.getPosition().getCoords()[1] == y) {
          return true;
        }
      }
    }
    return false;
  }

  public List<Agent> getMyOtherAgents() {
    return myOtherAgents;
  }

  public Agent getMyPlayer() {
    return myPlayer;
  }

  public List<Agent> getMyWalls() {
    return myWalls;
  }

  public void updateHandlers() {
    myPlayer.updateConsumer();
    for (Agent a : myOtherAgents) a.updateConsumer();
    for (Agent wall : myWalls) wall.updateConsumer();
  }

  public boolean checkConsumables(int x, int y) {
    for (Agent pellet : myOtherAgents) {
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
}
