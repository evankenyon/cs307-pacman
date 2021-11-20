package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameBoard {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";
  private static final Logger LOG = LogManager.getLogger(GameBoard.class);

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
    List<Agent> movers = new ArrayList<>();
    movers.add(myState.getMyPlayer());
    movers.addAll(myState.getMyWalls());
    movers.addAll(myState.getMyOtherAgents());
    for (Agent agent : movers) {
      Position newPosition = agent.step();
      if (newPosition != null) {
        if (checkMoveValidity(newPosition)) {
          agent.setCoords(newPosition);
        }
      }
    }
  }


  public void setPlayerDirection(String direction) {
    LOG.info("setting direction in board to {}", direction);
    myState.setPlayerDirection(direction);
  }

  private boolean checkMoveValidity(Position newPosition) {
    //TODO: add cases for walls, other overlaps, etc
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return myState.checkGridBounds(x, y) && !myState.checkWallCollision(x, y);
  }

  public GameState getGameState() {
    return myState;
  }
}
