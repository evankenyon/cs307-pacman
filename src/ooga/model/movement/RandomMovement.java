package ooga.model.movement;

import java.util.ArrayList;
import java.util.List;
import ooga.model.GameState;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;
import ooga.model.util.Utility;

/**
 * Implements one type of automatic movement for agent.
 */
public class RandomMovement implements Movable {

  @Override
  public Position move(GameState state, Position pos) {
    List<String> myDirections = new ArrayList<>();
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
    return Utility.translateDirectionToPosition(coordinates, currentDirection);
  }

}
