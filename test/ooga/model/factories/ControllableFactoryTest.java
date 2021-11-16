package ooga.model.factories;

import java.lang.reflect.InvocationTargetException;
import ooga.model.agents.players.Pacman;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllableFactoryTest {

  private ControllableFactory controllableFactory;

  @BeforeEach
  void setUp() {
    controllableFactory = new ControllableFactory();
  }

//  TODO: delete if controllables are no longer used at end of project, uncomment if they are

//  @Test
//  void createControllableCorrect()
//      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//    Assertions.assertTrue(
//        controllableFactory.createControllable("Pacman") instanceof Pacman);
//  }

  @Test
  void createControllableBad() {
    Assertions.assertThrows(ClassNotFoundException.class,
        () -> controllableFactory.createControllable("bad", 0, 0));
  }
}