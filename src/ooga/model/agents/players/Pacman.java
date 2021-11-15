package ooga.model.agents.players;

import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.movement.Controllable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.AgentInfo;

public class Pacman implements Agent {

  private AgentInfo myData;
  private MovementStrategyContext myMover;
  private Consumer<AgentInfo> myConsumer;

  public Pacman() {
    myMover = new MovementStrategyContext(new Controllable());
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

  public void step() {
    AgentInfo oldData = myData;
    myData = myMover.move(oldData);
  }
}
