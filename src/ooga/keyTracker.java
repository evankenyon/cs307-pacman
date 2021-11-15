package ooga;

import java.awt.event.KeyEvent;


public class keyTracker {

  /**
   * Communicates the key pressed by user to the model so that it can move the player avatar
   * @param event is key pressed by player which should be one of the arrow keys
   * @return String indicating which arrow key was pressed, or that the key was not an arrow key
   */
  public String getPressedKey(KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
      return "left";
    }
    else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
      return "right";
    }
    else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
      return "down";
    }
    else if (event.getKeyCode() == KeyEvent.VK_UP) {
     return "up";
    }
    else {
      return "not-arrow";
    }
  }





}
