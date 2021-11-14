package ooga.view.center.movable;

import javafx.scene.image.ImageView;

public class PacView extends PlayerView {

  public static final String PAC_IMAGE = "pacman.png";

  private ImageView pacImage;

  public PacView() {
    pacImage = new ImageView(String.format("%s%s", IMAGE_PATH, PAC_IMAGE));
  }

  @Override
  protected void move(int x, int y) {

  }

  @Override
  protected void consume(PlayerView prey) {

  }
}
