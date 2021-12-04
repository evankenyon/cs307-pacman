package ooga.model;

import ooga.model.agents.consumables.pellet;
import ooga.model.agents.players.Pacman;
import org.junit.jupiter.api.BeforeEach;

public class pelletTest {

  private pellet myPellet;
  private Pacman myPacman;

  @BeforeEach
  void setUp() {
    myPellet = new pellet(1, 2);
    myPacman = new Pacman(1,1);
  }

//
//  @Test
//  void stepTestNothing() {
//    Position myPosition = myPellet.getNextMove();
//    int currentX = myPosition.getCoords()[0];
//    int currentY = myPosition.getCoords()[1];
//
//    Assertions.assertEquals(1, currentX);
//    Assertions.assertEquals(2, currentY);
//  }
//
//  @Test
//  void testConsumed(){
//    int initState = myPellet.getState();
//    Assertions.assertEquals(1,initState);
//    myPacman.consume(myPellet);
//    Assertions.assertEquals(0,myPellet.getState());
//
//  }

}
