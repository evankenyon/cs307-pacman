package ooga.view.center.agents;

import static ooga.controller.Controller.cols;
import static ooga.controller.Controller.rows;
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
    setX(myAgent.getPosition()[0]);
    setY(myAgent.getPosition()[1]);
    ghostImage.setX(GRID_WIDTH*myAgent.getPosition()[0]);
    ghostImage.setY(GRID_HEIGHT*myAgent.getPosition()[1]);
    myAgent.addConsumer(updateGhost);
  }

  private ImageView makeGhostImage(int ghostNum) {
    String path = String.format("%s%s_ghost.png", IMAGE_PATH, GHOST_COLORS[ghostNum]);
    return new ImageView(path);
  }

  @Override
  protected void moveX(int x) {
    setX(x);
    ghostImage.setX(BOARD_WIDTH/cols*x);
  }

  @Override
  protected void moveY(int y) {
    setY(y);
    ghostImage.setY(BOARD_HEIGHT/rows*y);
  }

  @Override
  protected void updateState(int state) {
    ghostImage.setVisible(state == CONSUMABLE_STATE);
  }
}
