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
import ooga.model.GameEngine;
import ooga.model.util.GameStatus;
import ooga.model.util.Position;
import ooga.view.loginView.LoginView;
import ooga.view.mainView.MainView;
import ooga.view.popups.ErrorPopups;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Purpose: This class is the intermediary between the frontend and model. Specifically, it takes in
 * raw data from the frontend and transforms it into the necessary format for the model. It also
 * starts the animation timeline. Dependencies: File, IOException, UnsupportedEncodingException,
 * InvocationTargetException, Method, InputMismatchException, List, Map, ResourceBundle, Status,
 * Set, KeyFrame, Timeline, KeyEvent, Stage, Duration, firebase4j, all classes in controller.IO,
 * GameEngine, GameStatus, Position, LoginView, MainView, ErrorPopups, log4j, json-java Example:
 * Instantiate this class in a main method in order to start the game, and use this class on the
 * frontend in order to let the model know that the user input something that should change the
 * model
 *
 * @author Evan Kenyon
 */
public class Controller implements ControllerInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      Controller.class.getPackageName() + ".resources.";
  private static final String EXCEPTION_MESSAGES_FILENAME = "Exceptions";
  private static final String MAGIC_VALUES_FILENAME = "ControllerMagicValues";
  private static final String METHOD_MAPPINGS_FILENAME = "MethodMappings";

  private final JsonParserInterface jsonParser;
  private final keyTracker keyTracker;
  private GameEngine gameEngine;
  private final Timeline myAnimation;
  private final PreferencesParser preferencesParser;
  private Map<String, List<Position>> wallMap;
  private boolean isPaused;
  private final String myLanguage;
  private final String myViewMode;
  private FirebaseReader firebaseReader;
  private JSONObject originalStartingConfigJson;
  private final Stage myStage;
  private UserPreferences myPreferences;
  private ProfileGenerator profileGenerator;
  private User currUser;
  //TODO: remove
  private String password;
  private Runnable playPauseRun;
  private static final Logger LOG = LogManager.getLogger(Controller.class);

  private final ResourceBundle magicValues;

  /**
   * Purpose: Instantiates all the data structures in this class, the animation timeline, and a
   * LoginView
   *
   * @param language the language to be displayed to the user
   * @param stage    the stage to begin the view on
   * @param viewMode the initial style for the game
   */
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

  /**
   * Purpose: Get the firebase filenames
   *
   * @return the firebase filenames
   * @throws FirebaseException            thrown if there is a firebase error
   * @throws UnsupportedEncodingException never thrown, firebase4j is just badly designed in that it
   *                                      throws an exception that is never actually thrown
   */
  public Set<String> getFirebaseFilenames()
      throws FirebaseException, UnsupportedEncodingException {
    return firebaseReader.getFileNames();
  }

//  @Deprecated
//  public void createUser(String username, String password, File imageFile) throws IOException {
//    profileGenerator.createUser(username, password, imageFile);
//  }

  /**
   * Purpose: create a user through ProfileGenerator
   *
   * @param username  username for new user
   * @param password  password for new user
   * @param imageFile image file for new user
   * @return the new user packaged into a User object
   * @throws IOException              thrown if path in ProfileGenerator is bad
   * @throws IllegalArgumentException if login fails after user is created
   */
  public User createUser(String username, String password, File imageFile)
      throws IOException, IllegalArgumentException {
    profileGenerator.createUser(username, password, imageFile);
    return login(username, password);
  }

  /**
   * Purpose: Log in as a user through ProfileGenerator
   *
   * @param username username for user to log in as
   * @param password password for user to log in as
   * @return the user that was logged in as packaged into a User object
   * @throws IOException thrown if path in ProfileGenerator is bad
   */
  public User login(String username, String password) throws IOException {
    currUser = profileGenerator.login(username, password);
    this.password = password;
    return profileGenerator.login(username, password);
  }

  /**
   * Purpose: gets the current user that is maintained by this controller Assumptions: login is
   * called before this method
   *
   * @return the current user that is maintained by this controller
   */
  public User getUser() {
    return currUser;
  }

  /**
   * Purpose: update the current user's username Assumptions: login is called before this method
   *
   * @param updatedUsername the current user's new username
   * @throws IOException thrown if path in ProfileGenerator is bad
   */
  public void updateUsername(String updatedUsername) throws IOException {
    profileGenerator.changeProfileUsername(currUser.username(), password, updatedUsername);
    currUser = login(updatedUsername, password);
  }

  /**
   * Purpose: update the current user's password Assumptions: login is called before this method
   *
   * @param updatedPassword the current user's new password
   * @throws IOException thrown if path in ProfileGenerator is bad
   */
  public void updatePassword(String updatedPassword) throws IOException {
    profileGenerator.changeProfilePassword(currUser.username(), password, updatedPassword);
    password = updatedPassword;
  }

  /**
   * Purpose: remove a file from the current user's favorite files list Assumptions: login is called
   * before this method
   *
   * @param file the String representing the file to be removed from the user's favorite files list
   * @throws IOException thrown if path in ProfileGenerator is bad
   */
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

  /**
   * Purpose: update a file (specifically add a favorite file or change a profile picture) for a
   * user profile
   *
   * @param file file that will be part of the user profile update
   * @param type determines the method to be called in the ProfileGenerator class (i.e. which
   * @throws IOException
   * @throws NoSuchMethodException     thrown if type argument results in trying to call a method
   *                                   that does not exist
   * @throws InvocationTargetException thrown if underlying method for updating a user profile file
   *                                   throws an exception
   * @throws IllegalAccessException    thrown if the login information for the user is incorrect
   */
  public void updateFile(File file, String type)
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ResourceBundle methodMappings = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, METHOD_MAPPINGS_FILENAME));
    Method fileUpdateMethod = ProfileGenerator.class.getDeclaredMethod(
        methodMappings.getString(type), String.class, String.class, File.class);
    fileUpdateMethod.invoke(profileGenerator, currUser.username(), password, file);
    currUser = login(currUser.username(), password);
  }

  /**
   * Purpose: Create a new model based on a firebase file input
   *
   * @param fileName the name of the firebase file that the model will be based on
   * @return user preferences to start the game with user preferred images, colors, and style
   * @throws IOException           thrown if file is invalid
   * @throws NoSuchMethodException thrown if PreferencesParser throws this (see that class for more
   *                               details)
   * @throws FirebaseException     thrown if FirebaseReader throws this (see that class for more *
   *                               details)
   */
  public UserPreferences uploadFirebaseFile(String fileName)
      throws IOException, NoSuchMethodException, FirebaseException, IllegalAccessException {
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

  /**
   * Purpose: Create a new model based on local file input
   *
   * @param filename String that represents a local json file (starting config or preferences)
   * @return user preferences to start the game with user preferred images, colors, and style
   * @throws IOException           thrown if file is invalid
   * @throws NoSuchMethodException thrown if PreferencesParser throws this (see that class for more
   *                               details)
   * @throws FirebaseException     thrown if FirebaseReader throws this (see that class for more *
   *                               details)
   */
  public UserPreferences uploadFile(String filename)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    setupPreferencesAndVanillaGame(JSONObjectParser.parseJSONObject(new File(filename)));
    return myPreferences;
  }

  private void setupPreferencesAndVanillaGame(JSONObject json)
      throws IOException, NoSuchMethodException {
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.wallMap());
    jsonParser.addVanillaGameDataConsumer(
        vanillaGameDataInterface -> {
          try {
            gameEngine = new GameEngine(vanillaGameDataInterface);
            gameEngine.getBoard().addGameStatusConsumer(gameStatus -> updateUserStats(gameStatus));
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

  /**
   * Purpose: Set the animation speed via a factor (ex. for factor = 1.2, animation will be 1.2
   * times faster)
   *
   * @param factor the factor by which to increase the animation speed
   */
  @Override
  public void setAnimationSpeed(double factor) {
    myAnimation.setRate(factor);
  }

  /**
   * Purpose: pause or resume the animation
   */
  @Override
  public void pauseOrResume() {
    isPaused = !isPaused;
    playPauseRun.run();
  }

  /**
   * Purpose: get the vanilla game object
   *
   * @return the vanilla game object
   */
  @Override
  public GameEngine getVanillaGame() {
    return gameEngine;
  }

  private void step(double elapsedTime) {
    if (gameEngine != null && !isPaused) {
      gameEngine.step();
    }
  }

  private void updateUserStats(GameStatus gameStatus) {
    System.out.println("test");
    if (gameStatus == GameStatus.LOSS || gameStatus == GameStatus.WIN) {
      try {
        profileGenerator.updateUserStats(currUser.username(), password,
            gameEngine.getBoard().getMyPacScore(), gameStatus == GameStatus.WIN);
      } catch (Exception e) {
        //TODO: fix
        e.printStackTrace();
      }
    }
  }

  /**
   * Purpose: update the pressed key in order to change the player's direction
   *
   * @param event the KeyEvent which represents the most recently pressed key
   */
  @Override
  public void updatePressedKey(KeyEvent event) {
//    LOG.info("updating pressed key to {}", event.getCode());
    gameEngine.setPlayerDirection(keyTracker.getPressedKey(event));
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

  /**
   * Purpose: Saves the current model as a starting config
   *
   * @throws IOException thrown if GameSaver throws it (see GameSaver for more details)
   */
  public void saveFile() throws IOException {
    //GameSaver saver = new GameSaver(gameEngine);
    //saver.saveGame();
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
   * Toggles the animation status to paused if running or running if paused. Used in MainView to
   * pause/start animation to show and hide win/loss alerts
   */
  public void toggleAnimation() {
    if (myAnimation.getStatus() == Status.PAUSED) {
      myAnimation.play();
    } else if (myAnimation.getStatus() == Status.RUNNING) {
      myAnimation.pause();
    }
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