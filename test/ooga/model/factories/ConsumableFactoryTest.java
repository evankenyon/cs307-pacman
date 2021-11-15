package ooga.model.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import ooga.model.agents.consumables.pellet;
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
    Assertions.assertTrue(consumableFactory.createConsumable("pellet") instanceof pellet);
  }

  @Test
  void createConsumableBad() {
    Assertions.assertThrows(ClassNotFoundException.class,
        () -> consumableFactory.createConsumable("bad"));
  }
}