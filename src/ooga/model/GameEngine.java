package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Game;
import ooga.model.util.Position;

public class GameEngine implements Game {

  private GameBoard myBoard;
  private Map<String, Boolean> pelletInfoMap;

  /**
   *
   * @param vanillaGameData
   * @throws ClassNotFoundException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public GameEngine(GameData vanillaGameData)
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
    myBoard.checkGameEnd();
    updateHandlers();
  }

  private void updateHandlers() {
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

