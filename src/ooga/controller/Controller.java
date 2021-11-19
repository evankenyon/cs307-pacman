package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.Main;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.keyTracker;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.GameStartupPanel;
import ooga.view.mainView.MainView;

public class Controller implements ControllerInterface {
  private static final double SECONDS_ANIMATION_BASE = 20 / 60.0;
  private static final double SECOND_DELAY = 1.0 / 60;
  public static final int ROWS = 11;
  public static final int COLS = 11;

  private JsonParserInterface jsonParser;
  private keyTracker keyTracker;
  private VanillaGame vanillaGame;
  private GameStartupPanel gameStartupPanel;
  private Timeline myAnimation;
  private GameStartupPanel panel;
  private Map<String, List<Position>> wallMap;
  private boolean isPaused;
  private int count;


  public Controller(String language, Stage stage) {
    count++;
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    myAnimation.play();
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
    gameStartupPanel = new GameStartupPanel(stage);
    isPaused = false;
  }

  // TODO: properly handle exception
  @Override
  public Map<String, List<Position>> uploadFile(File file) throws IOException {
    jsonParser.addVanillaGameDataConsumer(vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.getWallMap());
    jsonParser.addVanillaGameDataConsumer(
    vanillaGameDataInterface -> {
      try {
        vanillaGame = new VanillaGame(vanillaGameDataInterface);
      } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        throw new InputMismatchException("Error occurred in backend reflection");
      }
    });
    jsonParser.uploadFile(file);

    return wallMap;
  }

  @Override
  public void setAnimationSpeed(double factor) {
    myAnimation.setRate(factor);
  }

  @Override
  public void pauseOrResume() {
    isPaused = !isPaused;
  }

  @Override
  public VanillaGame getVanillaGame() {
    return vanillaGame;
  }

  private void step(double elapsedTime) {
    if (vanillaGame != null && !isPaused) {
      System.out.println(count);
      count++;
      vanillaGame.step();
    }
  }

  @Override
  public void updatePressedKey(KeyEvent event) {
    keyTracker.getPressedKey(event);
  }

}
