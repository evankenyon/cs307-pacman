package ooga.model.movement;

import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

/**
 * Implements one type of automatic movement for agent.
 */
public class Random implements Movable {

  @Override
  public Position move(Position pos) {
    int[] coords = pos.getCoords();
    String currentDirection = pos.getDirection();
    return handleMovement(coords, currentDirection);
  }

  private Position handleMovement(int[] coordinates, String currentDirection) {
    //refactor this to not use switch case statements potentially?
    //also argument that we never really need it to recognize other keys to move so it doesn't need to be flexible
    return switch (currentDirection) {
      case "left" -> new Position((coordinates[0] - 1), coordinates[1]);
      case "right" -> new Position((coordinates[0] + 1), coordinates[1]);
      case "up" -> new Position(coordinates[0], (coordinates[1] - 1));
      case "down" -> new Position(coordinates[0], (coordinates[1] + 1));
      default -> null;
    };
  }

  @Override
  public boolean isNull() {
    return false;
  }
}
