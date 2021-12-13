package ooga.model.agents;


import ooga.model.movement.Static;
import ooga.model.util.Position;

/**
 * Empty cell object.
 */
public class Empty extends AbstractAgent {

  public static final int EMPTY = 0;
  private int myState;

  /**
   * Constructor for empty cell
   *
   * @param x position
   * @param y position
   */
  public Empty(int x, int y) {
    super(x, y);
    myState = EMPTY;
    setStrategy(new Static());
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  @Override
  public void setDirection(String direction) {
  }

  @Override
  public void setState(int i) {
    myState = i;
  }

  @Override
  public int getState() {
    return myState;
  }

}
