package ooga.model.agents.consumables;

import ooga.model.GameState;
import ooga.model.agents.AbstractAgent;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.BFS;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;


public class Ghost extends AbstractAgent implements Consumable {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int AFRAID_STATE = 2;

  private final static int GHOST_POINTS = 20;

  private int myState;
  private final MovementStrategyContext myMover;


  public Ghost(int x, int y) {
    super(x, y);
    getPosition().setDirection("right");
    myState = ALIVE_STATE;
    myMover = new MovementStrategyContext(new BFS());
  }

  public int getState() {
    return myState;
  }

  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  public Position getNextMove(GameState state) {
    return myMover.move(state, getPosition());
  }

  @Override
  public void setState(int i) {
    myState = i;
    updateConsumer();
  }

  @Override
  public int getConsumed() {
    if (myState == AFRAID_STATE) {
      myState = DEAD_STATE;
      updateConsumer();
      return GHOST_POINTS;
    }
    if (myState == ALIVE_STATE) {
      System.out.println("pacman tried to eat a ghost- it didn't work");
      updateConsumer();
    }
    return 0;
  }

  @Override
  public void applyEffects(Pacman pacman) {
//    if (pacman.getState() != 2){
//       pacman.loseLife();
//       updateConsumer();
//    }
  }

}
