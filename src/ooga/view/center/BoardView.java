package ooga.view.center;

import java.awt.Canvas;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import ooga.controller.IO.JsonParser;
import ooga.model.VanillaGame;
import ooga.model.util.Position;

public class BoardView {

  private VanillaGame myGame;
  private JsonParser myParser;
  private GridPane myBoardPane;

  public BoardView (VanillaGame game, JsonParser parser) {
    myGame = game;
    myParser = parser;
    myBoardPane = new GridPane();
    initiateBoard();
  }

  private void initiateBoard() {
    Rectangle board = new Rectangle();
//    makeWalls(myParser.getWallMapPositions());
  }

  private void makeWalls(List<Position> positions) {
    for (Position p : positions) {
      myBoardPane.add(new Rectangle(p.getX(), p.getY()), p.getX(), p.getY()); //TODO: input height,width
    }
  }

}
