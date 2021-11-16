package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.interfaces.Controllable;
import ooga.model.interfaces.Game;

public class VanillaGame implements Game {

  private static final String BOARD_CONFIGURATION = "board";
  private Controllable myPlayer;
  private GameBoard myBoard;
  private List<Consumer<String>> myObservers;

  //private GameScore myScore; potential data structure to hold score, highscore, time played, etc.?

  public VanillaGame(VanillaGameDataInterface vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myBoard = new GameBoard(vanillaGameData.getWallMap(), vanillaGameData.getPlayer());
  }

  public void initializeGame() {

  }

  /**
   * Sets player direction.
   */
  public void setPlayerDirection(String direction) {
    myBoard.setPlayerDirection(direction);
  }

  public void step() {
    //make every single move on the backend
    myBoard.moveAll();
    //update all view handlers
    updateHandlers();
  }

  public boolean isWin() {
    return false;
  }

  public boolean isLoss() {
    return false;
  }

  private void updateHandlers() {
    //update all view handlers
  }
}

