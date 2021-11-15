package ooga.model.agents.players;

import java.util.function.Consumer;
import ooga.model.Agent;
import ooga.model.movement.Controllable;
import ooga.model.util.AgentInfo;
import ooga.model.movement.MovementStrategyContext;

public class Pacman implements Agent {

  private AgentInfo myData;
  private MovementStrategyContext myMover;

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

  }

  @Override
  public void updateConsumer() {

  }

  public void step() {
    AgentInfo oldData = myData;
    myData = myMover.move(oldData);
  }
}
