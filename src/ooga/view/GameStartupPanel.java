package ooga.view;

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
import ooga.view.mainView.MainView;

import java.io.File;

import static java.util.Objects.isNull;

public class GameStartupPanel {

    Stage stage;
    ComboBox<String> selectGameType;
    ComboBox<String> selectLanguage;
    ComboBox<String> selectViewMode;

    public GameStartupPanel(Stage stage) {
        this.stage = stage;
        stage.setScene(createStartupScene());
        stage.setTitle("Game Startup");
        stage.show();
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
        Button fileUploadButton = new Button();
        fileUploadButton.setId("fileUploadButton");
        fileUploadButton.setText("Upload game file");
        fileUploadButton.setOnAction(e -> uploadFile());
        Label selectGameFileLabel = makeLabel("Select a game file:");
        root.add(selectGameFileLabel, 1, 7);
        root.add(fileUploadButton, 1, 8);
    }

    private void uploadFile() {
        File gameFile = fileExplorer();
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
                Controller application = new Controller(selectedLanguage, stage);
                MainView newMainView = new MainView();
                selectGameType.setValue(null);
                selectLanguage.setValue(null);
                selectViewMode.setValue(null);
            } else {
                //notEnoughInfo();
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