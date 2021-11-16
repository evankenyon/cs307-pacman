package ooga.controller;

import java.io.File;
import java.io.IOException;
import javafx.scene.input.KeyEvent;

public interface ControllerInterface {

  public void uploadFile(File file) throws IOException;

  public void updatePressedKey(KeyEvent event);
}
