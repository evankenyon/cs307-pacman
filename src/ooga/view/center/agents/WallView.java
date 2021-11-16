package ooga.view.center.agents;

import static ooga.controller.Controller.cols;
import static ooga.controller.Controller.rows;
import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ooga.model.agents.wall;
import ooga.model.interfaces.Agent;

public class WallView extends StationaryView {

  public static final Paint WALL_COLOR = Color.BLUE;
  public static final int WALL_HEIGHT = BOARD_HEIGHT/rows;
  public static final int WALL_WIDTH = BOARD_WIDTH/cols;

  private Agent myAgent;
  private Rectangle myWallShape;
  private Consumer<Agent> updateWall = newInfo -> updateAgent(newInfo);

  public WallView (Agent w) {
    myAgent = w;
    myWallShape = new Rectangle(WALL_WIDTH, WALL_HEIGHT, WALL_COLOR);
    setImage(myWallShape);
    setX(myAgent.getPosition()[0]);
    setY(myAgent.getPosition()[1]);
    myWallShape.setX(BOARD_WIDTH/cols*myAgent.getPosition()[0]);
    myWallShape.setY(BOARD_HEIGHT/rows*myAgent.getPosition()[1]);
    myAgent.addConsumer(updateWall);
  }

  @Override
  protected void updateState(int newState) {
    myWallShape.setVisible(newState == ALIVE_STATE);
  }
}