package ooga.model;

import java.util.ArrayList;
import java.util.List;
import ooga.model.util.AgentInfo;

public class GameBoard {

  private int myRows;
  private int myCols;
  private List<List<Agent>> myGrid;
  private Controllable myPlayer;

  public GameBoard(int rows, int cols, List<List<String>> initialStates, Controllable player) {
    myRows = rows;
    myCols = cols;
    createGrid(initialStates);
    myPlayer = player;
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
   * @param info
   * @return
   */
  public Agent findAgent(AgentInfo info) {
    return myGrid.get(info.getY()).get(info.getX());
  }

  /**
   * Example List<List<String>> <<wall,wall,wall,wall,wall> <wall,dot,dot,dot,wall>
   * <wall,dot,player,dot,wall> <wall,dot,dot,dot,wall> <wall,wall,wall,wall,wall>>
   **/
  private void createGrid(List<List<String>> initialStates) {
    String agentType;
    for (int row = 0; row < myRows; row++) {
      ArrayList<Agent> tempRow = new ArrayList<>();
      for (int col = 0; col < myCols; col++) {
        agentType = initialStates.get(row).get(col);
        if (agentType.equals("player")) {
          tempRow.add(myPlayer);
        } else {
          Agent agent = null;//use reflection to instantiate the right agent types (wall, ghost, Pacman...) This means initialState strings have to equal class names.
          tempRow.add(agent);
        }
      }
      myGrid.add(tempRow);
    }
  }
}
