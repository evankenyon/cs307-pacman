package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.agents.wall;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class wallTest {

  private wall myWall;

  @BeforeEach
  void setUp() {
    myWall = new wall(1, 2);
  }

  @Test
  void stepTestNothing()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Map<String, List<Position>> initialStates = new HashMap<>();
    initialStates.put("Pacman", new ArrayList<>());
    initialStates.get("Pacman").add(new Position(0, 0));

    initialStates.put("pellet", new ArrayList<>());
    initialStates.get("pellet").add(new Position(0, 1));

    Map<String, Boolean> pelletInfo = new HashMap<>();
    pelletInfo.put("pellet", Boolean.TRUE);

    GameData data = new GameData(initialStates, "Pacman", 0, 3, pelletInfo, 1, 1);
    GameState myState = new GameState(data);
    Position myPosition = myWall.getNextMove(myState);
    double currentX = myPosition.getCoords()[0];
    double currentY = myPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(2, currentY);
  }
}
