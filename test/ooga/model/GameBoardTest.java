package ooga.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.agents.Wall;
import ooga.model.agents.consumables.Pellet;
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
    initialStates.put("pellet", new ArrayList<>());
    initialStates.get("Pacman").add(new Position(0, 1));
    Map<String, Boolean> pelletInfo = new HashMap<>();
    pelletInfo.put("pellet", Boolean.TRUE);

    GameData vanillaGame = new GameData(initialStates, "Pacman", 0, 3, pelletInfo, 1, 1);

    gameBoard = new GameBoard(vanillaGame);
    Assertions.assertTrue(gameBoard.getGameState().findAgent(new Position(0, 0)) instanceof Pacman);
  }

  @Test
  void correctConsumableInstantiated()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> initialStates = new HashMap<>();
    initialStates.put("Pacman", new ArrayList<>());
    initialStates.get("Pacman").add(new Position(0, 0));

    initialStates.put("pellet", new ArrayList<>());
    initialStates.get("pellet").add(new Position(0, 1));

    Map<String, Boolean> pelletInfo = new HashMap<>();
    pelletInfo.put("pellet", Boolean.TRUE);

    GameData vanillaGame = new GameData(initialStates, "Pacman", 0, 3, pelletInfo, 1, 1);

    gameBoard = new GameBoard(vanillaGame);
    Assertions.assertTrue(gameBoard.getGameState().findAgent(new Position(0, 1)) instanceof Pellet);
  }

  @Test
  void correctWallInstantiated()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> initialStates = new HashMap<>();
    initialStates.put("Pacman", new ArrayList<>());
    initialStates.get("Pacman").add(new Position(0, 0));

    initialStates.put("Wall", new ArrayList<>());
    initialStates.get("Wall").add(new Position(0, 1));

    initialStates.put("pellet", new ArrayList<>());
    initialStates.get("pellet").add(new Position(0, 2));

    Map<String, Boolean> pelletInfo = new HashMap<>();
    pelletInfo.put("pellet", Boolean.TRUE);

    GameData vanillaGame = new GameData(initialStates, "Pacman", 0, 3, pelletInfo, 1, 1);

    gameBoard = new GameBoard(vanillaGame);
    Assertions.assertTrue(gameBoard.getGameState().findAgent(new Position(0, 1)) instanceof Wall);
  }

  @Test
  void mappingWorks()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Map<String, List<Position>> initialStates = new HashMap<>();
    initialStates.put("Pacman", new ArrayList<>());
    initialStates.get("Pacman").add(new Position(0, 0));

    initialStates.put("Wall", new ArrayList<>());
    initialStates.get("Wall").add(new Position(0, 1));
    initialStates.put("Dot", new ArrayList<>());
    initialStates.get("Dot").add(new Position(0, 2));

    Map<String, Boolean> pelletInfo = new HashMap<>();
    pelletInfo.put("Dot", Boolean.TRUE);

    GameData vanillaGame = new GameData(initialStates, "Pacman", 0, 3, pelletInfo, 1, 1);

    gameBoard = new GameBoard(vanillaGame);
    Assertions.assertTrue(gameBoard.getGameState().findAgent(new Position(0, 1)) instanceof Wall);
    Assertions.assertTrue(gameBoard.getGameState().findAgent(new Position(0, 2)) instanceof Pellet);
  }

  @Test
  void superPacmanDyingGhost() throws IOException {
    JsonParser jsonParser = new JsonParser();
    jsonParser.addVanillaGameDataConsumer(vanillaGameData -> {
      try {
        gameBoard = new GameBoard(vanillaGameData);
      } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
        e.printStackTrace();
      }
    });
    jsonParser.parseJSON(JSONObjectParser.parseJSONObject(
        new File("./data/tests/super_pacman_bug2.json")));
    gameBoard.addScoreConsumer(stuff -> {});
    gameBoard.addLivesConsumer(stuff -> {});
    gameBoard.addGameStatusConsumer(stuff -> {});
    gameBoard.setPlayerDirection("right");
    gameBoard.movePawns();
    gameBoard.checkCollisions();
    Assertions.assertEquals(4, gameBoard.getGameState().getLives());
    gameBoard.movePawns();
    gameBoard.checkCollisions();
    Assertions.assertEquals(4, gameBoard.getGameState().getLives());
    gameBoard.movePawns();
    gameBoard.checkCollisions();
    Assertions.assertEquals(4, gameBoard.getGameState().getLives());
    gameBoard.movePawns();
    gameBoard.checkCollisions();
    Assertions.assertEquals(4, gameBoard.getGameState().getLives());
  }
}