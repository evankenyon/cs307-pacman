package ooga.view.center.agents.interfaces;

import ooga.model.interfaces.Agent;

/**
 * Interface for the AgentView API, which is the super class of StationaryView and MovableView and
 * is the API for all agents in the view
 */
public interface AgentViewInterface {

  /**
   * Method that is called when the consumer for each agent is called with .accept(). This is
   * overridden in MovableView and StationaryView depending on the action each agent should take
   * with the consumer is called.
   *
   * @param agent is the Agent interface of the agent to be updated
   */
  void updateAgent(Agent agent);

}
