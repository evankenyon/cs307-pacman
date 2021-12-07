package ooga.factories;

import ooga.model.agents.Wall;
import ooga.model.agents.consumables.Pellet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgentFactoryTest {

  AgentFactory agentFactory;

  @BeforeEach
  void setUp() {
    agentFactory = new AgentFactory();
  }

  @Test
  void createAgentConsumable() {
    Assertions.assertTrue(agentFactory.createAgent("pellet", 0, 0) instanceof Pellet);
  }

  @Test
  void createAgentWall() {
    Assertions.assertTrue(agentFactory.createAgent("Wall", 0, 0) instanceof Wall);
  }

  @Test
  void createAgentBad() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> agentFactory.createAgent("bad", 0, 0));
  }
}