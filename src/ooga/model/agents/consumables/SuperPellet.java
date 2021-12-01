package ooga.model.agents.consumables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.agents.AbstractAgent;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class SuperPellet extends AbstractAgent implements Consumable {

  private final static int PELLET_POINT = 10;

  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;

  private int myState;
  private MovementStrategyContext myMover;
  protected List<Consumer<Agent>> stateConsumers;

  /**
   * abstract constructor for cell
   *
   * @param x int x position
   * @param y int y position
   */
  public SuperPellet(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
    myMover = new MovementStrategyContext(new Static());
    stateConsumers = new ArrayList<>();
  }

  @Override
  public int getState() {
    return myState;
  }

  @Override
  public Position step() {
    return myMover.move(new Position(getPosition().getCoords()[0], getPosition().getCoords()[1]));
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  @Override
  public int consume(Consumable agent) {
    return 0;
  }

  @Override
  public void setState(int i) {
    myState = i;
    updateConsumer();
  }

  public void addConsumer(Consumer<Agent> consumer) {
    stateConsumers.add(consumer);
  }

  public void updateConsumer() {
    for (Consumer<Agent> consumer : stateConsumers) {
      consumer.accept(this);
    }
  }

  @Override
  public void getConsumed() {
    myState = EATEN_STATE;
    //TODO
    // 1) update score
    // 2) update pacman state
    // 3) update ghost states
    updateConsumer();
  }

  @Override
  public void applyEffects(Pacman pacman) {
    pacman.setState(2);
  }

  @Override
  public int applyPoints() {
    return PELLET_POINT;
  }
}
