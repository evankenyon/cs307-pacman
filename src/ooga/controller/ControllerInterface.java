package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.model.util.Position;

public interface ControllerInterface {

  public Map<String, List<Position>> uploadFile(File file) throws IOException;

  public void updatePressedKey(KeyEvent event);
}
