package ooga.model.interfaces;


import java.util.function.Consumer;

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
   */
  void step();

  int[] getPosition();

  int getState();
}
