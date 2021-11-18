package ooga.view.center.agents;

import static ooga.controller.Controller.COLS;
import static ooga.controller.Controller.ROWS;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

public class GhostView extends MovableView {

  public static final String GHOST_COLORS[] = {"blue","light_blue","pink","red","yellow"};

  public static final int CONSUMABLE_STATE = 1;

  private ImageView ghostImage;
  private Agent myAgent;
  private Consumer<Agent> updateGhost = newInfo -> updateAgent(newInfo);

  public GhostView (Agent ghost) { // make just 1 ghost (not 4) for first test?
    myAgent = ghost;
    ghostImage = makeGhostImage(0); //TODO: fix Ghost Number
    setImage(ghostImage);
//    setX(myAgent.getPosition().getCoords()[0]);
//    setY(myAgent.getPosition().getCoords()[1]);
    ghostImage.setX(GRID_WIDTH*myAgent.getPosition().getCoords()[0] + HORIZONTAL_IMAGE_BUFFER);
    ghostImage.setY(GRID_HEIGHT*myAgent.getPosition().getCoords()[1] + VERTICAL_IMAGE_BUFFER);
    myAgent.addConsumer(updateGhost);
  }

  private ImageView makeGhostImage(int ghostNum) {
    String path = String.format("%s%s_ghost.png", IMAGE_PATH, GHOST_COLORS[ghostNum]);
    ImageView ghost = new ImageView(path);
    ghost.setFitWidth(IMAGE_BUFFER);
    ghost.setFitHeight(IMAGE_BUFFER);
    return ghost;
  }

  @Override
  protected void moveX(int x) {
//    setX(x);
    ghostImage.setX(BOARD_WIDTH/COLS * x + HORIZONTAL_IMAGE_BUFFER);
  }

  @Override
  protected void moveY(int y) {
//    setY(y);
    ghostImage.setY(BOARD_HEIGHT/ROWS * y + VERTICAL_IMAGE_BUFFER);
  }

  @Override
  protected void updateState(int state) {
    ghostImage.setVisible(state == CONSUMABLE_STATE);
  }

  @Override
  protected void updateOrientation(String orientation) {
    ghostImage.setRotate(ORIENTATION_MAP.get(orientation));
  }
}
