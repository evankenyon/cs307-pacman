package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ooga.Main;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.keyTracker;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.mainView.MainView;

public class Controller implements ControllerInterface {
  private final static double SECONDS_ANIMATION_BASE = 20 / 60.0;

  private JsonParserInterface jsonParser;
  private Map<String, List<Position>> wallMap;
  private keyTracker keyTracker;
  private VanillaGame vanillaGame;
  private MainView mainView;
  private Timeline myAnimation;
  private double secondDelay;


  public Controller(String language, Stage stage) {
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    mainView = new MainView();
    secondDelay = SECONDS_ANIMATION_BASE;
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
  }

  // TODO: properly handle exception
  @Override
  public void uploadFile(File file) throws IOException {
    jsonParser.addVanillaGameDataConsumer(vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.getWallMap());
    jsonParser.addVanillaGameDataConsumer(
    vanillaGameDataInterface -> {
      try {
        vanillaGame = new VanillaGame(vanillaGameDataInterface);
      } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
        throw new InputMismatchException("Error occurred in backend reflection");
      }
    });
    jsonParser.uploadFile(file);
  }

  public Map<String, List<Position>> getWallMap() {
    return wallMap;
  }

  @Override
  public void updatePressedKey(KeyEvent event) {
    keyTracker.getPressedKey(event);
  }

}
