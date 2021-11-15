package ooga.model.agents;

import java.util.function.Consumer;
import ooga.model.Agent;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.AgentInfo;

public class wall implements Agent {

  private AgentInfo myData;
  private MovementStrategyContext myMover;

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

  }

  @Override
  public void updateConsumer() {

  }

  @Override
  public void step() {
    myMover.move(myData);
  }
}
