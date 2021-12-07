package ooga.model;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VanillaGameTest {


  private GameEngine myGame;

  @Test
  void moveAllPacmanTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    //map of only pacman and dot to its right
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(0, 0)), "Dot",
        List.of(new Position(1, 0)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);
    myGame = new GameEngine(vanillaGameData);
    myGame.getBoard().addScoreConsumer(System.out::println);
    myGame.getBoard().addGameStatusConsumer(System.out::println);
    myGame.getBoard().setPlayerDirection("right");
    myGame.step();
    //expect that pacman moves one place to the right
    int[] expected = {1, 0};
    Assertions.assertArrayEquals(expected,
        myGame.getBoard().getGameState().getMyPlayer().getPosition().getCoords());
  }
}
