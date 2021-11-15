package ooga.model.agents;

import ooga.model.Agent;
import ooga.model.util.AgentInfo;

public class wall implements Agent {

  private AgentInfo myData;

  @Override
  public void setCoordinates(AgentInfo data) {
    myData = data;
  }

  @Override
  public void getCoordinates() {

  }
}
