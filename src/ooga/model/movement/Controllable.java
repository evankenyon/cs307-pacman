package ooga.model.movement;

import ooga.controller.IO.keyTracker;
import ooga.model.GameState;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;
import ooga.model.util.Utility;

/**
 * Allows agent to be controlled via keystrokes.
 */
public class Controllable implements Movable {

  private final keyTracker myKeyTracker;

  /**
   * Constructor for controllable intializing keytracker
   */
  public Controllable() {
    myKeyTracker = new keyTracker();
  }

  @Override
  public Position move(GameState state, Position pos) {
    int[] coords = pos.getCoords();
    String currentDirection = pos.getDirection();
    return handleMovement(coords, currentDirection);
  }

  private Position handleMovement(int[] coordinates, String currentDirection) {
    return Utility.translateDirectionToPosition(coordinates, currentDirection);
  }

}
