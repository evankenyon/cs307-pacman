package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ooga.controller.IO.keyTracker;
import ooga.model.agents.consumables.pellet;
import ooga.model.agents.players.Pacman;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PacmanTest {

  private Pacman pacman;
  private keyTracker tracker;
  private KeyEvent left;
  private KeyEvent right;
  private KeyEvent up;
  private KeyEvent down;
  private GameState state;

  @BeforeEach
  void setUp() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    pacman = new Pacman(1, 2);
    Map<String, List<Position>> wallMap = new HashMap<>();
    Map<String, Boolean> pelletInfo = new HashMap<>();
    state = new GameState(new GameData(wallMap, "Pacman", 0, 3, pelletInfo, 3, 3));
    tracker = new keyTracker();
    left = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "A",
        "left", KeyCode.A, false, false, false,
        false);
    right = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "D",
        "right", KeyCode.D, false, false, false,
        false);
    up = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "W",
        "up", KeyCode.W, false, false, false,
        false);
    down = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "S",
        "down", KeyCode.S, false, false, false,
        false);
  }

  @Test
  void stepTestUp() {
    pacman.getPosition().setDirection(tracker.getPressedKey(up));
    Position potentialPosition = pacman.getNextMove(state);
    double currentX = potentialPosition.getCoords()[0];
    double currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(1, currentY);
  }

  @Test
  void stepTestDown() {
    pacman.getPosition().setDirection(tracker.getPressedKey(down));
    Position potentialPosition = pacman.getNextMove(state);
    double currentX = potentialPosition.getCoords()[0];
    double currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(3, currentY);
  }

  @Test
  void stepTestRight() {
    pacman.getPosition().setDirection(tracker.getPressedKey(right));
    Position potentialPosition = pacman.getNextMove(state);
    double currentX = potentialPosition.getCoords()[0];
    double currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(2, currentX);
    Assertions.assertEquals(2, currentY);
  }

  @Test
  void stepTestLeft() {
    pacman.getPosition().setDirection(tracker.getPressedKey(left));
    Position potentialPosition = pacman.getNextMove(state);
    double currentX = potentialPosition.getCoords()[0];
    double currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(0, currentX);
    Assertions.assertEquals(2, currentY);
  }

  @Test
  void consumeTestPellet() {
    //create pellet at 2,2 and pacman at 1,2 then move pacman towards pellet
    pellet myPellet = new pellet(2, 2);
    pacman.getPosition().setDirection("right");
    int pointsGained = myPellet.getConsumed();

    Assertions.assertEquals(2, pointsGained);
  }


}
