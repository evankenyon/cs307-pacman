package ooga.view.center.movable;

public abstract class PlayerView {

  public static final String IMAGE_PATH = "ooga.view.center.images.";

  protected abstract void moveX(int x);

  protected abstract void moveY(int y);

  protected abstract void updateState(int state);

  protected abstract void consume(PlayerView prey);

  protected void updatePlayer(Integer[] newInfo) {
    moveX(newInfo[0]);
    moveY(newInfo[1]);
    updateState(newInfo[2]);
  }


 // protected void updatePlayer(AgentInfo newInfo) {
//  }

}
