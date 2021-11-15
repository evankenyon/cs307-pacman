package ooga.factories;

import java.lang.reflect.InvocationTargetException;
import ooga.model.agents.consumables.ghost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsumableFactoryTest {

  private ConsumableFactory consumableFactory;

  @BeforeEach
  void setUp() {
    consumableFactory = new ConsumableFactory();
  }

  @Test
  void createConsumableWorking()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Assertions.assertTrue(consumableFactory.createConsumable("ghost") instanceof ghost);
  }

  @Test
  void createConsumableBad() {
    Assertions.assertThrows(ClassNotFoundException.class,
        () -> consumableFactory.createConsumable("bad"));
  }
}