package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.model.agents.wall.UNPASSABLE;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.model.interfaces.Agent;

public class WallView extends StationaryView {

  public static final Paint WALL_COLOR = Color.BLUE;

  private Agent myAgent;
  private Rectangle myWallShape;
  private Consumer<Agent> updateWall = newInfo -> updateAgent(newInfo);

  public WallView (Agent w) {
    myAgent = w;
    wallViewSetup(WALL_COLOR);
  }

  public WallView(Agent w, List<Double> rgb) {
    myAgent = w;
    Color wallColor = new Color(rgb.get(0), rgb.get(1), rgb.get(2), 1);
    wallViewSetup(wallColor);
  }

  private void wallViewSetup(Paint color) {
    myWallShape = new Rectangle(GRID_WIDTH, GRID_HEIGHT, color);
    setImage(myWallShape);
    myWallShape.setX(GRID_WIDTH*myAgent.getPosition().getCoords()[0]);
    myWallShape.setY(GRID_HEIGHT*myAgent.getPosition().getCoords()[1]);
    myAgent.addConsumer(updateWall);
  }

  @Override
  protected void updateState(int newState) {
    myWallShape.setVisible(newState == UNPASSABLE);
  }
}
