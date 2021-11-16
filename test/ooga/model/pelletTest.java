package ooga.model;

import ooga.model.agents.consumables.pellet;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class pelletTest {

  private pellet myPellet;

  @BeforeEach
  void setUp() {
    myPellet = new pellet(1, 2);
  }

  @Test
  void stepTestNothing() {
    Position myPosition = myPellet.step();
    int currentX = myPosition.getCoords()[0];
    int currentY = myPosition.getCoords()[1];

    Assertions.assertEquals(1, currentX);
    Assertions.assertEquals(2, currentY);
  }

}
