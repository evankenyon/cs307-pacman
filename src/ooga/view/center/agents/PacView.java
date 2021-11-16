package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Agent;

public class PacView extends MovableView {

  public static final String PAC_IMAGE = "pacman.png";

  private ImageView pacImage;
  private Pacman myAgent; //TODO: change to subclass of Agent
  private Consumer<Agent> updatePacMan = newInfo -> updateAgent(newInfo);

  public PacView(Pacman pac) {
    myAgent = pac;
    pacImage = new ImageView(String.format("%s%s", IMAGE_PATH, PAC_IMAGE));
    setImage(pacImage);
    setX(myAgent.getPosition()[0]);
    setY(myAgent.getPosition()[1]);
// add the Consumers to the List<Consumer<Integer>> in the model
    myAgent.addConsumer(updatePacMan);
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
    pacImage.setVisible(state == ALIVE_STATE);
  }
}
