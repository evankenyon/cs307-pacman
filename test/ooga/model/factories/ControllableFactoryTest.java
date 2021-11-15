package ooga.model.factories;

import java.lang.reflect.InvocationTargetException;
import ooga.model.agents.players.ghostPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllableFactoryTest {

  private ControllableFactory controllableFactory;

  @BeforeEach
  void setUp() {
    controllableFactory = new ControllableFactory();
  }

  @Test
  void createControllableCorrect()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Assertions.assertTrue(
        controllableFactory.createControllable("ghostPlayer") instanceof ghostPlayer);
  }

  @Test
  void createControllableBad() {
    Assertions.assertThrows(ClassNotFoundException.class,
        () -> controllableFactory.createControllable("bad"));
  }
}