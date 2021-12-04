package ooga.model;

import java.lang.reflect.InvocationTargetException;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Game;
import ooga.model.util.Position;

public class VanillaGame implements Game {

  private static final String BOARD_CONFIGURATION = "board";
  private final GameBoard myBoard;

  public VanillaGame(Data vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myBoard = new GameBoard(vanillaGameData);
  }

  public void step() {
    myBoard.moveAgents();
    myBoard.checkWin();
    myBoard.checkLoss();
    updateHandlers();
  }

  private void updateHandlers() {
    //update view handlers
    myBoard.getGameState().updateHandlers();
  }

  public Agent findAgent(Position position) {
    return myBoard.getGameState().findAgent(position);
  }

  public void setPlayerDirection(String direction) {
    myBoard.setPlayerDirection(direction);
  }

  public GameBoard getBoard() {
    return myBoard;
  }

  //for when ghost becomes player/super pacman
//  public void setWinCondition(){}
}

