package ooga.model.interfaces;


import java.util.function.Consumer;
import ooga.model.util.Position;

/**
 * Interface to set/retrieve agents coords.
 **/
public interface Agent {

  /**
   * add consumer to link to view
   *
   * @param consumer
   */
  void addConsumer(Consumer<Agent> consumer);

  /**
   * updates consumer linked to view
   */
  void updateConsumer();

  /**
   * Moves agent.
   *
   * @return
   */
  Position getNextMove();

  Position getPosition();

  int getState();

  void setCoords(Position newPosition);

  void setDirection(String direction);

  //not sure if this makes sense to have
  int consume(Consumable agent);

  void setState(int i);

  void addRunnable(Runnable runnable);
}
