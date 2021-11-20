package ooga.controller.IO;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class keyTracker {

  // no need to translate to string; just account for non arrow case
  /**
   * Communicates the key pressed by user to the model so that it can move the player avatar
   * @param event is key pressed by player which should be one of the arrow keys
   * @return String indicating which arrow key was pressed, or that the key was not an arrow key
   */
  public String getPressedKey(KeyEvent event) {
    KeyCode code = event.getCode();
    if (code == KeyCode.A) {
      return "left";
    }
    else if (code == KeyCode.D) {
      return "right";
    }
    else if (code == KeyCode.W) {
      return "up";
    }
    else if (code == KeyCode.S) {
      return "down";
    }
    else {
      return "not-arrow";
    }
  }

}
