package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.endConditions.AllRequiredPelletsConsumed;
import ooga.model.endConditions.EndConditionContext;
import ooga.model.endConditions.PacmanDies;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EndConditionsTest {

  EndConditionContext context;

  public EndConditionsTest() {
    context = new EndConditionContext();
  }

  @Test
  void allRequiredPelletsConsumedFalseTest()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(0, 1), new Position(2, 1)), "Wall",
        List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(0, 2),
            new Position(1, 2), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    context.setStrategy(new AllRequiredPelletsConsumed());
    Assertions.assertFalse(context.checkEnd(new GameState(vanillaGameData)));
  }

  @Test
  void pacmanDiesFalseTest()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(0, 1), new Position(2, 1)), "Wall",
        List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(0, 2),
            new Position(1, 2), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    context.setStrategy(new PacmanDies());
    Assertions.assertFalse(context.checkEnd(new GameState(vanillaGameData)));
  }

  @Test
  void pacmanDiesTrueTest()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(0, 1), new Position(2, 1)), "Wall",
        List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(0, 2),
            new Position(1, 2), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 0, pelletInfo, 1, 1);

    context.setStrategy(new PacmanDies());
    Assertions.assertTrue(context.checkEnd(new GameState(vanillaGameData)));
  }
}
