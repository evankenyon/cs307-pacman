package ooga.model.movement;

import java.util.ArrayList;
import java.util.List;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

/**
 * Implements one type of automatic movement for agent.
 */
public class Random implements Movable {
  private List<String> myDirections;

  @Override
  public Position move(Position pos) {
    myDirections = new ArrayList<>();
    myDirections.add("left");
    myDirections.add("right");
    myDirections.add("up");
    myDirections.add("down");
    myDirections.add(pos.getDirection());
    int[] coords = pos.getCoords();
    String newDirection = myDirections.get((int) (Math.random() * 5));
    return handleMovement(coords, newDirection);
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
