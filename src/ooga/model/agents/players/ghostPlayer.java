package ooga.model.agents.players;

import java.util.function.Consumer;
import ooga.model.Agent;
import ooga.model.util.AgentInfo;

public class ghostPlayer implements Agent {

  private AgentInfo myData;

  @Override
  public void setData(AgentInfo data) {
    myData = data;
  }

  @Override
  public AgentInfo getData() {

  }

  @Override
  public void addConsumer(Consumer<AgentInfo> consumer) {

  }

  @Override
  public void updateConsumer() {

  }

  @Override
  public void step() {

  }

}
