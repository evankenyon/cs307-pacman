package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.agents.consumables.Ghost;
import ooga.model.movement.BFS;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BFSTest {

  private BFS bfs;

  @BeforeEach
  void setUp() {

  }

  @Test
  void moveBFSTest()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(0, 0)), "Dot",
        List.of(new Position(1, 0), new Position(2, 0), new Position(0, 1), new Position(0, 2),
            new Position(2, 1), new Position(1, 1), new Position(2, 2)),
        "Ghost", List.of(new Position(0, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    Data vanillaGameData = new Data(wallMap, "Pacman", 3, pelletInfo, 3, 3);

    Ghost myGhost = new Ghost(0, 2);
    GameState state = new GameState(vanillaGameData);
    int[] expected = {0, 1};
    myGhost.setCoords(myGhost.getNextMove(state));
    Assertions.assertArrayEquals(expected,
        myGhost.getPosition().getCoords());
  }

  @Test
  void containsTest() {
    List<Position> list = new ArrayList<>(List.of(new Position(0, 2), new Position(1, 0)));
    Position toTest = new Position(0, 2);
    Assertions.assertTrue(list.contains(toTest));
  }
}
