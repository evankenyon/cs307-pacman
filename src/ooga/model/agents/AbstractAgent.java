package ooga.model.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

public abstract class AbstractAgent implements Agent {

  /*** cell list of consumers*/
  protected List<Consumer<Agent>> stateConsumers;

  private Position myPosition;

  /**
   * abstract constructor for cell
   *
   * @param x int x position
   * @param y int y position
   */
  public AbstractAgent(int x, int y) {
    myPosition = new Position(x, y);
    stateConsumers = new ArrayList<>();
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

  public Position getPosition() {
    return myPosition;
  }

  public void setPosition(int[] newPosition) {
    myPosition.setCoords(newPosition[0], newPosition[1]);
  }

  public abstract int getState();

  public void setDirection(String direction) {
    myPosition.setDirection(direction);
  }
}
