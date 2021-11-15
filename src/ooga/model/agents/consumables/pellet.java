package ooga.model.agents.consumables;

import java.util.function.Consumer;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.AgentInfo;

public class pellet implements Consumable {

  private AgentInfo myData;
  private MovementStrategyContext myMover;
  private Consumer myConsumer;

  @Override
  public void setData(AgentInfo data) {
    myData = data;
    myMover = new MovementStrategyContext(new Static());
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
