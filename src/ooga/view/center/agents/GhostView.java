package ooga.view.center.agents;

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
    myAgent.addConsumer(updateGhost);
  }

  private ImageView makeGhostImage(int ghostNum) {
    String path = String.format("%s%s_ghost.png", IMAGE_PATH, GHOST_COLORS[ghostNum]);
    return new ImageView(path);
  }

  @Override
  protected void moveX(int x) {
    setX(x);
  }

  @Override
  protected void moveY(int y) {
    setY(y);
  }

  @Override
  protected void updateState(int state) {
    ghostImage.setVisible(state == CONSUMABLE_STATE);
  }
}
