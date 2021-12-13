package ooga.model.agents;


import ooga.model.movement.Static;
import ooga.model.util.Position;

/**
 * Wall class for Pacman.
 */
public class Wall extends AbstractAgent {

  public static final int UNPASSABLE = 0;
  public static final int PASSABLE = 1;

  private int myState;

  /**
   * Wall object constructor
   *
   * @param x position
   * @param y position
   */
  public Wall(int x, int y) {
    super(x, y);
    myState = UNPASSABLE;
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
