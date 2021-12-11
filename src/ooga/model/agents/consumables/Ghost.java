package ooga.model.agents.consumables;

import java.util.Timer;
import java.util.TimerTask;
import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.BFS;
import ooga.model.movement.Scatter;
import ooga.model.movement.Static;
import ooga.model.util.Position;

/**
 * Ghost class for Pacman.
 */
public class Ghost extends AbstractAgent implements Consumable {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int AFRAID_STATE = 2;
  private final static int GHOST_POINTS = 20;
  private int myState;


  /**
   * Ghost constructor.
   *
   * @param x initial coordinate
   * @param y initial coordinate
   */
  public Ghost(int x, int y) {
    super(x, y);
    getPosition().setDirection("right");
    myState = ALIVE_STATE;
  }

  /**
   * @return ghost state.
   */
  public int getState() {
    return myState;
  }


  /**
   * Set new ghost position
   *
   * @param newPosition Position object with x, y and bearing.
   */
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
    updateConsumer();
  }

  @Override
  public void setState(int i) {
    myState = i;
    updateConsumer();
    if (i == AFRAID_STATE) {
      setStrategy(new Scatter());
    } else if (i == DEAD_STATE) {
      setStrategy(new Static());
    } else {
      setStrategy(new BFS());
    }
    attachStateTimer();
  }

  //makes ghost go back to normal after 4 seconds
  private void attachStateTimer() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        myState = ALIVE_STATE;
        setStrategy(new BFS());
      }
    }, 5000);
  }

  @Override
  public int getConsumed() {
    if (myState == AFRAID_STATE) {
      setStrategy(new BFS());
      return GHOST_POINTS;
    }
    if (myState == ALIVE_STATE) {
      updateConsumer();
    }
    return 0;
  }
}
