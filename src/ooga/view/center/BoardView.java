package ooga.view.center;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.controller.IO.JsonParser;
import ooga.model.VanillaGame;
import ooga.model.util.Position;

public class BoardView {

  public static final int BOARD_WIDTH = 500;
  public static final int BOARD_HEIGHT = 700;
  public static final int GRID_SIZE = 1;
  public static final Paint BOARD_COLOR = Color.BLACK;


  private VanillaGame myGame;
  private JsonParser myParser;
  private GridPane myBoardPane;

  public BoardView (VanillaGame game, JsonParser parser) {
    myGame = game;
    myParser = parser;
    myBoardPane = new GridPane();
    myBoardPane.setBackground(new Background(new BackgroundFill(BOARD_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    initiateBoard();
  }

  private void initiateBoard() {
    makeWalls(myParser.getWallMapPositions());
  }

  private void makeWalls(List<Position> positions) {
    for (Position p : positions) {
      myBoardPane.add(new Rectangle(p.getCoords()[0], p.getCoords()[1], GRID_SIZE, GRID_SIZE), p.getCoords()[0], p.getCoords()[1]);
    }
  }

}
