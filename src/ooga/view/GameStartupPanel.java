package ooga.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static java.util.Objects.isNull;

public class GameStartupPanel {

    Stage stage;
    ComboBox<String> selectGameType;
    ComboBox<String> selectLanguage;
    ComboBox<String> selectViewMode;

    public GameStartupPanel(Stage stage) {
        createStartupPanelView(stage);
        stage.show();
    }

    public void createStartupPanelView(Stage stage) {
        this.stage = stage;
        stage.setTitle("Game Startup");
        GridPane root = new GridPane();
        addStartupOptions(root);
        addStartButton(root);
        Scene myScene = new Scene(root);
    }

    private void addStartupOptions(GridPane root) {
        Label selectGameLabel = makeLabel("Select game type:");
        root.add(selectGameLabel, 1, 1);

        String[] gameTypes = {"Vanilla Pacman", "Super Pac Man", "Play as a ghost"};
        ComboBox<String> selectGameType = makeDropDown("game type", gameTypes);
        root.add(selectGameType, 1, 2);

        Label selectLanguageLabel = makeLabel("Select language:");
        root.add(selectLanguageLabel, 1, 3);

        String[] languages = {"English", "Lang1", "Lang2", "Lang3", "Lang4"};
        ComboBox<String> selectLanguage = makeDropDown("language", languages);
        root.add(selectLanguage, 1, 4);

        Label selectModeLabel = makeLabel("Select viewing mode:");
        root.add(selectModeLabel, 1, 5);

        String[] viewModes = {"Light", "Dark", "Duke"};
        ComboBox<String> selectViewMode = makeDropDown("viewing mode", viewModes);
        root.add(selectViewMode, 1, 6);
    }

    private void addStartButton(GridPane root){
        Button startButton = new Button();
        startButton.setDefaultButton(true);
        startButton.setText("PLAY");
        startButton.setOnAction(e -> {
            String selectedGameType = selectGameType.getValue();
            String selectedLanguage = selectLanguage.getValue();
            String selectedViewMode = selectViewMode.getValue();
            if (!isNull(selectedGameType) && !isNull(selectedLanguage) && !isNull(selectedViewMode)) {
                //newGame(selectedGameType, selectedLanguage, selectedViewMode);
                selectGameType.setValue(null);
                selectLanguage.setValue(null);
                selectViewMode.setValue(null);
            } else {
                //notEnoughInfo();
            }
        });
        root.add(startButton, 1, 7);

    }

    private ComboBox makeDropDown(String category, String[] options) {
        ComboBox<String> newComboBox = new ComboBox<>();
        newComboBox.setPromptText("Select " + category);
        for (String option : options) {
            newComboBox.getItems().add(option);
        }
        newComboBox.setMinWidth(150);
        return newComboBox;
    }

    private Label makeLabel(String labelText) {
        Label myLabel = new Label(labelText);
        myLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        return myLabel;
    }


}