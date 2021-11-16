package ooga.view.center.agents.stationary;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.agents.movable.PlayerView.IMAGE_PATH;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.VanillaGame;
import ooga.model.interfaces.Agent;

public class FruitView extends FoodView {

  public static final String CHERRIES_IMAGE = "cherries.png";

//  private VanillaGame myGame;
//  private AgentInfo myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private ImageView myImage;
  private Consumer<Agent> updatePellet = newInfo -> updateFood(newInfo);


  public FruitView (Agent fruit) {
    myAgent = fruit;
//    myInfo = agentInfo;
    myAgent.addConsumer(updatePellet);
    myImage = new ImageView(String.format("%s%s", IMAGE_PATH, CHERRIES_IMAGE));
  }


  @Override
  protected void updateState(int newState) {
    myImage.setVisible(newState == ALIVE_STATE);
  }
}
