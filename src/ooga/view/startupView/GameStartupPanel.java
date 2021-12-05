package ooga.view.startupView;

import static java.util.Objects.isNull;
import static ooga.view.center.agents.MovableView.IMAGE_PATH;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import ooga.view.instructions.InstructionsView;
import ooga.view.popups.ErrorPopups;

public class GameStartupPanel {

  public static final String NO_FILE_TEXT = "No file selected";
  private Stage stage;
  private ComboBox<String> selectGameType;
  private ComboBox<String> selectLanguage;
  private ComboBox<String> selectViewMode;
  private ComboBox<String> selectFile;
  private Button fileUploadButton;
  private File gameFile;
  private String selectedGameType;
  private String selectedLanguage;
  private String selectedViewMode;
  private ResourceBundle myResources;
  private Text displayFileName;
  private User myUser;

  private static final int SCREEN_WIDTH = 400;
  private static final int SCREEN_HEIGHT = 425;
  public static final Paint BACKGROUND = Color.BLACK;
  public static final String STARTUP_PACKAGE = "ooga.view.startupView.";
  public static final String DEFAULT_STYLESHEET = String.format("/%sGameStartupPanel.css",
      STARTUP_PACKAGE.replace(".", "/"));
  public static final String RESOURCES_PATH_WITH_LANGUAGE = "ooga.view.resources.English";
  public static final String RESOURCES_PATH = "ooga.view.resources.";
  public static final String DEFAULT_LANGUAGE = "English";
  public static final String GAME_TYPE_KEYS[] = {"VanillaPacman", "SuperPacman", "MrsPacman",
      "GhostPacman"};
  public static final String LANGUAGE_KEYS[] = {"English", "French", "German", "Italian", "Spanish"};
  public static final String VIEW_MODE_KEYS[] = {"Dark", "Duke", "Light"};
  public static final String LOAD_FILE_KEYS[] = {"SelectLocally","SelectFromDatabase"};

//  @Deprecated
//  public GameStartupPanel(Stage stage) {
//    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
//    this.stage = stage;
//    this.stage.setScene(createStartupScene());
//    this.stage.setTitle("PACMAN STARTUP");
//    Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
//    this.stage.getIcons().add(favicon);
//    this.stage.show();
//  }

  public GameStartupPanel(Stage stage, User user) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    this.stage = stage;
    myUser = user;
    this.stage.setScene(createStartupScene());
    this.stage.setTitle("PACMAN STARTUP");
    Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
    this.stage.getIcons().add(favicon);
    this.stage.show();
  }

  private Scene createStartupScene() {
    GridPane root = new GridPane();
    root.getStyleClass().add("grid-pane");
    makeBackground(root);
    addPacMan307Img(root);
    addStartupOptions(root);
    addStartButton(root);
    Scene myScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    myScene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    return myScene;
  }

  private void addPacMan307Img(GridPane root) {
    ImageView pm307 = new ImageView(
        new Image(new File("data/images/pac_man_307.png").toURI().toString()));
    setImgWidth(pm307, SCREEN_WIDTH);
    root.add(pm307, 1, 1);
  }

  private void addStartupOptions(GridPane root) {
    VBox selectCol1L = new VBox();
    VBox selectCol1R = new VBox();
    VBox selectCol2L = new VBox();
    VBox selectCol2R = new VBox();
    HBox selectCluster1 = new HBox();
    HBox selectCluster2 = new HBox();
    selectGameType = makeSelectorBox(selectCol1L, "GameType", GAME_TYPE_KEYS);
    selectLanguage = makeSelectorBox(selectCol1R, "Language", LANGUAGE_KEYS);
    addToCluster(root, selectCol1L, selectCol1R, selectCluster1, 2);
    selectViewMode = makeSelectorBox(selectCol2L, "ViewingMode", VIEW_MODE_KEYS);
    selectFile = makeSelectorBox(selectCol2R, "GameFile", LOAD_FILE_KEYS);
    selectFile.setOnAction(e -> selectFileAction());
    displayFileName = makeText(Color.LIGHTGRAY, NO_FILE_TEXT, selectCol2R);
    addToCluster(root, selectCol2L, selectCol2R, selectCluster2, 3);
  }

  private void selectFileAction() {
    String location = selectFile.getValue();
    if (location.equals(myResources.getString(LOAD_FILE_KEYS[0]))) {
      uploadFile();
    }
    else if (location.equals(myResources.getString(LOAD_FILE_KEYS[1]))) {
      makeChoiceDialog();
    }
  }

  private void makeChoiceDialog() {
    String choices[] = {"hi","hey","hello"};
    ChoiceDialog databaseChoices = new ChoiceDialog<>(choices[0],choices);
    databaseChoices.setHeaderText("Select File from Database");
    databaseChoices.setTitle("Database Files");
    String fileName = databaseChoices.getSelectedItem().toString();
    databaseChoices.showAndWait();
  }

  private Text makeText(Paint color, String message, VBox vBox) {
    Text text = new Text();
    text.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
    text.setFill(color);
    text.setText(message);
    vBox.getChildren().add(text);
    return text;
  }

  private ComboBox<String> makeSelectorBox(VBox vBox, String category, String keys[]) {
    ImageView imageLabel = new ImageView(
        new Image(new File(String.format("%sselect%s.png", IMAGE_PATH, category)).toURI().toString()));
    setImgWidth(imageLabel, SCREEN_WIDTH / 2);
    List<String> choices = new ArrayList<>();
    for (String s : keys) {
      choices.add(myResources.getString(s));
    }
    ComboBox<String> comboBox = makeDropDown(category, choices.toArray(new String[0]));
    vBox.getChildren().addAll(imageLabel, comboBox);
    vBox.setAlignment(Pos.TOP_CENTER);
    return comboBox;
  }

  private void addToCluster(GridPane root, VBox vBoxChild1, VBox vBoxChild2, HBox hBoxParent,
      int row) {
    hBoxParent.getChildren().addAll(vBoxChild1, vBoxChild2);
    root.add(hBoxParent, 1, row);
  }

  private Button makeFileUploadButton() {
    fileUploadButton = new Button();
    fileUploadButton.setMinWidth(150);
    fileUploadButton.setId("fileUploadButton");
    fileUploadButton.setText(myResources.getString("UploadFile"));
    fileUploadButton.setOnAction(e -> uploadFile());
    return fileUploadButton;
  }

  private void uploadFile() {
    gameFile = fileExplorer();
    if (gameFile != null) {
      displayFileName.setText(gameFile.getName());
    }
  }

  private File fileExplorer() {
    // Credit to Carl Fisher for writing this code in Cell Society team 6
    FileChooser myFileChooser = new FileChooser();
    myFileChooser.setInitialDirectory(new File("data/basic_examples"));
    return myFileChooser.showOpenDialog(stage);
  }

  private void addStartButton(GridPane root) {
    ImageView startButton = new ImageView(
        new Image(new File("data/images/playButton.png").toURI().toString()));
    setImgWidth(startButton, SCREEN_WIDTH / 4);
    startButton.setId("startButton");
    startButton.setOnMouseReleased(e -> startButtonAction());
    HBox playBox = new HBox();
    playBox.getChildren().add(startButton);
    playBox.setAlignment(Pos.CENTER);
    root.add(playBox, 1, 4);
  }

  private void startButtonAction() {
    selectedGameType = selectGameType.getValue();
    selectedLanguage = selectLanguage.getValue();
    selectedViewMode = selectViewMode.getValue();
    if (!isNull(selectedGameType) && !isNull(selectedLanguage) && !isNull(selectedViewMode)) {
      runFile();
      openInstructions(selectedLanguage, selectedGameType, selectedViewMode);
      selectGameType.setValue(null);
      selectLanguage.setValue(null);
      selectViewMode.setValue(null);
    } else {
      if (selectedLanguage == null) {
        selectedLanguage = DEFAULT_LANGUAGE;
      }
      new ErrorPopups(selectedLanguage, "RequiredFields");
    }
  }

  private void openInstructions(String selectedLanguage, String selectedGameType, String selectedViewMode) {
    Stage instructionsStage = new Stage();
    InstructionsView instructionsView = new InstructionsView(instructionsStage, selectedLanguage, selectedGameType, selectedViewMode);
  }

  private void runFile() {
    Stage gameStage = new Stage();
    Controller application = new Controller(selectedLanguage, gameStage);
    try {
      UserPreferences userPreferences = application.uploadFile(gameFile);
      MainView mainView = new MainView(application, application.getVanillaGame(), gameStage,
          userPreferences);
    } catch (Exception ex) {
      if (gameFile == null) {
        new ErrorPopups(selectedLanguage, "NoFile");
      } else {
        new ErrorPopups(selectedLanguage, "InvalidFile");
      }
    }
  }


  private ComboBox makeDropDown(String category, String[] options) {
    ComboBox<String> newComboBox = new ComboBox<>();
    newComboBox.setPromptText(myResources.getString("Select") + " " + category);
    for (String option : options) {
      newComboBox.getItems().add(option);
    }
    newComboBox.setMinWidth(150);
    newComboBox.setId(category);
    return newComboBox;
  }

  private void makeBackground(GridPane root) {
    BackgroundFill background_fill = new BackgroundFill(BACKGROUND,
        CornerRadii.EMPTY, Insets.EMPTY);
    Background background = new Background(background_fill);
    root.setBackground(background);
  }

  private void setImgWidth(ImageView img, int width) {
    img.setPreserveRatio(true);
    img.setFitWidth(width);
  }

}
