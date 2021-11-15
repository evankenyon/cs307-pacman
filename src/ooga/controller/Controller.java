package ooga.controller;

import java.io.File;
import java.io.IOException;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.keyTracker;

public class Controller implements ControllerInterface {
  private JsonParser jsonParser;
  private keyTracker keyTracker;

  public Controller() {
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
  }

  // TODO: properly handle exception
  @Override
  public void uploadFile(File file) throws IOException {
    jsonParser.uploadFile(file);
  }

  @Override
  public void updatePressedKey(KeyEvent event) {
    keyTracker.getPressedKey(event);
  }


}
