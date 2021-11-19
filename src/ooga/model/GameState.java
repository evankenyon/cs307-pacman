package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.factories.AgentFactory;
import ooga.factories.ConsumableFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;

public class GameState {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";


  private int myRows;
  private int myCols;
  private List<Agent> myOtherAgents;
  private Agent myPlayer;
  private List<Agent> myWalls;
  private List<Consumable> myConsumables;
  private AgentFactory agentFactory;

  public GameState(DataInterface vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myRows = calculateDimension(vanillaGameData.getWallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.getWallMap(), 0) + 1;
    myOtherAgents = new ArrayList<>();
    myWalls = new ArrayList<>();
    agentFactory = new AgentFactory();
    createGrid(vanillaGameData.getWallMap());
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

  private void createGrid(Map<String, List<Position>> initialStates)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    for (String state : initialStates.keySet()) {
      for (Position position : initialStates.get(state)) {
        addAgentToSpecificList(state, position.getCoords()[0],
            position.getCoords()[1]);
      }
    }
  }

  private Agent addAgentToSpecificList(String agent, int x, int y)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ResourceBundle types = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, TYPES_FILENAME));
    Method method = this.getClass()
        .getDeclaredMethod(String.format("addTo%s", types.getString(agent)), String.class,
            int.class, int.class);
    method.setAccessible(true);
    return (Agent) method.invoke(this, agent, x, y);
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
    if (myPlayer.getPosition().getCoords()[0] == pos.getCoords()[0] &&  myPlayer.getPosition().getCoords()[1] == pos.getCoords()[1]) {
      return myPlayer;
    }
    Agent potentialAgent = null;
    for (Agent agent : myOtherAgents) {
      if(agent.getPosition().getCoords()[0] == pos.getCoords()[0] &&  agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }

    for (Agent agent : myWalls) {
      if(agent.getPosition().getCoords()[0] == pos.getCoords()[0] &&  agent.getPosition().getCoords()[1] == pos.getCoords()[1]) {
        potentialAgent = agent;
      }
    }
    return potentialAgent;
  }

  //TODO : actually implement
  public List<Consumable> getAllConsumables() {
    return new ArrayList<>();
  }

//  public void setPlayerDirection(String direction){
//    myPlayer.setDirection(direction);
//  }
}
