package ooga.model;

import ooga.model.agents.players.Pacman;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PacmanTest {

  private Pacman pacman;

  @BeforeEach
  void setUp() {
    pacman = new Pacman(1, 2);
  }

  @Test
  void stepTestUp() {
    pacman.setDirection("up");
    Position potentialPosition = pacman.step();
    int currentX = potentialPosition.getCoords()[0];
    int currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(3, currentY);
  }

  @Test
  void stepTestDown() {
    pacman.setDirection("down");
    Position potentialPosition = pacman.step();
    int currentX = potentialPosition.getCoords()[0];
    int currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(1, currentY);
  }

  @Test
  void stepTestRight() {
    pacman.setDirection("right");
    Position potentialPosition = pacman.step();
    int currentX = potentialPosition.getCoords()[0];
    int currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(2, currentX);
    Assertions.assertEquals(2, currentY);
  }

  @Test
  void stepTestLeft() {
    pacman.setDirection("left");
    Position potentialPosition = pacman.step();
    int currentX = potentialPosition.getCoords()[0];
    int currentY = potentialPosition.getCoords()[1];

    Assertions.assertEquals(0, currentX);
    Assertions.assertEquals(2, currentY);
  }


}
