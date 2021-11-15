package ooga.model.movement;

import ooga.controller.IO.keyTracker;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

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
  public Position move(Position pos) {
    int newX = myKeyTracker.getNewX(pos.getCoords()[0]);
    int newY = myKeyTracker.getNewY(pos.getCoords()[1]);
    return new Position(newX, newY);
  }

  @Override
  public boolean isNull() {
    return false;
  }
}
