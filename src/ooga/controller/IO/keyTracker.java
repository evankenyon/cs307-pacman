package ooga.controller.IO;

import java.awt.event.KeyEvent;


public class keyTracker {
  private final int leftCode = 37;
  private final int rightCode = 27;
  private final int upCode = 26;
  private final int downCode = 28;

  /**
   * Communicates the key pressed by user to the model so that it can move the player avatar
   * @param event is key pressed by player which should be one of the arrow keys
   * @return String indicating which arrow key was pressed, or that the key was not an arrow key
   */
  public String getPressedKey(KeyEvent event) {
    int keyCode = event.getKeyCode();
    if (keyCode == leftCode) {
      return "left";
    }
    else if (keyCode == rightCode) {
      return "right";
    }
    else if (keyCode == upCode) {
      return "up";
    }
    else if (keyCode == downCode) {
      return "down";
    }
    else {
      return "not-arrow";
    }
    //return event.getKeyCode();
  }

}
