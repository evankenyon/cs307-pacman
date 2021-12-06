package ooga.view.userProfileView;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;

public class UserInformationView {
  private static final int SCREEN_WIDTH = 400;
  private static final int SCREEN_HEIGHT = 425;
  public static final String STARTUP_PACKAGE = "ooga.view.startupView.";
  public static final String DEFAULT_STYLESHEET = String.format("/%sGameStartupPanel.css",
      STARTUP_PACKAGE.replace(".", "/"));

  private Stage stage;
  private Controller controller;

  public UserInformationView(Controller controller, User user, Stage stage) {
    this.stage = stage;
    this.stage.setScene(createStartupScene(user));
    this.stage.setTitle("PACMAN STARTUP");
    Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
    this.stage.getIcons().add(favicon);
    this.stage.show();
  }

  public Scene createStartupScene(User user) {
    GridPane root = new GridPane();
    root.getStyleClass().add("grid-pane");
    addProfileImage(root, user);
    addTextInfo(root, "Username", user.username(), 1, 2);
    Button editUsernameButton = makeButton("Edit Username", e -> editForm());
    addTextInfo(root, "High Score", String.valueOf(user.highScore()), 1, 3);
    addTextInfo(root, "Number of wins", String.valueOf(user.wins()), 1, 4);
    addTextInfo(root, "Number of losses", String.valueOf(user.losses()), 1, 5);
    Scene myScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    myScene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    return myScene;
  }

  private void addProfileImage(GridPane root, User user) {
    ImageView profileImage = new ImageView(new Image(new File(user.imagePath()).toURI().toString()));
    setImgWidth(profileImage, 100);
    root.add(profileImage, 1, 1);
  }

  private void addTextInfo(GridPane root, String key, String value, int columnIndex, int rowIndex) {
    Text textInfo = new Text(String.format("%s: %s", key, value));
    root.add(textInfo, columnIndex, rowIndex);
  }

  private void setImgWidth(ImageView img, int width) {
    img.setPreserveRatio(true);
    img.setFitWidth(width);
  }

  private void editForm() {
    try {
      controller.updateUsername(makeTextInputDialog("Username", "Please enter a new username"));
    } catch (IOException e) {
      // TODO: handle
    }

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
