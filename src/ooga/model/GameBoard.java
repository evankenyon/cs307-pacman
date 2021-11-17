package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.factories.AgentFactory;
import ooga.factories.ConsumableFactory;
import ooga.factories.ControllableFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.interfaces.Controllable;
import ooga.model.util.Position;

public class GameBoard {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";


  private int myRows;
  private int myCols;
  private List<List<Agent>> myGrid;
  private Controllable myPlayer;
  private List<Agent> myAgents;
  private List<Consumable> allConsumables;
  private List<Consumable> requiredConsumables;
  private Map<String, Boolean> consumableInfo;

  // TODO: handle exceptions
  public GameBoard(DataInterface vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myRows = calculateDimension(vanillaGameData.getWallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.getWallMap(), 0) + 1;
    myAgents = new ArrayList<>();
    allConsumables = new ArrayList<>();
    requiredConsumables = new ArrayList<>();
    consumableInfo = vanillaGameData.getPelletInfo();
    createGrid(vanillaGameData.getWallMap());
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

  //TODO: implement
  public boolean checkWin() {
    return requiredConsumables.isEmpty();
  }

  //move every agent in the board by one step
  public void moveAll() {
    for (List<Agent> row : myGrid) {
      for (Agent agent : row) {
        Position newPosition = agent.step();
        if (checkMoveValidity(newPosition)) {
          agent.setCoords(newPosition);
        }
      }
    }
  }

  private boolean checkMoveValidity(Position newPosition) {
    //TODO: add cases for walls, other overlaps, etc
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return checkGridBounds(x, y);
  }

  private boolean checkGridBounds(int x, int y) {
    if (x > myRows || y > myCols) {
      return false;
    } else if (x < 0 || y < 0) {
      return false;
    }
    return true;
  }

  /**
   * Finds agent in the grid with the same given agent info.
   *
   * @param pos
   * @return
   */
  public Agent findAgent(Position pos) {
    return myGrid.get(pos.getCoords()[1]).get(pos.getCoords()[0]);
  }

  /**
   * Set pacman direction for movement and display
   *
   * @param direction string
   */
  public void setPlayerDirection(String direction) {
    myPlayer.setDirection(direction);
  }

  /**
   * Example List<List<String>> <<wall,wall,wall,wall,wall> <wall,dot,dot,dot,wall>
   * <wall,dot,player,dot,wall> <wall,dot,dot,dot,wall> <wall,wall,wall,wall,wall>>
   **/
  private void createGrid(Map<String, List<Position>> initialStates)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Agent[][] myGridArr = new Agent[myRows][myCols];
    for (String state : initialStates.keySet()) {
      for (Position position : initialStates.get(state)) {
        myGridArr[position.getCoords()[1]][position.getCoords()[0]] = new AgentFactory().createAgent(
            state, position.getCoords()[0], position.getCoords()[1]);
        addAgentToSpecificList(state, position.getCoords()[0], position.getCoords()[1]);
      }
    }
    myGrid = new ArrayList<>();
    for (Agent[] myGridArrRow : myGridArr) {
      myGrid.add(List.of(myGridArrRow));
    }
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

  private void addToConsumables(String agent, int x, int y)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Consumable consumable = new ConsumableFactory().createConsumable(agent, x, y);
    allConsumables.add(consumable);
    if (consumableInfo.get(agent)) {
      requiredConsumables.add(consumable);
    }
  }

  private void addToAgents(String agent, int x, int y) throws InputMismatchException {
    myAgents.add(new AgentFactory().createAgent(agent, x, y));
  }

  private void addToPlayer(String agent, int x, int y)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myPlayer = new ControllableFactory().createControllable(agent, x, y);
  }

  public List<List<Agent>> getMyGrid() {
    return myGrid;
  }
}
