package ooga.model.agents.consumables;

import ooga.model.Consumable;
import ooga.model.util.AgentInfo;


public class ghost implements Consumable {

  private AgentInfo myData;

  @Override
  public void setCoordinates(AgentInfo data) {
    myData = data;
  }
  @Override
  public void getCoordinates() {

  }

  @Override
  public void consume() {

  }

  @Override
  public void agentReact() {

  }

  @Override
  public void applyEffects() {

  }
}
