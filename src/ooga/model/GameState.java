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
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

public class GameState {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";


  private int myRows;
  private int myCols;
  private List<List<Agent>> myGrid;
  private List<Movable> myMovables;
  private List<Consumable> allConsumables;

  public GameState(DataInterface vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myRows = calculateDimension(vanillaGameData.getWallMap(), 1) + 1;
    myCols = calculateDimension(vanillaGameData.getWallMap(), 0) + 1;
    myMovables = new ArrayList<>();
    allConsumables = new ArrayList<>();
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
    ResourceBundle types = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, TYPES_FILENAME));
    Method method = this.getClass().getDeclaredMethod(String.format("addTo%s", types.getString(agent)), String.class, int.class, int.class);
    method.setAccessible(true);
    method.invoke(this, agent, x, y);
  }

  private void addToConsumables(String agent, int x, int y)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Consumable consumable = new ConsumableFactory().createConsumable(agent, x, y);
    allConsumables.add(consumable);
  }

  public List<List<Agent>> getMyGrid() {
    return myGrid;
  }

  public Agent findAgent(Position pos) {
    return myGrid.get(pos.getCoords()[1]).get(pos.getCoords()[0]);
  }


  public List<Consumable> getAllConsumables() {
    return allConsumables;
  }
}
