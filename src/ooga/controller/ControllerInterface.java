package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.controller.IO.UserPreferences;
import ooga.model.VanillaGame;
import ooga.model.util.Position;

public interface ControllerInterface {

  public UserPreferences uploadFile(File file)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

  public void updatePressedKey(KeyEvent event);

  public void setAnimationSpeed(double factor);

  public void pauseOrResume();

  public VanillaGame getVanillaGame();
}
