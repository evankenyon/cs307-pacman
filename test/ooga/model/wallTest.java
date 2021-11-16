package ooga.model;

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
  void stepTestNothing() {
    Position myPosition = myWall.step();
    int currentX = myPosition.getCoords()[0];
    int currentY = myPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(2, currentY);
  }
}
