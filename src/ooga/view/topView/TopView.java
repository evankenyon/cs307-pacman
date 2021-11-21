package ooga.view.topView;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ooga.model.VanillaGame;

public class TopView {
    private GridPane topGrid;
    private Label scoreDisplay;
    private VanillaGame myGame;
    private Consumer<Integer> scoreConsumer = i -> updateScoreDisplay(i);

    public TopView (VanillaGame game) {
        myGame = game;
        game.getBoard().addScoreConsumer(scoreConsumer);
        initiateTopView();
    }

    private GridPane initiateTopView() {
        Button loadButton = makeButton("Load game", e -> loadGame());
        Button saveButton = makeButton("Save game", e -> saveGame());
        Label lifeDisplay = updateLifeDisplay();
        scoreDisplay = new Label("SCORE: ");

        topGrid = new GridPane();
        topGrid.add(loadButton, 1, 1);
        topGrid.add(saveButton, 2, 1);
        topGrid.add(lifeDisplay, 1,2);
        topGrid.add(scoreDisplay, 1,3);

        return topGrid;
    }

    private void loadGame() {
        // TODO: Implement
    }

    private void saveGame() {
        // TODO: Implement
    }

    private Label updateLifeDisplay() {
        String lifeDisplayText = "LIVES LEFT: " + String.valueOf(3); // TODO: GET LIFECOUNT
        Label lifeDisplay = new Label(lifeDisplayText);
        return lifeDisplay;
    }

    private void updateScoreDisplay(int i) {
        String newText = String.format("SCORE: %s", String.valueOf(i));
        scoreDisplay.setText(newText);
    }

    private Button makeButton(String name, EventHandler<ActionEvent> handler) {
        Button myButton = new Button(name);
        myButton.setOnAction(handler);
        return myButton;
    }

    public GridPane getTopViewGP() {
        return this.topGrid;
    }
}
