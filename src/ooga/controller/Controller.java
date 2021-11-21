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
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.PreferencesParser;
import ooga.controller.IO.keyTracker;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.GameStartupPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller implements ControllerInterface {

  private static final double SECONDS_ANIMATION_BASE = 20 / 60.0;
  private static final double SECOND_DELAY = 20.0 / 60;
  public static final int ROWS = 11;
  public static final int COLS = 11;

  private JsonParserInterface jsonParser;
  private keyTracker keyTracker;
  private VanillaGame vanillaGame;
  private GameStartupPanel gameStartupPanel;
  private Timeline myAnimation;
  private PreferencesParser preferencesParser;
  private GameStartupPanel panel;
  private Map<String, List<Position>> wallMap;
  private boolean isPaused;
  private int count;
  private static final Logger LOG = LogManager.getLogger(Controller.class);


  public Controller(String language, Stage stage) {
    count++;
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    myAnimation.play();
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
    preferencesParser = new PreferencesParser();
    gameStartupPanel = new GameStartupPanel(stage);
    isPaused = false;
  }

  // TODO: properly handle exception
  @Override
  public Map<String, List<Position>> uploadFile(File file)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    preferencesParser.uploadFile(file);
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.getWallMap());
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> {
          try {
            vanillaGame = new VanillaGame(vanillaGameDataInterface);
          } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InputMismatchException("Error occurred in backend reflection");
          }
        });
    jsonParser.uploadFile(preferencesParser.getStartingConfig());

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
      vanillaGame.step();
    }
  }

  @Override
  public void updatePressedKey(KeyEvent event) {
//    LOG.info("updating pressed key to {}", event.getCode());
    vanillaGame.getBoard().setPlayerDirection(keyTracker.getPressedKey(event));
  }

}
