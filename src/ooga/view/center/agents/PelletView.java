package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.function.Consumer;
import javafx.scene.shape.Circle;
import ooga.model.interfaces.Agent;

public class PelletView extends StationaryView {

  public static final Paint PELLET_COLOR = Color.WHITE;
  public static final int SMALL_PELLET_SIZE = 20;
  public static final int LARGE_PELLET_SIZE = 100;
  public static final double PELLET_BUFFER_Y = GRID_HEIGHT/2;
  public static final double PELLET_BUFFER_X = GRID_WIDTH/2;

//  private VanillaGame myGame;
//  private Position myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private Circle myCircle;
  private Consumer<Agent> updatePellet = newInfo -> updateAgent(newInfo);


  public PelletView (Agent pelletAgent) {
    myAgent = pelletAgent;
//    myInfo = agentInfo;
    myCircle = makeCircle();
    setImage(myCircle);
//    setX(myAgent.getPosition()[0]);
//    setY(myAgent.getPosition()[1]);
    myCircle.setCenterX(GRID_WIDTH*myAgent.getPosition()[0] + PELLET_BUFFER_X);
    myCircle.setCenterY(GRID_HEIGHT*myAgent.getPosition()[1] + PELLET_BUFFER_Y);
    myAgent.addConsumer(updatePellet);
  }

  private Circle makeCircle() {
    int x = myAgent.getPosition()[0];
    int y = myAgent.getPosition()[1];
    int size = SMALL_PELLET_SIZE;
    if (myAgent.getState() == 1) size = LARGE_PELLET_SIZE;
    return new Circle(x, y, size, PELLET_COLOR);
  }

  @Override
  protected void updateState(int newState) {
    myCircle.setVisible(newState == ALIVE_STATE);
  }
}
