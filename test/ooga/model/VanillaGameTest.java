package ooga.model;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VanillaGameTest {


  private VanillaGame myGame;

  @Test
  void moveAllPacmanTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(0, 0)), "Dot",
        List.of(new Position(1, 0)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
//    Data vanillaGameData = new Data(wallMap, "Pacman", pelletInfo, mapCols, mapRows);
//    myGame = new VanillaGame(vanillaGameData);
    myGame.getBoard().setPlayerDirection("right");
    myGame.step();
    //expect that pacman moves one place to the right
    int[] expected = {1, 0};
    Assertions.assertArrayEquals(expected,
        myGame.getBoard().getGameState().getMyPlayer().getPosition().getCoords());
  }
}
