package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.IO.FirebaseReader;
import ooga.controller.IO.GameSaver;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.PreferencesParser;
import ooga.controller.IO.ProfileGenerator;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.controller.IO.keyTracker;
import ooga.controller.IO.utils.JSONObjectParser;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.loginView.LoginView;
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
  private final String myViewMode;
  private int rows;
  private int cols;
  private FirebaseReader firebaseReader;
  private JSONObject originalStartingConfigJson;
  private final Stage myStage;
  private UserPreferences myPreferences;
  private ProfileGenerator profileGenerator;
  private GameStartupPanel gameStartupPanel;
  private User currUser;
  private static final Logger LOG = LogManager.getLogger(Controller.class);

  private final ResourceBundle magicValues;

  public Controller(String language, Stage stage, String viewMode) {
    magicValues = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, MAGIC_VALUES_FILENAME));
    myLanguage = language;
    myViewMode = viewMode;
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
//    try {
//      new UserInformationView(this, profileGenerator.login("dane", "dane"), stage);
//    } catch(Exception e) {
//
//    }
//
    new LoginView(myStage, this);
//    gameStartupPanel = new GameStartupPanel(stage); //TODO: pass this Controller into GameStartupPanel instead of making a new Controller inside the class
    isPaused = true;
  }

//  public Set<String> getFirebaseFilenames() throws InterruptedException {
//    return firebaseReader.getFileNames();
//  }

//  @Deprecated
//  public void createUser(String username, String password, File imageFile) throws IOException {
//    profileGenerator.createUser(username, password, imageFile);
//  }

  public User createUser(String username, String password, File imageFile)
      throws IOException, InterruptedException {
    profileGenerator.createUser(username, password, imageFile);
    return login(username, password);
  }

  public User login(String username, String password) throws IOException {
    return profileGenerator.login(username, password);
  }

//  public UserPreferences uploadFirebaseFile(String fileName)
//      throws InterruptedException, IOException, NoSuchMethodException, IllegalAccessException {
//    //setupPreferencesAndVanillaGame(firebaseReader.getFile(fileName));
//    return myPreferences;
//  }

  // TODO: properly handle exception
  @Override
  public UserPreferences uploadFile(File file)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    setupPreferencesAndVanillaGame(JSONObjectParser.parseJSONObject(file));
    return myPreferences;
  }

  private void setupPreferencesAndVanillaGame(JSONObject json)
      throws IOException, NoSuchMethodException, IllegalAccessException {
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.wallMap());
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> {
          try {
            vanillaGame = new VanillaGame(vanillaGameDataInterface);
          } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            new ErrorPopups(myLanguage, "reflectionError");
            ResourceBundle exceptionMessages = ResourceBundle.getBundle(
                String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, EXCEPTION_MESSAGES_FILENAME));
            throw new InputMismatchException(exceptionMessages.getString("BadReflection"));
          }
        });
    if (!json.toMap()
        .containsKey(magicValues.getString("PlayerKey"))) {
      preferencesParser.parseJSON(json);
      originalStartingConfigJson = JSONObjectParser.parseJSONObject(
          preferencesParser.getStartingConfig());
    } else {
      originalStartingConfigJson = json;
    }
    jsonParser.parseJSON(originalStartingConfigJson);
    myPreferences = new UserPreferences(wallMap, jsonParser.getRows(), jsonParser.getCols(),
        preferencesParser.getImagePaths(), preferencesParser.getColors(),
        preferencesParser.getStyle(), myLanguage);
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
//   * Restarts the game with the same file that was originally uploaded. Called from BottomView when
//   * user clicks on "Restart" button
//   */
//  @Deprecated
//  public void restartGame() {
//    jsonParser.parseJSON(originalStartingConfigJson);
//    new MainView(this, getVanillaGame(), newStage, myViewMode, myPreferences);
//  }

  /**
   * Restarts the game with the same file that was originally uploaded. Called from BottomView when
   * user clicks on "Restart" button
   */
  public void restartGame(Stage stage) {
    jsonParser.parseJSON(originalStartingConfigJson);
    isPaused = true;
    new MainView(this, getVanillaGame(), stage, myViewMode, myPreferences);
  }

  /**
   * Toggles the animation status to paused if running or running if paused.
   * Used in MainView to pause/start animation to show and hide win/loss alerts
   */
  public void toggleAnimation() {
    if (myAnimation.getStatus() == Status.PAUSED) myAnimation.play();
    else if (myAnimation.getStatus() == Status.RUNNING) myAnimation.pause();
  }
}