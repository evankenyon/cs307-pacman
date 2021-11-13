package ooga.view.center.movable;

import javafx.scene.image.ImageView;
import ooga.model.VanillaGame;

public class GhostView extends PlayerView {

  public static final String GHOST_COLORS[] = {"blue","light_blue","pink","red","yellow"};

  private ImageView ghostImage;
  private VanillaGame myGame;

  public GhostView (VanillaGame game, int ghostNum) {
    myGame = game;
    ghostImage = makeGhostImage(ghostNum);
  }

  private ImageView makeGhostImage(int ghostNum) {
    String path = String.format("%s%s_ghost.png", IMAGE_PATH, GHOST_COLORS[ghostNum]);
    return new ImageView(path);
  }

  @Override
  protected void moveX(int x) {

  }

  @Override
  protected void moveY(int y) {

  }

  @Override
  protected void updateState(int state) {

  }

  @Override
  protected void consume(PlayerView prey) {

  }
}
