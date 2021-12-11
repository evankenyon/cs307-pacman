package ooga.model.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import ooga.model.GameState;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements one type of automatic movement for agent.
 */
public class BFS implements Movable {

  private static final Logger LOG = LogManager.getLogger(BFS.class);
  private int randomCounter;

  /**
   * Constructor for BFS movement strategy.
   */
  public BFS() {
    randomCounter = 0;
  }

  @Override
  public Position move(GameState state, Position currentPos) {
    Position pacPos = state.getPacman().getPosition();
    Map<Position, Position> myPath = doBFS(pacPos, currentPos, state);
    Position first = pacPos;
    List<Position> optimalPath = new ArrayList<>();

    while (first != null) {
      optimalPath.add(first);
      first = myPath.get(first);
    }

    if (optimalPath.size() == 1) {
      //this should never happen unless literally stuck in a box
      return optimalPath.get(0);
    } else {
      //take second index which is next step from currentPos
      Position correctPositionToReturn = optimalPath.get(optimalPath.size() - 2);
      randomCounter++;
      Random rand = new Random();
      List<Position> potentialPositions = state.getPotentialMoveTargets(currentPos);
      Position potentiallyWrongPosition = potentialPositions.get(
          rand.nextInt(potentialPositions.size()));

      if (randomCounter == 4) {
        randomCounter = 0;
        return potentiallyWrongPosition;
      } else {
        return correctPositionToReturn;
      }
    }
  }

  protected Map<Position, Position> doBFS(Position targetPosition, Position currentPos,
      GameState state) {
    Queue<Position> BFSqueue = new LinkedList<>();
    Map<Position, Position> myPath = new HashMap<>();
    List<Position> visited = new ArrayList<>();

    BFSqueue.add(currentPos);

    while (BFSqueue.size() != 0) {
      Position current = BFSqueue.poll();
      visited.add(current);
      List<Position> availablePositions = state.getPotentialMoveTargets(current);

      for (Position next : availablePositions) {
        if (!visited.contains(next)) {
          BFSqueue.add(next);
          myPath.put(next, current);
        }
      }

      BFSqueue.remove(current);
      if (current.equals(targetPosition)) {
        BFSqueue.clear();
        break;
      }
    }
    return myPath;
  }
}
