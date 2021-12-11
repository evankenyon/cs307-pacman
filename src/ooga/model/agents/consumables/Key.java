package ooga.model.agents.consumables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class Key extends AbstractAgent implements Consumable {

  private final static int PELLET_POINT = 5;

  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;

  private int myState;
  protected List<Consumer<Agent>> stateConsumers;

  public Key(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
    stateConsumers = new ArrayList<>();
    setStrategy(new Static());
  }
  @Override
  public int getState() {
    return myState;
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
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
  public int getConsumed() {
    if (myState != EATEN_STATE) {
      myState = EATEN_STATE;
      getRunnable().run();
      updateConsumer();
      return PELLET_POINT;
    } else {
      return 0;
    }
  }
}
