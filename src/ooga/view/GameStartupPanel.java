package ooga.view;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
import ooga.model.util.Position;
import ooga.view.mainView.MainView;

public class GameStartupPanel {

  private Stage stage;
  private ComboBox<String> selectGameType;
  private ComboBox<String> selectLanguage;
  private ComboBox<String> selectViewMode;
  private Button fileUploadButton;
  private File gameFile;

  public GameStartupPanel(Stage stage) {
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
    Label selectGameLabel = makeLabel("Select game type:");
    root.add(selectGameLabel, 1, 1);

    String[] gameTypes = {"Vanilla Pacman", "Super Pac Man", "Play as a ghost"};
    selectGameType = makeDropDown("game type", gameTypes);
    root.add(selectGameType, 1, 2);

    Label selectLanguageLabel = makeLabel("Select language:");
    root.add(selectLanguageLabel, 1, 3);

    String[] languages = {"English", "Lang1", "Lang2", "Lang3", "Lang4"};
    selectLanguage = makeDropDown("language", languages);
    root.add(selectLanguage, 1, 4);

    Label selectModeLabel = makeLabel("Select viewing mode:");
    root.add(selectModeLabel, 1, 5);

    String[] viewModes = {"Light", "Dark", "Duke"};
    selectViewMode = makeDropDown("viewing mode", viewModes);
    root.add(selectViewMode, 1, 6);
  }

  private void addFileUploadButton(GridPane root) {
    fileUploadButton = new Button();
    fileUploadButton.setId("fileUploadButton");
    fileUploadButton.setText("Upload game file");
    fileUploadButton.setOnAction(e -> uploadFile());
    Label selectGameFileLabel = makeLabel("Select a game file:");
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
    startButton.setText("PLAY");
    startButton.setOnAction(e -> {
      String selectedGameType = selectGameType.getValue();
      String selectedLanguage = selectLanguage.getValue();
      String selectedViewMode = selectViewMode.getValue();
      if (!isNull(selectedGameType) && !isNull(selectedLanguage) && !isNull(selectedViewMode)) {
        Stage gameStage = new Stage();
        Controller application = new Controller(selectedLanguage, gameStage);
        // TODO: Fix exception:
        try {
          UserPreferences userPreferences = application.uploadFile(gameFile);
          MainView mainView = new MainView(application, application.getVanillaGame(), gameStage,
              userPreferences);
        } catch (Exception ex) {
          // TODO: clean this up
//                    ex.printStackTrace();
          ex.printStackTrace();
        }
        //MainView newMainView = new MainView();
        selectGameType.setValue(null);
        selectLanguage.setValue(null);
        selectViewMode.setValue(null);
      } else {
        //notEnoughInfo();
        System.out.println("whoops");
      }
    });
    root.add(startButton, 1, 9);
  }

  private ComboBox makeDropDown(String category, String[] options) {
    ComboBox<String> newComboBox = new ComboBox<>();
    newComboBox.setPromptText("Select " + category);
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