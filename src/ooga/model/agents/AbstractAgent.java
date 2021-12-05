package ooga.model.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractAgent implements Agent  {

  /*** cell list of consumers*/
  protected List<Consumer<Agent>> stateConsumers;
  private static final Logger LOG = LogManager.getLogger(AbstractAgent.class);

  private Position myPosition;
  private Runnable superPelletRun;

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

  /**
   * Adds a Runnable to the SuperPellet object that updates the Ghost and Pac states when consumed
   *
   * @param runnable is the Runnable to be assigned to local Runnable variable
   */
  public void addRunnable(Runnable runnable) { superPelletRun = runnable; }

  protected Runnable getRunnable() { return superPelletRun; }

}
