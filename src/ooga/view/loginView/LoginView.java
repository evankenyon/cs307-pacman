package ooga.view.loginView;

import static ooga.Main.LANGUAGE;
import static ooga.view.center.agents.MovableView.IMAGE_PATH;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.view.popups.ErrorPopups;
import ooga.view.startupView.GameStartupPanel;
import org.json.JSONObject;

public class LoginView {

  public static final int LOGIN_WIDTH = 300;
  public static final int LOGIN_HEIGHT = 150;
  public static final int BUTTON_WIDTH = 100;
  public static final int PADDING = 18;
  public static final int SPACING = 14;
  public static final int BUTTON_SPACING = 8;
  public static final int SMALL_TEXT = 12;
  public static final String SIGN_IN_KEY = "SignIn";
  public static final String SIGN_UP_KEY = "SignUp";
  public static final String SIGN_IN_ID = "signInButton";
  public static final String SIGN_UP_ID = "signUpButton";
  public static final String CHEAT_KEY_ID = "cheatKeyButton";
  public static final String DEFAULT_VIEW_MODE = "Dark";

  private ResourceBundle myResources;
  private Stage myStage;
  private Controller myController;
  private User myUser;
  private ErrorPopups myError;
  private String cheatKey;

  /**
   * Class that creates the login screen for the game.  Options include logging into an
   * existing user profile, creating a new user profile, and opening a text dialogue for
   * cheat codes.
   *
   * @author Dane Erickson, Kat Cottrell
   */

  public LoginView (Stage stage, Controller controller) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    myStage = stage;
    myStage.setTitle("PACMAN LOGIN");
    Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
    myStage.getIcons().add(favicon);
    myController = controller;
    myStage.setScene(createLoginScene());
    myStage.show();
  }

  private Scene createLoginScene() {
    VBox root = new VBox();
    root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(PADDING));
    root.setSpacing(SPACING);
    ImageView welcome = new ImageView(new Image(
            new File("data/images/welcome.png").toURI().toString()));
    welcome.setPreserveRatio(true);
    welcome.setFitWidth(LOGIN_WIDTH - (2 * PADDING));
    ImageView signInButton = makeButton("signIn", e -> signInAction(), SIGN_IN_ID);
    ImageView signUpButton = makeButton("signUp", e -> signUpAction(), SIGN_UP_ID);
    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(BUTTON_SPACING);
    buttonBox.getChildren().addAll(signInButton, signUpButton);
    Text cheatKeyButton = makeCheatButton();
    root.getChildren().addAll(welcome, buttonBox, cheatKeyButton);
    return new Scene(root, LOGIN_WIDTH, LOGIN_HEIGHT);
  }

  private Text makeCheatButton() {
    Text cheatButton = new Text(myResources.getString("ClickToCheat"));
    cheatButton.setFont(Font.font("Verdana", FontWeight.LIGHT, FontPosture.ITALIC, SMALL_TEXT));
    cheatButton.setFill(Color.LIGHTGRAY);
    cheatButton.setOnMouseReleased(e -> cheatKeyDialog());
    return cheatButton;
  }

  private void cheatKeyDialog() {
    TextInputDialog cheatKeyInput = new TextInputDialog();
    cheatKeyInput.setTitle(myResources.getString("CheatKeyInputTitle"));
    cheatKeyInput.setHeaderText(myResources.getString("CheatKeyInputHeader"));
    cheatKeyInput.showAndWait();
    cheatKey = cheatKeyInput.getEditor().getText();
    new CheatKeyProcessor(myStage, myController, cheatKey);
  }

  private void signUpAction() {
    String username = makeTextInputDialog("Username", "Please enter your username");
    String password = makeTextInputDialog("Password", "Please enter your password");
    Alert selectImage = new Alert(AlertType.INFORMATION);
    selectImage.setTitle("Select Image");
    selectImage.setHeaderText("Please select a profile picture");
    selectImage.showAndWait();
    File image = fileExplorer();
    try {
      myUser = myController.createUser(username, password, image);
      new GameStartupPanel(myStage, myUser, myController);
    } catch (IOException | IllegalArgumentException e) {
      myError = new ErrorPopups(e, LANGUAGE, "SignUpError");
    }
  }

  private void signInAction() {
    String username = makeTextInputDialog("Username", "Please enter your username");
    String password = makeTextInputDialog("Password", "Please enter your password");
    try {
      myUser = myController.login(username, password);
      new GameStartupPanel(myStage, myUser, myController);
    } catch (IOException | IllegalArgumentException e) {
      myError = new ErrorPopups(e, LANGUAGE, "SignInError");
    }
  }

  private File fileExplorer() {
    FileChooser myFileChooser = new FileChooser();
    myFileChooser.setInitialDirectory(new File(IMAGE_PATH));
    return myFileChooser.showOpenDialog(myStage);
  }

  private String makeTextInputDialog(String title, String header) {
    TextInputDialog textInput = new TextInputDialog();
    textInput.setTitle(title);
    textInput.setHeaderText(header);
    textInput.showAndWait();
    return textInput.getEditor().getText();
  }

  private ImageView makeButton(String imageName, EventHandler handler, String id) {
    ImageView buttonImg = new ImageView(new Image(
            new File("data/images/" + imageName + ".png").toURI().toString()));
    buttonImg.setPreserveRatio(true);
    buttonImg.setFitWidth(BUTTON_WIDTH);
    buttonImg.setOnMouseReleased(handler);
    buttonImg.setId(id);
    return buttonImg;
  }

  // Used for testing
  protected User getUser() { return myUser; }

  // Used for testing
  protected User makeUserNoImage(String username, String password, String imagePath)
      throws IOException, InterruptedException {
    return myController.createUser(username, password, new File(imagePath));
  }

  // Used for testing
  protected ErrorPopups getError() { return myError; }

}
