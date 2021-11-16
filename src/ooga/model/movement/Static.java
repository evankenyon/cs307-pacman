package ooga.model.movement;

import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

/**
 * Implements not moving for agents.
 */
public class Static implements Movable {

  @Override
  public Position move(Position info) {
    return null;
  }

  @Override
  public boolean isNull() {
    return true;
  }
}
