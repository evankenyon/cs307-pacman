package ooga.view.center.agents.stationary;

import ooga.model.interfaces.Agent;
import ooga.view.center.agents.AgentView;

public abstract class FoodView extends AgentView {

  protected abstract void updateState(int newState);

  protected void updateFood (Agent newAgent) {
    updateState(newAgent.getState());
  }
}

