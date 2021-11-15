package ooga.model.movement;

import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

/**
 * Implements one type of automatic movement for agent.
 */
public class Automatic implements Movable {

  @Override
  public Position move(Position pos) {
    //some movement algorithm?
    return null;
  }

  @Override
  public boolean isNull() {
    return false;
  }
}
