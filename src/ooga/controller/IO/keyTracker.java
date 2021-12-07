package ooga.controller.IO;

import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class keyTracker {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      keyTracker.class.getPackageName() + ".resources.";
  private static final String KEY_DIRECTION_MAP_FILENAME = "KeyDirectionMap";

  private ResourceBundle keyDirectionMap;

  public keyTracker() {
    keyDirectionMap = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, KEY_DIRECTION_MAP_FILENAME));
  }
  // no need to translate to string; just account for non arrow case

  /**
   * Communicates the key pressed by user to the model so that it can move the player avatar
   *
   * @param event is key pressed by player which should be one of the arrow keys
   * @return String indicating which arrow key was pressed, or that the key was not an arrow key
   */
  public String getPressedKey(KeyEvent event) {
    KeyCode code = event.getCode();
    if (keyDirectionMap.containsKey(code.toString())) {
      return keyDirectionMap.getString(code.toString());
    }
    return keyDirectionMap.getString("default");
  }

}
