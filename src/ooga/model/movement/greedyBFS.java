package ooga.model.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ooga.model.GameState;
import ooga.model.interfaces.Consumable;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements one type of automatic movement for agent.
 */
public class greedyBFS extends BFS implements Movable {

  private static final Logger LOG = LogManager.getLogger(greedyBFS.class);


  @Override
  public Position move(GameState state, Position currentPos) {
    List<Consumable> foodsList = state.getFood();
    int min = Integer.MAX_VALUE;
    Consumable foodToChase = null;

    for (Consumable food : foodsList) {
      int distanceHeuristicBetweenFood = 0;
      int xAway = Math.abs(currentPos.getCoords()[0] - food.getPosition().getCoords()[0]);
      int yAway = Math.abs(currentPos.getCoords()[1] - food.getPosition().getCoords()[1]);
      distanceHeuristicBetweenFood = xAway + yAway;
      if (min > distanceHeuristicBetweenFood) {
        min = distanceHeuristicBetweenFood;
        foodToChase = food;
      }
    }

    if (foodToChase == null) {
      return currentPos;
    } else {
      List<Position> optimalPath = new ArrayList<>();
      Map<Position, Position> myPath = doBFS(foodToChase.getPosition(), currentPos, state);

      Position first = foodToChase.getPosition();

      while (first != null) {
        optimalPath.add(first);
        first = myPath.get(first);
      }

      optimalPath = getNewPathFromNewFood(state, currentPos, optimalPath);

      if (optimalPath.size() == 1) {
        //this should never happen unless literally stuck in a box
        return optimalPath.get(0);
      } else {
        //take second index which is next step from currentPos
        Position positionToGo = optimalPath.get(optimalPath.size() - 2);
        handleDirection(state, currentPos, positionToGo);
        return positionToGo;
      }
    }
  }

  private List<Position> getNewPathFromNewFood(GameState state, Position currentPos,
      List<Position> optimalPath) {
    List<Consumable> foodsList;
    Map<Position, Position> myPath;
    Consumable foodToChase;
    Position first;
    while (optimalPath.size() <= 1) {
      foodsList = state.getFood();
      if (foodsList.isEmpty()) {
        break;
      }
      foodToChase = foodsList.get(new Random().nextInt(foodsList.size()));
      optimalPath = new ArrayList<>();
      myPath = doBFS(foodToChase.getPosition(), currentPos, state);

      first = foodToChase.getPosition();
      while (first != null) {
        optimalPath.add(first);
        first = myPath.get(first);
      }
    }
    return optimalPath;
  }

  // to set the correct direction for pacman view.
  private void handleDirection(GameState state, Position currentPos, Position targetPosition) {
    if (currentPos.getCoords()[0] == targetPosition.getCoords()[0]
        && currentPos.getCoords()[1] < targetPosition.getCoords()[1]) {
      state.findAgent(currentPos).setDirection("down");
    } else if (currentPos.getCoords()[0] == targetPosition.getCoords()[0]
        && currentPos.getCoords()[1] > targetPosition.getCoords()[1]) {
      state.findAgent(currentPos).setDirection("up");
    } else if (currentPos.getCoords()[0] < targetPosition.getCoords()[0]
        && currentPos.getCoords()[1] == targetPosition.getCoords()[1]) {
      state.findAgent(currentPos).setDirection("right");
    } else if (currentPos.getCoords()[0] > targetPosition.getCoords()[0]
        && currentPos.getCoords()[1] == targetPosition.getCoords()[1]) {
      state.findAgent(currentPos).setDirection("left");
    }
  }
}
