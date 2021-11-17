package ooga.view.center.agents;

import ooga.model.interfaces.Agent;
import ooga.view.center.agents.AgentView;

public abstract class StationaryView extends AgentView {

  protected abstract void updateState(int newState);

  @Override
  public void updateAgent(Agent newAgent) {
    updateState(newAgent.getState());
  }
}

