package ooga.controller.IO;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class keyTracker {

  private int dx;
  private int dy;

  public void getPressedKey(KeyEvent event) {
    if (event.getCode() == KeyCode.LEFT) {
      dx = -1;
    } else if (event.getCode() == KeyCode.RIGHT) {
      dx = 1;
    } else if (event.getCode() == KeyCode.DOWN) {
      dx = 1;
    } else if (event.getCode() == KeyCode.UP) {
      dx = -1;
    }

  }

  public int getNewX(int currentX) {
    return currentX + dx;
  }

  public int getNewY(int currentY) {
    return currentY + dx;
  }

}
