package ooga.model.agents;

import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.AgentInfo;

public class wall implements Agent {

  private AgentInfo myData;
  private MovementStrategyContext myMover;
  private Consumer<AgentInfo> myConsumer;

  public wall() {
    myMover = new MovementStrategyContext(new Static());
  }

  @Override
  public void setData(AgentInfo data) {
    myData = data;
  }

  @Override
  public AgentInfo getData() {
    return myData;
  }

  @Override
  public void addConsumer(Consumer<AgentInfo> consumer) {
    myConsumer = consumer;
  }

  @Override
  public void updateConsumer(AgentInfo info) {
    myConsumer.accept(info);
  }

  @Override
  public void step() {
    myMover.move(myData);
  }
}
