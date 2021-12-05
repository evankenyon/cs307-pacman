package ooga.view.loginView;

import static ooga.view.center.agents.MovableView.IMAGE_PATH;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.view.startupView.GameStartupPanel;

public class LoginView {

  public static final int LOGIN_WIDTH = 400;
  public static final int LOGIN_HEIGHT = 400;
  public static final String SIGN_IN_KEY = "SignIn";
  public static final String SIGN_UP_KEY = "SignUp";

  private ResourceBundle myResources;
  private Stage myStage;
  private Controller myController;
  private User myUser;

  public LoginView (Stage stage, Controller controller) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    myStage = stage;
    myController = controller;
    myStage.setScene(createLoginScene());
    myStage.show();
  }

  private Scene createLoginScene() {
    HBox root = new HBox();
    Button signInButton = makeButton(myResources.getString(SIGN_IN_KEY), e -> signInAction());
    Button signUpButton = makeButton(myResources.getString(SIGN_UP_KEY), e -> signUpAction());
    root.getChildren().addAll(signInButton, signUpButton);
    return new Scene(root, LOGIN_WIDTH, LOGIN_HEIGHT);
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
    } catch (IOException e) {
      //TODO: make signup exception popup
      e.printStackTrace();
    }
    new GameStartupPanel(myStage, myUser);
  }

  private void signInAction() {
    String username = makeTextInputDialog("Username", "Please enter your username");
    String password = makeTextInputDialog("Password", "Please enter your password");
    try {
      myUser = myController.login(username, password);
    } catch (IOException e) {
      //TODO: make sign in exception popup
      e.printStackTrace();
    }
    new GameStartupPanel(myStage, myUser);
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

  private Button makeButton(String label, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setOnAction(handler);
    button.setText(label);
    return button;
  }

}
