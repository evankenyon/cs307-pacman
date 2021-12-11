package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.model.agents.consumables.Ghost;
import org.junit.jupiter.api.Test;

public class GhostTest {

  @Test
  void testGhostOrientation() {
    Ghost newGhost = new Ghost(0,0);
    assertEquals("right", newGhost.getPosition().getDirection());
  }
}
