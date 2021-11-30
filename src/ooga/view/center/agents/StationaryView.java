package ooga.view.center.agents;

import ooga.model.interfaces.Agent;
import ooga.view.center.agents.AgentView;

/**
 * Subclass of AgentView and is the super class for the view agents that do not move on the screen
 *
 * @author Dane Erickson
 */
public abstract class StationaryView extends AgentView {

  protected abstract void updateState(int newState);

  /**
   * Overridden method from AgentView super class that changes image or color depending on the new
   * Agent's state when the consumer for the corresponding Agent is called with accept().
   *
   * @param newAgent is the Agent interface of the agent to be updated
   */
  @Override
  public void updateAgent(Agent newAgent) {
    updateState(newAgent.getState());
  }
}

