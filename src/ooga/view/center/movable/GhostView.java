package ooga.view.center.movable;

import javafx.scene.image.ImageView;

public class GhostView extends PlayerView {

  public static final String GHOST_COLORS[] = {"blue","light_blue","pink","red","yellow"};

  private ImageView ghostImage;

  public GhostView (int ghostNum) {
    ghostImage = makeGhostImage(ghostNum);
  }

  private ImageView makeGhostImage(int ghostNum) {
    String path = String.format("%s%s_ghost.png", IMAGE_PATH, GHOST_COLORS[ghostNum]);
    return new ImageView(path);
  }

  @Override
  protected void move(int x, int y) {

  }

  @Override
  protected void consume(PlayerView prey) {

  }
}
