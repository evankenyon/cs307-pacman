package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Game;
import ooga.model.util.Position;

public class VanillaGame implements Game {

  private static final String BOARD_CONFIGURATION = "board";
  private GameBoard myBoard;
  private Map<String, Boolean> pelletInfoMap;
  private List<Consumer<String>> myObservers;

  //private GameScore myScore; potential data structure to hold score, highscore, time played, etc.?

  public VanillaGame(Data vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myBoard = new GameBoard(vanillaGameData);
    pelletInfoMap = vanillaGameData.pelletInfo();
  }

  public Map<String, Boolean> getPelletInfo() {
    return pelletInfoMap;
  }

  public void step() {
    myBoard.movePawns();
    myBoard.checkCollisions();
    isWin();
    isLoss();
    updateHandlers();
  }

  public boolean isWin() {
    return true;
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

  public void setPlayerDirection(String direction) {
    myBoard.setPlayerDirection(direction);
  }

  public Agent findAgent(Position position) {
    return myBoard.getGameState().findAgent(position);
  }
}

