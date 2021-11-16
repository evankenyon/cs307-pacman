package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.factories.ControllableFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Controllable;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

public class GameBoard {
  private int myRows;
  private int myCols;
  private List<List<Agent>> myGrid;
  private Controllable myPlayer;
  private List<Movable> myMoveables;

  // TODO: handle exceptions
  public GameBoard(Map<String, List<Position>> initialStates, String player)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myPlayer = new ControllableFactory().createControllable(player, initialStates.get(player).get(0).getCoords()[1], initialStates.get(player).get(0).getCoords()[0]);
    myRows = calculateDimension(initialStates, 1) + 1;
    myCols = calculateDimension(initialStates, 0) + 1;
    createGrid(initialStates);
  }

  private int calculateDimension(Map<String, List<Position>> initialStates, int dim) {
    int maxCol = 0;
    for(String key : initialStates.keySet()) {
      for(Position position : initialStates.get(key)) {
        maxCol = Math.max(position.getCoords()[dim], maxCol);
      }
    }
    return maxCol;
  }

  //move every agent in the board by one step
  public void moveAll() {
    for (List<Agent> row : myGrid) {
      for (Agent agent : row) {
        agent.step();
      }
    }
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

  public void setPlayerDirection(String direction) {
    myPlayer.setDirection(direction);
  }

  /**
   * Example List<List<String>> <<wall,wall,wall,wall,wall> <wall,dot,dot,dot,wall>
   * <wall,dot,player,dot,wall> <wall,dot,dot,dot,wall> <wall,wall,wall,wall,wall>>
   **/
  private void createGrid(Map<String, List<Position>> initialStates) {
    Agent[][] myGridArr = new Agent[myRows][myCols];
    for (String state : initialStates.keySet()) {
      for (Position position : initialStates.get(state)) {
        myGridArr[position.getCoords()[1]][position.getCoords()[0]] = new AgentFactory().createAgent(state, position.getCoords()[0], position.getCoords()[1]);
      }
    }
    myGrid = new ArrayList<>();
    for(Agent[] myGridArrRow : myGridArr) {
      myGrid.add(List.of(myGridArrRow));
    }
  }

  public List<List<Agent>> getMyGrid() {
    return myGrid;
  }
}
