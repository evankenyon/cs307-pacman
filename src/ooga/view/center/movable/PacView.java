package ooga.view.center.movable;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.VanillaGame;

public class PacView extends PlayerView {

  public static final String PAC_IMAGE = "pacman.png";

  private ImageView pacImage;
  private VanillaGame myGame;
  private Consumer<Integer> updateX = x -> moveX(x);
  private Consumer<Integer> updateY = y -> moveY(y);
  private Consumer<Integer> updateState = state -> updateState(state);

  public PacView(VanillaGame game) {
    myGame = game;
    pacImage = new ImageView(String.format("%s%s", IMAGE_PATH, PAC_IMAGE));
    // add the Consumers to the List<Consumer<Integer>> in the model
//    game.addConsumers(updateX, updateY, updateState);
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
