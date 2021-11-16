package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.agents.players.Pacman;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {
  private GameBoard gameBoard;

  @BeforeEach
  void setUp() {

  }

  @Test
  void correctPlayerInstantiated()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> initialStates = new HashMap<>();
    initialStates.put("Pacman", new ArrayList<>());
    initialStates.get("Pacman").add(new Position(0, 0));

    Map<String, Boolean> pelletInfo = new HashMap<>();
    pelletInfo.put("Dot", Boolean.TRUE);

    VanillaGameDataInterface vanillaGame = new VanillaGameData(initialStates, "Pacman", pelletInfo);

    gameBoard = new GameBoard(vanillaGame);
    Assertions.assertTrue(gameBoard.findAgent(new Position(0, 0)) instanceof Pacman);
  }
}