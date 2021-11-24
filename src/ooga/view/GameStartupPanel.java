package ooga.view;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import ooga.view.popups.ErrorPopups;

public class GameStartupPanel {

  public static final String RESOURCES_PATH_WITH_LANGUAGE = "ooga.view.resources.English";
  public static final String RESOURCES_PATH = "ooga.view.resources.";
  public static final String DEFAULT_LANGUAGE = "English";

  private Stage stage;
  private ComboBox<String> selectGameType;
  private ComboBox<String> selectLanguage;
  private ComboBox<String> selectViewMode;
  private Button fileUploadButton;
  private File gameFile;
  private ResourceBundle myResources;
  private String selectedGameType;
  private String selectedLanguage;
  private String selectedViewMode;

  public GameStartupPanel(Stage stage) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    this.stage = stage;
    this.stage.setScene(createStartupScene());
    this.stage.setTitle("Game Startup");
    this.stage.show();
  }

  public Scene createStartupScene() {
    GridPane root = new GridPane();
    addStartupOptions(root);
    addFileUploadButton(root);
    addStartButton(root);
    Scene myScene = new Scene(root, 250, 250);
    return myScene;
  }

  private void addStartupOptions(GridPane root) {
    Label selectGameLabel = makeLabel(myResources.getString("SelectGameType"));
    root.add(selectGameLabel, 1, 1);

    String[] gameTypes = {myResources.getString("VanillaPacman"),
        myResources.getString("SuperPacman"), myResources.getString("MrsPacman"),
        myResources.getString("GhostPacman")};
    selectGameType = makeDropDown("game type", gameTypes);
    root.add(selectGameType, 1, 2);

    Label selectLanguageLabel = makeLabel(myResources.getString("SelectLanguage"));
    root.add(selectLanguageLabel, 1, 3);

    String[] languages = {myResources.getString("English"), myResources.getString("Spanish"),
        myResources.getString("Lang3"), myResources.getString("Lang4"),
        myResources.getString("Lang5")};
    selectLanguage = makeDropDown("language", languages);
    root.add(selectLanguage, 1, 4);

    Label selectModeLabel = makeLabel(myResources.getString("SelectViewMode"));
    root.add(selectModeLabel, 1, 5);

    String[] viewModes = {myResources.getString("Light"), myResources.getString("Dark"),
        myResources.getString("Duke")};
    selectViewMode = makeDropDown("viewing mode", viewModes);
    root.add(selectViewMode, 1, 6);
  }

  private void addFileUploadButton(GridPane root) {
    fileUploadButton = new Button();
    fileUploadButton.setId("fileUploadButton");
    fileUploadButton.setText(myResources.getString("UploadFile"));
    fileUploadButton.setOnAction(e -> uploadFile());
    Label selectGameFileLabel = makeLabel(myResources.getString("SelectGameFile"));
    root.add(selectGameFileLabel, 1, 7);
    root.add(fileUploadButton, 1, 8);
  }

  private void uploadFile() {
    gameFile = fileExplorer();
  }

  private File fileExplorer() {
    // Credit to Carl Fisher for writing this code in Cell Society team 6
    FileChooser myFileChooser = new FileChooser();
    return myFileChooser.showOpenDialog(stage);
  }

  private void addStartButton(GridPane root) {
    Button startButton = new Button();
    startButton.setId("startButton");
    startButton.setDefaultButton(true);
    startButton.setText(myResources.getString("Play"));
    startButton.setOnAction(e -> buttonAction());
    root.add(startButton, 1, 9);
  }

  private void buttonAction() {
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
      new ErrorPopups(selectedLanguage).requiredFieldsPopup();
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
        new ErrorPopups(selectedLanguage).noFilePopup();
      } else {
        new ErrorPopups(selectedLanguage).fileErrorPopup();
      }
    }
  }

  private ComboBox makeDropDown(String category, String[] options) {
    ComboBox<String> newComboBox = new ComboBox<>();
    newComboBox.setPromptText(myResources.getString("Select") + category);
    for (String option : options) {
      newComboBox.getItems().add(option);
    }
    newComboBox.setMinWidth(150);
    newComboBox.setId(category);
    return newComboBox;
  }

  private Label makeLabel(String labelText) {
    Label myLabel = new Label(labelText);
    myLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
    return myLabel;
  }


}