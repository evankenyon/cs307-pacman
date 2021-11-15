package ooga.model.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.util.Position;

public abstract class AbstractAgent implements Agent {

  /*** cell list of consumers*/
  protected List<Consumer<Agent>> stateConsumers;

  private String TYPE;
  private Position myPosition;

  /**
   * abstract constructor for cell
   *
   * @param x int x position
   * @param y int y position
   */
  public AbstractAgent(int x, int y, String type) {
    myPosition = new Position(x, y);
    stateConsumers = new ArrayList<Consumer<Agent>>();
    TYPE = type;
  }


  /**
   * add consumers
   *
   * @param consumer consumer objects
   */
  public void addConsumer(Consumer<Agent> consumer) {
    stateConsumers.add(consumer);
  }

  public void updateConsumer() {
    for (Consumer<Agent> consumer : stateConsumers) {
      consumer.accept(this);
    }
  }

  public String getType() {
    return TYPE;
  }

}
