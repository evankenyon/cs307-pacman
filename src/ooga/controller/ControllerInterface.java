package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.input.KeyEvent;
import ooga.controller.IO.UserPreferences;
import ooga.model.GameEngine;

public interface ControllerInterface {

  @Deprecated
  public UserPreferences uploadFile(File file)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

  public UserPreferences uploadFile(String fileName)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

  public void updatePressedKey(KeyEvent event);

  public void setAnimationSpeed(double factor);

  public void pauseOrResume();

  public GameEngine getVanillaGame();
}
