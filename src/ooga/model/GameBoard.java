package ooga.model;

import java.lang.reflect.InvocationTargetException;
import ooga.model.util.Position;

public class GameBoard {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";

  GameState myState;


  // TODO: handle exceptions
  public GameBoard(DataInterface vanillaGameData)
      throws
      ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myState = new GameState(vanillaGameData);
  }


  //TODO: implement
  public boolean checkWin() {
    return myState.getAllConsumables().isEmpty();
  }

  //move every agent in the board by one step
  public void moveAll() {
//    for (List<Agent> row : myState.getMyGrid()) {
//      for (Agent agent : row) {
//        Position newPosition = agent.step();
//        if (checkMoveValidity(newPosition)) {
//          agent.setCoords(newPosition);
//        }
//      }
//    }
  }


//  public void setPlayerDirection(String direction) {
//    myState.setPlayerDirection(direction);
//  }

  private boolean checkMoveValidity(Position newPosition) {
    //TODO: add cases for walls, other overlaps, etc
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return myState.checkGridBounds(x, y);
  }

  public GameState getGameState() {
    return myState;
  }
}
