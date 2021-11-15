package ooga.model;


import java.util.function.Consumer;
import ooga.model.util.AgentInfo;

/**
 * Interface to set/retrieve agents coords.
 **/
public interface Agent {

  /**
   * set the coordinates of a given agent
   **/
  void setData(AgentInfo data);

  /**
   * get the coordinates of a given agent
   *
   * @return*/
  AgentInfo getData();

  /**
   * add consumer to link to view
   *
   * @param consumer
   */
  void addConsumer(Consumer<AgentInfo> consumer);

  /**
   * update view consumers
   */
  void updateConsumer();

  /**
   * Moves agent.
   */
  void step();
}
