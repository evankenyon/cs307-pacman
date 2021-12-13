package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Game;
import ooga.model.util.Position;

public class GameEngine implements Game {

  private final GameBoard myBoard;
  private final Map<String, Boolean> pelletInfoMap;

  /**
   * Constructor for main GameEngine
   *
   * @param vanillaGameData GameData from controller
   */
  public GameEngine(GameData vanillaGameData)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myBoard = new GameBoard(vanillaGameData);
    pelletInfoMap = vanillaGameData.pelletInfo();
  }

  /**
   * @return pellet info that explains which pellets are required
   */
  public Map<String, Boolean> getPelletInfo() {
    return pelletInfoMap;
  }

  /**
   * Executes one step of the game of pacman.
   */
  public void step() {
    myBoard.movePawns();
    myBoard.checkCollisions();
    myBoard.checkGameEnd();
    updateHandlers();
  }

  private void updateHandlers() {
    myBoard.getGameState().updateHandlers();
  }


  /**
   * @return board of the game where logic lives
   */
  public GameBoard getBoard() {
    return myBoard;
  }

  /**
   * Sets direction of player.
   *
   * @param direction for the player (mostly for view to update the image)
   */
  public void setPlayerDirection(String direction) {
    myBoard.setPlayerDirection(direction);
  }

  /**
   * Finds one agent in the board with a given position.
   *
   * @param position of agent to find
   * @return agent object
   */
  public Agent findAgent(Position position) {
    return myBoard.getGameState().findAgent(position);
  }

}

