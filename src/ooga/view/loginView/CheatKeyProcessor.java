package ooga.view.loginView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import ooga.view.popups.ErrorPopups;

/**
 * Class to create a MainView based on the inputted cheat code. Directly creates a MainView with a
 * file that corresponds to the cheat key.
 *
 * @author Dane Erickson
 */
public class CheatKeyProcessor {

  public static final String LOGIN_PATH = "ooga.view.loginView.";
  public static final String DATA_FILES_PATH = "data/basic_examples/";
  public static final String CHEAT_IMAGE = "data/images/blue_up.gif";
  public static final String CHEAT_USERNAME = "cheater";
  public static final String CHEAT_PASSWORD = "cheat_password";

  private Stage myStage;
  private ResourceBundle cheatFiles;
  private String fileName;
  private Controller myController;

  /**
   * Constructor to create a CheatKeyProcessor to create the MainView from the cheat key
   *
   * @param stage is the stage from LoginView that is replaced
   * @param controller is the Controller to create the user
   * @param cheatKey is the String cheatKey
   */
  public CheatKeyProcessor(Stage stage, Controller controller, String cheatKey) {
    cheatFiles = ResourceBundle.getBundle(String.format("%s%s", LOGIN_PATH, "cheatFiles"));
    myStage = stage;
    myController = controller;
    fileName = String.format("%s%s", DATA_FILES_PATH, cheatFiles.getString(cheatKey));
    try {
      makeMainView();
    } catch (IOException e) {
      new ErrorPopups(e, LANGUAGE, "SignInError");
    }
  }

  private void makeMainView() throws IOException {
    UserPreferences prefs = null;
    try {
      prefs = myController.uploadFile(fileName);
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      new ErrorPopups(e, LANGUAGE, "InvalidFile");
    }
    try {
      User user = myController.createUser(CHEAT_USERNAME, CHEAT_PASSWORD, new File(CHEAT_IMAGE));
      new MainView(myController, myController.getVanillaGame(), myStage, VIEW_MODE, prefs, user);
    } catch (Exception e) {
      User user = myController.login(CHEAT_USERNAME, CHEAT_PASSWORD);
      new MainView(myController, myController.getVanillaGame(), myStage, VIEW_MODE, prefs, user);
    }
  }


}
