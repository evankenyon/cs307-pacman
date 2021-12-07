package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.thegreshams.firebase4j.error.FirebaseException;
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
import ooga.model.util.GameStatus;
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
  private static final String METHOD_MAPPINGS_FILENAME = "MethodMappings";

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
  private String password;
  private Runnable playPauseRun;
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
    } catch (FirebaseException e) {
      new ErrorPopups(e, myLanguage, "FirebaseError");
    }

    new LoginView(myStage, this);
    isPaused = true;
  }

  public Set<String> getFirebaseFilenames()
      throws FirebaseException, UnsupportedEncodingException {
    return firebaseReader.getFileNames();
  }

//  @Deprecated
//  public void createUser(String username, String password, File imageFile) throws IOException {
//    profileGenerator.createUser(username, password, imageFile);
//  }

  public User createUser(String username, String password, File imageFile)
      throws IOException, InterruptedException, IllegalArgumentException {
    profileGenerator.createUser(username, password, imageFile);
    return login(username, password);
  }

  public User login(String username, String password) throws IOException {
    currUser = profileGenerator.login(username, password);
    this.password = password;
    return profileGenerator.login(username, password);
  }

  public User getUser() {
    return currUser;
  }

  public void updateUsername(String updatedUsername) throws IOException {
    profileGenerator.changeProfileUsername(currUser.username(), password, updatedUsername);
    currUser = login(updatedUsername, password);
  }

  public void updatePassword(String updatedPassword) throws IOException {
    profileGenerator.changeProfilePassword(currUser.username(), password, updatedPassword);
    password = updatedPassword;
  }

  public void removeFile(String file) throws IOException {
    profileGenerator.removeFavoriteFile(currUser.username(), password, file);
    currUser = login(currUser.username(), password);
  }

  @Deprecated
  public void updateImage(File updatedImageFile) throws IOException {
    profileGenerator.updateProfilePicture(currUser.username(), password, updatedImageFile);
    currUser = login(currUser.username(), password);
  }

//  public void updateString(String string, String type)
//      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//    ResourceBundle methodMappings = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, METHOD_MAPPINGS_FILENAME));
//    Method fileUpdateMethod = ProfileGenerator.class.getDeclaredMethod(methodMappings.getString(type), String.class, String.class, String.class);
//    fileUpdateMethod.invoke(profileGenerator, currUser.username(), password, string);
//    currUser = login(currUser.username(), password);
//    System.out.println(currUser.username());
//  }

  public void updateFile(File file, String type)
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ResourceBundle methodMappings = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, METHOD_MAPPINGS_FILENAME));
    Method fileUpdateMethod = ProfileGenerator.class.getDeclaredMethod(methodMappings.getString(type), String.class, String.class, File.class);
    fileUpdateMethod.invoke(profileGenerator, currUser.username(), password, file);
    currUser = login(currUser.username(), password);
  }


  public UserPreferences uploadFirebaseFile(String fileName)
      throws IOException, NoSuchMethodException, IllegalAccessException, FirebaseException {
    setupPreferencesAndVanillaGame(firebaseReader.getFile(fileName));
    return myPreferences;
  }

  // TODO: properly handle exception
  @Deprecated
  @Override
  public UserPreferences uploadFile(File file)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    setupPreferencesAndVanillaGame(JSONObjectParser.parseJSONObject(file));
    return myPreferences;
  }

  public UserPreferences uploadFile(String filename)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    setupPreferencesAndVanillaGame(JSONObjectParser.parseJSONObject(new File(filename)));
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
            vanillaGame.getBoard().addGameStatusConsumer(gameStatus -> updateUserStats(gameStatus));
          } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            new ErrorPopups(e, myLanguage, "reflectionError");
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
    playPauseRun.run();
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

  private void updateUserStats(GameStatus gameStatus) {
    System.out.println("test");
    if(gameStatus == GameStatus.LOSS || gameStatus == GameStatus.WIN) {
      try {
        profileGenerator.updateUserStats(currUser.username(), password, vanillaGame.getBoard().getMyPacScore(), gameStatus == GameStatus.WIN);
      } catch (Exception e) {
        //TODO: fix
        e.printStackTrace();
      }
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
    new MainView(this, getVanillaGame(), stage, myViewMode, myPreferences, currUser);
  }

  /**
   * Toggles the animation status to paused if running or running if paused.
   * Used in MainView to pause/start animation to show and hide win/loss alerts
   */
  public void toggleAnimation() {
    if (myAnimation.getStatus() == Status.PAUSED) myAnimation.play();
    else if (myAnimation.getStatus() == Status.RUNNING) myAnimation.pause();
  }

  /**
   * Adds a Runnable to change the image on the play pause button in the BottomView
   *
   * @param runnable is the runnable from BottomView
   */
  public void addPlayPauseRun(Runnable runnable) {
    playPauseRun = runnable;
  }
}