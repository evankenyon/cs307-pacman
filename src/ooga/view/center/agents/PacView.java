package ooga.view.center.agents;

import static ooga.controller.Controller.cols;
import static ooga.controller.Controller.rows;
import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.net.URL;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Agent;

public class PacView extends MovableView {

  public static final String PAC_IMAGE = "pacman.png";

  private ImageView pacImage;
  private Agent myAgent; //TODO: change to subclass of Agent
  private Consumer<Agent> updatePacMan = newInfo -> updateAgent(newInfo);

  public PacView(Agent pac) {
    myAgent = pac;
    pacImage = new ImageView(String.format("%s%s", IMAGE_PATH, PAC_IMAGE));
    pacImage.setFitWidth(GRID_MIN);
    pacImage.setFitHeight(GRID_MIN);
    setImage(pacImage);
//    setX(myAgent.getPosition()[0]);
//    setY(myAgent.getPosition()[1]);
    pacImage.setX(GRID_WIDTH*myAgent.getPosition().getCoords()[0]);
    pacImage.setY(GRID_HEIGHT*myAgent.getPosition().getCoords()[1]);
// add the Consumers to the List<Consumer<Integer>> in the model
    myAgent.addConsumer(updatePacMan);
  }

  @Override
  protected void moveX(int x) {
    setX(x);
    pacImage.setX(BOARD_WIDTH/cols*x);
  }

  @Override
  protected void moveY(int y) {
    setY(y);
    pacImage.setY(BOARD_HEIGHT/rows*y);
  }

  @Override
  protected void updateState(int state) {
    pacImage.setVisible(state == ALIVE_STATE);
  }
}
