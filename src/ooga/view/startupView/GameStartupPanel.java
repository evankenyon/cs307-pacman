package ooga.view.startupView;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import ooga.view.popups.ErrorPopups;

public class GameStartupPanel {

  private Stage stage;
  private ComboBox<String> selectGameType;
  private ComboBox<String> selectLanguage;
  private ComboBox<String> selectViewMode;
  private Button fileUploadButton;
  private File gameFile;
  private String selectedGameType;
  private String selectedLanguage;
  private String selectedViewMode;
  private ResourceBundle myResources;
  private Text displayFileName;

  private static final int SCREEN_WIDTH = 400;
  private static final int SCREEN_HEIGHT = 425;
  public static final Paint BACKGROUND = Color.BLACK;
  public static final String STARTUP_PACKAGE = "ooga.view.startupView.";
  public static final String DEFAULT_STYLESHEET = String.format("/%sGameStartupPanel.css",
          STARTUP_PACKAGE.replace(".", "/"));
  public static final String RESOURCES_PATH_WITH_LANGUAGE = "ooga.view.resources.English";
  public static final String RESOURCES_PATH = "ooga.view.resources.";
  public static final String DEFAULT_LANGUAGE = "English";

  public GameStartupPanel(Stage stage) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    this.stage = stage;
    this.stage.setScene(createStartupScene());
    this.stage.setTitle("PACMAN STARTUP");
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
    ImageView pm307 = new ImageView(new Image(new File("data/images/pac_man_307.png").toURI().toString()));
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

    ImageView selectGameLabel = new ImageView(new Image(new File("data/images/selectGameType.png").toURI().toString()));
    setImgWidth(selectGameLabel, SCREEN_WIDTH/2);
    String[] gameTypes = {myResources.getString("VanillaPacman"),
        myResources.getString("SuperPacman"), myResources.getString("MrsPacman"),
        myResources.getString("GhostPacman")};
    selectGameType = makeDropDown("game type", gameTypes);
    selectCol1L.getChildren().addAll(selectGameLabel, selectGameType);
    selectCol1L.setAlignment(Pos.CENTER);

    ImageView selectLanguageLabel = new ImageView(new Image(new File("data/images/selectLanguage.png").toURI().toString()));
    setImgWidth(selectLanguageLabel, SCREEN_WIDTH/2);
    String[] languages = {myResources.getString("English"), myResources.getString("Spanish"),
        myResources.getString("Lang3"), myResources.getString("Lang4"),
        myResources.getString("Lang5")};
    selectLanguage = makeDropDown("language", languages);
    selectCol1R.getChildren().addAll(selectLanguageLabel, selectLanguage);
    selectCol1R.setAlignment(Pos.CENTER);

    selectCluster1.getChildren().addAll(selectCol1L, selectCol1R);
    root.add(selectCluster1, 1, 2);

    ImageView selectModeLabel = new ImageView(new Image(new File("data/images/selectViewingMode.png").toURI().toString()));
    setImgWidth(selectModeLabel, SCREEN_WIDTH/2);
    String[] viewModes = {myResources.getString("Light"), myResources.getString("Dark"),
        myResources.getString("Duke")};
    selectViewMode = makeDropDown("viewing mode", viewModes);
    Text spacingFix = new Text();
    spacingFix.setText(".");
    spacingFix.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
    selectCol2L.getChildren().addAll(selectModeLabel, selectViewMode, spacingFix);
    selectCol2L.setAlignment(Pos.CENTER);

    ImageView selectGameFileLabel = new ImageView(new Image(new File("data/images/selectGameFile.png").toURI().toString()));
    setImgWidth(selectGameFileLabel, SCREEN_WIDTH/2);
    Button fileUploadButton = makeFileUploadButton();
    displayFileName = new Text();
    displayFileName.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
    displayFileName.setFill(Color.LIGHTGRAY);
    displayFileName.setText("No file selected");
    selectCol2R.getChildren().addAll(selectGameFileLabel, fileUploadButton, displayFileName);
    selectCol2R.setAlignment(Pos.CENTER);

    selectCluster2.getChildren().addAll(selectCol2L, selectCol2R);
    root.add(selectCluster2, 1, 3);
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
    displayFileName.setText(gameFile.getName());
  }

  private File fileExplorer() {
    // Credit to Carl Fisher for writing this code in Cell Society team 6
    FileChooser myFileChooser = new FileChooser();
    return myFileChooser.showOpenDialog(stage);
  }

  private void addStartButton(GridPane root) {
    ImageView startButton = new ImageView(new Image(new File("data/images/playButton.png").toURI().toString()));
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
      selectGameType.setValue(null);
      selectLanguage.setValue(null);
      selectViewMode.setValue(null);
    } else {
      if (selectedLanguage == null) {
        selectedLanguage = DEFAULT_LANGUAGE;
      }
      new ErrorPopups(selectedLanguage, "requiredFields");
    }
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
        new ErrorPopups(selectedLanguage, "noFile");
      } else {
        new ErrorPopups(selectedLanguage, "fileError");
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
