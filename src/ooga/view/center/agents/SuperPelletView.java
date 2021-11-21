package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import ooga.model.interfaces.Agent;

public class SuperPelletView extends StationaryView {

  public static final Paint PELLET_COLOR = Color.WHITE;
  public static final double LARGE_PELLET_SIZE = 0.25; // large pellets radii are 50% of grid square
  public static final double PELLET_BUFFER_Y = GRID_HEIGHT/2;
  public static final double PELLET_BUFFER_X = GRID_WIDTH/2;

//  private VanillaGame myGame;
//  private Position myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private Circle myCircle;
  private Consumer<Agent> updatePellet = newInfo -> updateAgent(newInfo);


  public SuperPelletView(Agent pelletAgent) {
    myAgent = pelletAgent;
//    myInfo = agentInfo;
    myCircle = makeCircle(PELLET_COLOR);
    superPelletViewSetup();
  }

  public SuperPelletView(Agent pelletAgent, List<Double> rgb) {
    myAgent = pelletAgent;
//    myInfo = agentInfo;
    // TODO: move these values to a props file
    Color pelletColor = new Color(rgb.get(0), rgb.get(1), rgb.get(2), 1);
    myCircle = makeCircle(pelletColor);
    superPelletViewSetup();
  }

  private void superPelletViewSetup() {
    setImage(myCircle);
    myCircle.setCenterX(GRID_WIDTH*myAgent.getPosition().getCoords()[0] + PELLET_BUFFER_X);
    myCircle.setCenterY(GRID_HEIGHT*myAgent.getPosition().getCoords()[1] + PELLET_BUFFER_Y);
    myAgent.addConsumer(updatePellet);
  }

  private Circle makeCircle(Paint color) {
    int x = myAgent.getPosition().getCoords()[0];
    int y = myAgent.getPosition().getCoords()[1];
    return new Circle(x, y, GRID_MIN*LARGE_PELLET_SIZE, color);
  }

  @Override
  protected void updateState(int newState) {
    myCircle.setVisible(newState == ALIVE_STATE);
  }
}
