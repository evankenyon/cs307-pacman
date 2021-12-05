package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.IO.FirebaseReader;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.PreferencesParser;
import ooga.controller.IO.ProfileGenerator;
import ooga.controller.IO.GameSaver;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.controller.IO.keyTracker;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.mainView.MainView;
import ooga.view.popups.ErrorPopups;
import ooga.view.startupView.GameStartupPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Controller implements ControllerInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      Controller.class.getPackageName() + ".resources.";
  private static final String EXCEPTION_MESSAGES_FILENAME = "Exceptions";
  private static final String MAGIC_VALUES_FILENAME = "ControllerMagicValues";

  private final JsonParserInterface jsonParser;
  private final keyTracker keyTracker;
  private VanillaGame vanillaGame;
  private final Timeline myAnimation;
  private final PreferencesParser preferencesParser;
  private GameStartupPanel panel;
  private Map<String, List<Position>> wallMap;
  private boolean isPaused;
  private final String myLanguage;
  private int rows;
  private int cols;
  private FirebaseReader firebaseReader;
  private JSONObject originalJson;
  private final Stage myStage;
  private UserPreferences myPreferences;
  private ProfileGenerator profileGenerator;
  private GameStartupPanel gameStartupPanel;
  private User currUser;
  private static final Logger LOG = LogManager.getLogger(Controller.class);

  private final ResourceBundle magicValues;

  public Controller(String language, Stage stage) {
    magicValues = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, MAGIC_VALUES_FILENAME));
    myLanguage = language;
    myStage = stage;
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    double secondDelay = Double.parseDouble(magicValues.getString("secondDelay"));
    myAnimation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(secondDelay), e -> step(secondDelay)));
    myAnimation.play();
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
    preferencesParser = new PreferencesParser();
    profileGenerator = new ProfileGenerator();
    try {
      firebaseReader = new FirebaseReader();
    } catch (IOException e) {
      // TODO: fix
      e.printStackTrace();
    }

    gameStartupPanel = new GameStartupPanel(stage); //TODO: pass this Controller into GameStartupPanel instead of making a new Controller inside the class
    isPaused = false;

  }

  public Set<String> getFirebaseFilenames() throws InterruptedException {
    return firebaseReader.getFileNames();
  }



  public void createUser(String username, String password, File imageFile)
      throws IOException, InterruptedException {
    profileGenerator.createUser(username, password, imageFile);
  }

  public User login(String username, String password) throws IOException {
    return profileGenerator.login(username, password);
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
            new ErrorPopups(myLanguage, "reflectionError");
            LOG.info("my exception is {}", e.toString());
            ResourceBundle exceptionMessages = ResourceBundle.getBundle(
                String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, EXCEPTION_MESSAGES_FILENAME));
            throw new InputMismatchException(exceptionMessages.getString("BadReflection"));
          }
        });
    if (!JSONObjectParser.parseJSONObject(file).toMap()
        .containsKey(magicValues.getString("PlayerKey"))) {
      preferencesParser.uploadFile(file);
      originalJson = JSONObjectParser.parseJSONObject(preferencesParser.getStartingConfig());
    } else {
      originalJson = JSONObjectParser.parseJSONObject(file);
    }
    jsonParser.parseJSON(originalJson);
    myPreferences = new UserPreferences(wallMap, jsonParser.getRows(), jsonParser.getCols(),
        preferencesParser.getImagePaths(), preferencesParser.getColors(),
        preferencesParser.getStyle(), myLanguage);
    return myPreferences;
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
    vanillaGame.setPlayerDirection(keyTracker.getPressedKey(event));
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
   * Getter method to get the animation speed. Used for testing
   *
   * @return double animation rate
   */
  public double getAnimationSpeed() {
    return myAnimation.getRate();
  }


  public void saveFile() throws IOException {
    GameSaver saver = new GameSaver(vanillaGame);
    saver.saveGame();
  }

//  /**
//   * Getter method to get the uploaded file. Used to reload a new game.
//   *
//   * @return File myFile
//   */
//  public File getFile() { return myFile; }
//
//  /**
//   * Getter method to get the current stage. Used to reload a new game.
//   *
//   * @return Stage myStage
//   */
//  public Stage getStage() { return myStage; }

  /**
   * Restarts the game with the same file that was originally uploaded. Called from BottomView when
   * user clicks on "Restart" button
   */
  public void restartGame() {
    jsonParser.parseJSON(originalJson);
    new MainView(this, getVanillaGame(), myStage, myPreferences);
  }
}