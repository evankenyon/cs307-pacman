package ooga.model.agents.players;

import ooga.model.Controllable;
import ooga.model.util.AgentInfo;

public class ghostPlayer implements Controllable {

  private AgentInfo myData;

  @Override
  public void setCoordinates(AgentInfo data) {
    myData = data;
  }
  @Override
  public void getCoordinates() {

  }

  @Override
  public void setDirection() {

  }
}
