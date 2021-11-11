package ooga.view.center;

import java.awt.Canvas;
import java.awt.Rectangle;
import ooga.model.VanillaGame;

public class BoardView {

  private VanillaGame myGame;
  private Canvas myBoardCanvas;

  public BoardView (VanillaGame game) {
    myGame = game;
    myBoardCanvas = new Canvas();
    initiateBoard();
  }

  private void initiateBoard() {
    Rectangle board = new Rectangle();
  }

}
