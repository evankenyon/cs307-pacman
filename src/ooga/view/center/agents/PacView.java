package ooga.view.center.agents;

import static ooga.controller.Controller.COLS;
import static ooga.controller.Controller.ROWS;
import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

public class PacView extends MovableView {

  public static final String PAC_IMAGE = "pacman.png";

  private ImageView pacImage;
  private Agent myAgent; //TODO: change to subclass of Agent
  private Consumer<Agent> updatePacMan = newInfo -> updateAgent(newInfo);

  public PacView(Agent pac) {
    myAgent = pac;
    pacImage = new ImageView(String.format("%s%s", IMAGE_PATH, PAC_IMAGE));
    pacImage.setFitWidth(IMAGE_BUFFER);
    pacImage.setFitHeight(IMAGE_BUFFER);
    setImage(pacImage);
    pacImage.setX(GRID_WIDTH*myAgent.getPosition().getCoords()[0] + HORIZONTAL_IMAGE_BUFFER);
    pacImage.setY(GRID_HEIGHT*myAgent.getPosition().getCoords()[1] + VERTICAL_IMAGE_BUFFER);
// add the Consumers to the List<Consumer<Integer>> in the model
    myAgent.addConsumer(updatePacMan);
  }

  @Override
  protected void moveX(int x) {
    pacImage.setX(BOARD_WIDTH/COLS * x + HORIZONTAL_IMAGE_BUFFER);
  }

  @Override
  protected void moveY(int y) {
//    setY(y);
    pacImage.setY(BOARD_HEIGHT/ROWS * y + VERTICAL_IMAGE_BUFFER);
  }

  @Override
  protected void updateState(int state) {
    pacImage.setVisible(state == ALIVE_STATE);
  }

  @Override
  protected void updateOrientation(String orientation) {
    pacImage.setRotate(ORIENTATION_MAP.get(orientation));
  }
}
