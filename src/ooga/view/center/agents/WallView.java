package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;

import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ooga.model.agents.wall;
import ooga.model.interfaces.Agent;

public class WallView extends StationaryView {

  public static final Paint WALL_COLOR = Color.BLUE;
  public static final int WALL_SIZE = 50;

  private wall myAgent;
  private Rectangle myWallShape;
  private Consumer<Agent> updateWall = newInfo -> updateStationary(newInfo);

  public WallView (wall w) {
    myAgent = w;
    myWallShape = makeWall(myAgent);
    setImage(myWallShape);
    myAgent.addConsumer(updateWall);
  }

  private Rectangle makeWall(wall myAgent) {
    int x = myAgent.getPosition()[0];
    int y = myAgent.getPosition()[1];
    return new Rectangle(WALL_SIZE, WALL_SIZE, WALL_COLOR);
  }

  @Override
  protected void updateState(int newState) {
    myWallShape.setVisible(newState == ALIVE_STATE);
  }
}
