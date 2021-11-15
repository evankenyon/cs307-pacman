package ooga.model.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.util.AgentInfo;

public abstract class AbstractAgent implements Agent{

  /*** cell list of consumers*/
  protected List<Consumer<AgentInfo>> stateConsumers;

  private AgentInfo myInfo;

  /**
   * abstract constructor for cell
   *
   * @param x int x position
   * @param y int y position
   * @param state int cell state
   */
  public AbstractAgent(int x, int y, int state) {
    myInfo = new AgentInfo(x,y,state);
    stateConsumers = new ArrayList<Consumer<AgentInfo>>();
  }

  /**
   * get the cell x position
   *
   * @return int x position
   */
  public int getX() {
    return myInfo.getX();
  }

  /**
   * get the y position
   *
   * @return int y position
   */
  public int getY() {
    return myInfo.getY();
  }

  /**
   * add consumers
   *
   * @param consumer consumer objects
   */
  public void addConsumer(Consumer<AgentInfo> consumer) {
    stateConsumers.add(consumer);
  }

}
