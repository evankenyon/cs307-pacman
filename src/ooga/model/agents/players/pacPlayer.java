package ooga.model.agents.players;

import ooga.model.Controllable;
import ooga.model.util.AgentInfo;
import ooga.model.util.MoveableClass;

public class pacPlayer implements Controllable {

  private AgentInfo myData;
  private MoveableClass mover;

  public pacPlayer() {
    mover = new MoveableClass();
  }

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
