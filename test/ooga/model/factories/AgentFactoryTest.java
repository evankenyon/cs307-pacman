package ooga.model.factories;

import ooga.model.agents.consumables.pellet;
import ooga.model.agents.wall;
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
    Assertions.assertTrue(agentFactory.createAgent("pellet") instanceof pellet);
  }

  // TODO: uncomment when we implement ghostPlayer
//  @Test
//  void createAgentPlayer() {
//    Assertions.assertTrue(agentFactory.createAgent("ghostPlayer") instanceof ghostPlayer);
//  }

  @Test
  void createAgentWall() {
    Assertions.assertTrue(agentFactory.createAgent("wall") instanceof wall);
  }

  @Test
  void createAgentBad() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> agentFactory.createAgent("bad"));
  }
}