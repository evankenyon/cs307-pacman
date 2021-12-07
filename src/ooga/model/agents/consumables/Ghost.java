package ooga.model.agents.consumables;

import java.util.Timer;
import java.util.TimerTask;
import ooga.model.GameState;
import ooga.model.agents.AbstractAgent;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.BFS;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Scatter;
import ooga.model.movement.Static;
import ooga.model.util.Position;


public class Ghost extends AbstractAgent implements Consumable {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int AFRAID_STATE = 2;

  private final static int GHOST_POINTS = 20;

  private int myState;


  public Ghost(int x, int y) {
    super(x, y);
    getPosition().setDirection("right");
    myState = ALIVE_STATE;
  }

  public int getState() {
    return myState;
  }

  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
    updateConsumer();
  }

  @Override
  public void setState(int i) {
    myState = i;
    updateConsumer();
    if (i == AFRAID_STATE){
      setStrategy(new Scatter());
    }
    else if(i == DEAD_STATE){
      setStrategy(new Static());
    }
    else{
      setStrategy(new BFS());
    }
    attachStateTimer();
  }

  //makes it go back to normal after 4 seconds
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
      System.out.println("pacman tried to eat a ghost- it didn't work");
      updateConsumer();
    }
    return 0;
  }
}
