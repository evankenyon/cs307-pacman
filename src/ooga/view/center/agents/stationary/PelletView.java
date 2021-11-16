package ooga.view.center.agents.stationary;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.function.Consumer;
import javafx.scene.shape.Circle;
import ooga.model.VanillaGame;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

public class PelletView extends FoodView {

  public static final Paint PELLET_COLOR = Color.WHITE;
  public static final int SMALL_PELLET_SIZE = 20;
  public static final int LARGE_PELLET_SIZE = 100;

//  private VanillaGame myGame;
//  private Position myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private Circle myCircle;
  private Consumer<Agent> updatePellet = newInfo -> updateFood(newInfo);


  public PelletView (Agent pellet) {
    myAgent = pellet;
//    myInfo = agentInfo;
    myAgent.addConsumer(updatePellet);
    myCircle = makeCircle(myAgent);
  }

  private Circle makeCircle(Agent agent) {
    int x = agent.getPosition()[0];
    int y = agent.getPosition()[1];
    int size = SMALL_PELLET_SIZE;
    if (agent.getState() == 1) size = LARGE_PELLET_SIZE;
    return new Circle(x, y, size, PELLET_COLOR);
  }

  @Override
  protected void updateState(int newState) {
    myCircle.setVisible(newState == ALIVE_STATE);
  }
}
