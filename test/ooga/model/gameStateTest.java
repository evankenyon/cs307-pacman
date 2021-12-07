package ooga.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class gameStateTest {

  @BeforeEach
  void setUp() throws IOException {
  }

  @Test
  void portalRightTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(0, 1), new Position(2, 1)), "Wall",
        List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(0, 2),
            new Position(1, 2), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    GameState state = new GameState(vanillaGameData);
    state.getMyPlayer().setDirection("right");
    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));

    int[] expectedStepOne = {2, 1};
    Assertions.assertArrayEquals(expectedStepOne, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepOne)));

    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    int[] expectedStepTwo = {0, 1};
    Assertions.assertArrayEquals(expectedStepTwo, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepTwo)));

    Assertions.assertEquals(0, state.getRequiredPelletsLeft());
  }

  @Test
  void portalLeftTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(0, 1), new Position(2, 1)), "Wall",
        List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(0, 2),
            new Position(1, 2), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    GameState state = new GameState(vanillaGameData);
    state.getMyPlayer().setDirection("left");
    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));

    int[] expectedStepOne = {0, 1};
    Assertions.assertArrayEquals(expectedStepOne, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepOne)));

    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    int[] expectedStepTwo = {2, 1};
    Assertions.assertArrayEquals(expectedStepTwo, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepTwo)));

    Assertions.assertEquals(0, state.getRequiredPelletsLeft());
  }

  @Test
  void portalUpTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(1, 0), new Position(1, 2)), "Wall",
        List.of(new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(2, 0),
            new Position(2, 1), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    GameState state = new GameState(vanillaGameData);
    state.getMyPlayer().setDirection("up");
    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    System.out.println(Arrays.toString(state.getMyPlayer().getPosition().getCoords()));

    int[] expectedStepOne = {1, 0};
    Assertions.assertArrayEquals(expectedStepOne, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepOne)));

    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    int[] expectedStepTwo = {1, 2};
    Assertions.assertArrayEquals(expectedStepTwo, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepTwo)));

    Assertions.assertEquals(0, state.getRequiredPelletsLeft());
  }

  @Test
  void portalDownTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(1, 0), new Position(1, 2)), "Wall",
        List.of(new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(2, 0),
            new Position(2, 1), new Position(2, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    GameState state = new GameState(vanillaGameData);
    state.getMyPlayer().setDirection("down");
    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    System.out.println(Arrays.toString(state.getMyPlayer().getPosition().getCoords()));

    int[] expectedStepOne = {1, 2};
    Assertions.assertArrayEquals(expectedStepOne, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepOne)));

    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    int[] expectedStepTwo = {1, 0};
    Assertions.assertArrayEquals(expectedStepTwo, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepTwo)));

    Assertions.assertEquals(0, state.getRequiredPelletsLeft());
  }

  @Test
  void portalOneEndWallTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(1, 1)), "Dot",
        List.of(new Position(1, 0)), "Wall",
        List.of(new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(2, 0),
            new Position(2, 1), new Position(2, 2), new Position(1, 2)));
    Map<String, Boolean> pelletInfo = Map.of("Dot", true);
    GameData vanillaGameData = new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 1, 1);

    GameState state = new GameState(vanillaGameData);
    state.getMyPlayer().setDirection("up");
    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));

    //pacman goes up
    int[] expectedStepOne = {1, 0};
    Assertions.assertArrayEquals(expectedStepOne, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepOne)));

    //pacman doesn't portal because the other end is a Wall (it will go to -1 because it's not in charge of that, board takes care of it)
    state.getMyPlayer().setCoords(state.portal(state.getPacman().getNextMove(state)));
    int[] expectedStepTwo = {1, -1};
    Assertions.assertArrayEquals(expectedStepTwo, state.getPacman().getPosition().getCoords());
    state.deleteFoods(List.of(new Position(expectedStepTwo)));

    Assertions.assertEquals(0, state.getRequiredPelletsLeft());
  }


}
