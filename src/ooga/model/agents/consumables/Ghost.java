package ooga.model.agents.consumables;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.Controllable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;


public class Ghost extends AbstractAgent implements Consumable {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int AFRAID_STATE = 2;

  private final static int GHOST_POINTS = 20;

  private int myState;
  private MovementStrategyContext myMover;


  public Ghost(int x, int y) {
    super(x, y);
    myState = ALIVE_STATE;
    myMover = new MovementStrategyContext(new Static());
  }

  public int getState() {
    return myState;
  }

  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  public Position step() {
    return myMover.move(getPosition());
  }

  public int consume(Consumable agent) {
    if (agent != null) {
      agent.agentReact();
      agent.applyEffects(this);
      return agent.applyPoints();
    }
    return 0;
  }

  @Override
  public void setState(int i) {
    myState = i;
    updateConsumer();
  }

  @Override
  public void agentReact() {
    if (myState == AFRAID_STATE){
      System.out.println("a ghost has been eaten");
    }
  }

  @Override
  public void applyEffects(Agent agent) {
    //decrease lives or something?
  }

  @Override
  public int applyPoints() {
    if (myState == AFRAID_STATE){
      return GHOST_POINTS;
    }
    else return 0;
  }
}
