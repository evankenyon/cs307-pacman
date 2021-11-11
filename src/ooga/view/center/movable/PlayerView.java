package ooga.view.center.movable;

public abstract class PlayerView {

  public static final String IMAGE_PATH = "ooga.view.center.images.";

  protected abstract void move(int x, int y);

  protected abstract void consume(PlayerView prey);

}
