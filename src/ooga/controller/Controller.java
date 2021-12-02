package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.PreferencesParser;
import ooga.controller.IO.GameSaver;
import ooga.controller.IO.UserPreferences;
import ooga.controller.IO.keyTracker;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.startupView.GameStartupPanel;
import ooga.view.popups.ErrorPopups;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller implements ControllerInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      Controller.class.getPackageName() + ".resources.";
  private static final String EXCEPTION_MESSAGES_FILENAME = "Exceptions";
  private static final String MAGIC_VALUES_FILENAME = "ControllerMagicValues";

  private JsonParserInterface jsonParser;
  private keyTracker keyTracker;
  private VanillaGame vanillaGame;
  private GameStartupPanel gameStartupPanel;
  private Timeline myAnimation;
  private PreferencesParser preferencesParser;
  private GameStartupPanel panel;
  private Map<String, List<Position>> wallMap;
  private boolean isPaused;
  private String myLanguage;
  private int rows;
  private int cols;
  private static final Logger LOG = LogManager.getLogger(Controller.class);

  private ResourceBundle magicValues;


  public Controller(String language, Stage stage) {
    magicValues = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, MAGIC_VALUES_FILENAME));
    myLanguage = language;
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    double secondDelay = Double.parseDouble(magicValues.getString("secondDelay"));
    myAnimation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(secondDelay), e -> step(secondDelay)));
    myAnimation.play();
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
    preferencesParser = new PreferencesParser();
    gameStartupPanel = new GameStartupPanel(stage);
    isPaused = false;
  }

  // TODO: properly handle exception
  @Override
  public UserPreferences uploadFile(File file)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.wallMap());
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> {
          try {
            vanillaGame = new VanillaGame(vanillaGameDataInterface);
          } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            new ErrorPopups(myLanguage).reflectionErrorPopup();
            ResourceBundle exceptionMessages = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, EXCEPTION_MESSAGES_FILENAME));
            throw new InputMismatchException(exceptionMessages.getString("BadReflection"));
          }
        });
    if(!JSONObjectParser.parseJSONObject(file).toMap().containsKey(magicValues.getString("PlayerKey"))) {
      preferencesParser.uploadFile(file);
      jsonParser.uploadFile(preferencesParser.getStartingConfig());
    } else {
      jsonParser.uploadFile(file);
    }
    return new UserPreferences(wallMap, jsonParser.getRows(), jsonParser.getCols(), preferencesParser.getImagePaths(), preferencesParser.getColors(), preferencesParser.getStyle(), myLanguage);
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

  /**
   * Getter method to return whether simulation is paused or not. Used for testing
   *
   * @return boolean isPaused
   */
  public boolean getPlayPause() {
    return isPaused;
  }

  /**
   * Getter method to get the animation speed. Used for teting
   *
   * @return double animation rate
   */
  public double getAnimationSpeed() { return myAnimation.getRate(); }

  public void saveFile() throws IOException {
    GameSaver saver = new GameSaver();
    saver.saveGame(vanillaGame.getBoard().getGameState());
  }
}
