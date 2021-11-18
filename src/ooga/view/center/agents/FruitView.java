package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.agents.MovableView.IMAGE_PATH;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

public class FruitView extends StationaryView {

  public static final String FRUIT_IMAGE = "fruit.png";

//  private VanillaGame myGame;
//  private AgentInfo myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private ImageView myImage;
  private Consumer<Agent> updatePellet = newInfo -> updateAgent(newInfo);


  public FruitView (Agent fruit) {
    myAgent = fruit;
//    myInfo = agentInfo;
    myAgent.addConsumer(updatePellet);
    myImage = new ImageView(String.format("%s%s", IMAGE_PATH, FRUIT_IMAGE));
    myImage.setFitWidth(IMAGE_BUFFER);
    myImage.setFitHeight(IMAGE_BUFFER);
//    setX(myAgent.getPosition().getCoords()[0]);
//    setY(myAgent.getPosition().getCoords()[1]);
    myImage.setX(GRID_WIDTH*myAgent.getPosition().getCoords()[0] + HORIZONTAL_IMAGE_BUFFER);
    myImage.setY(GRID_HEIGHT*myAgent.getPosition().getCoords()[1] + VERTICAL_IMAGE_BUFFER);
    setImage(myImage);
  }


  @Override
  protected void updateState(int newState) {
    myImage.setVisible(newState == ALIVE_STATE);
  }
}
