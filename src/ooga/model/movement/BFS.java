package ooga.model.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

  @Override
  public Position move(GameState state, Position currentPos) {
    int[] pacPos = state.getPacman().getPosition().getCoords();
    Queue<Position> BFSqueue = new LinkedList<>();
    Map<int[], int[]> myPath = new HashMap<>();
    List<Position> visited = new ArrayList<>();

    BFSqueue.add(currentPos);

    while (BFSqueue.size() != 0) {
      Position current = BFSqueue.poll();
      visited.add(current);

      myPath.put(current.getCoords(), null);
      List<Position> availablePositions = state.getPotentialMoveTargets(current);

      for (Position next : availablePositions) {
        if (!visited.contains(next)) {
          BFSqueue.add(next);
          myPath.put(current.getCoords(), next.getCoords());
          System.out.println(myPath.keySet());
        }
      }

      BFSqueue.remove(current);

      if (current.equals(pacPos)) {
        System.out.println("found pacman!");
        BFSqueue.clear();
        break;
      }
    }

    List<int[]> optimalPath = new ArrayList<>();
    int[] prev = pacPos;

    System.out.println(Arrays.toString(myPath.get(prev)));
//    System.out.println(Arrays.toString(myPath.get(new int[]{0, 2})));
//    myPath.keySet().forEach(s -> System.out.println(Arrays.toString(s)));
    while (prev != null) {
      optimalPath.add(prev);
      prev = myPath.get(prev);

//      System.out.println(Arrays.toString(prev.getCoords()));
    }
    System.out.println(myPath);

//    optimalPath.forEach(s -> System.out.println(Arrays.toString(s)));

    //take second to last item which is next step from currentPos
    return new Position(optimalPath.get(optimalPath.size())[0],
        optimalPath.get(optimalPath.size())[1]);
  }

}
