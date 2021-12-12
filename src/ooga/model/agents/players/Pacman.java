package ooga.model.agents.players;

import java.util.Timer;
import java.util.TimerTask;
import ooga.model.agents.AbstractAgent;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for Pacman.
 */
public class Pacman extends AbstractAgent {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int SUPER_STATE = 2;
  private int myState;
  private static final Logger LOG = LogManager.getLogger(Pacman.class);

  /**
   * Pacman constructor
   *
   * @param x initial coordinate
   * @param y initial coordinate
   */
  public Pacman(int x, int y) {
    super(x, y);
    myState = ALIVE_STATE;
  }

  @Override
  public int getState() {
    return myState;
  }

  /**
   * set new coordinates
   *
   * @param newPosition position object
   */
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  @Override
  public void setState(int i) {
    myState = i;
    LOG.info("pacman state now {}", myState);
    updateConsumer();
    attachStateTimer();
  }

  private void attachStateTimer() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if (myState == SUPER_STATE) {
          myState = ALIVE_STATE;
        }
      }
    }, 4000);
  }
}
