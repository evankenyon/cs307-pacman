package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.interfaces.Game;

public class VanillaGame implements Game {

  private static final String BOARD_CONFIGURATION = "board";
  private GameBoard myBoard;
  private List<Consumer<String>> myObservers;

  //private GameScore myScore; potential data structure to hold score, highscore, time played, etc.?

  public VanillaGame(Data vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myBoard = new GameBoard(vanillaGameData);
  }

  public void initializeGame() {

  }

  public void step() {
    myBoard.moveAll();
    updateHandlers();
  }

  public boolean isWin() {
    return myBoard.checkWin();
  }

  public boolean isLoss() {
    return false;
  }

  private void updateHandlers() {
    //update all view handlers
    myBoard.getGameState().updateHandlers();
  }

  public GameBoard getBoard() {
    return myBoard;
  }
}

