package ooga.view.bottomView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BottomView {
    GridPane bottomGrid;

    public void BottomView () {
        initiateBottomView();
    }

    private GridPane initiateBottomView() {
        Button easyButton    = makeButton("Easy", e -> makeGameEasy());
        Button hardButton    = makeButton("Hard", e -> makeGameHard());
        Button statsButton   = makeButton("Stats", e -> showStats());
        Button newGameButton = makeButton("New Game", e -> resetGame());

        bottomGrid = new GridPane();
        bottomGrid.add(easyButton, 1, 1);
        bottomGrid.add(hardButton, 2, 1);
        bottomGrid.add(statsButton, 3, 1);
        bottomGrid.add(newGameButton, 4, 1);

        return bottomGrid;
    }

    private void makeGameEasy() {
        // TODO: Implement
    }

    private void makeGameHard() {
        // TODO: Implement
    }

    private void showStats() {
        // TODO: implement pauseGame function here
        Alert statsPopup = new Alert(Alert.AlertType.NONE);
        // TODO: Wire all text to resources files
        statsPopup.setTitle("GAME STATS");
        statsPopup.setContentText(
                "All time high score: " + "\n" +
                "Your lives: " + "\n" +
                "Other stats: " + "\n");
        statsPopup.showAndWait();
    }

    private void resetGame() {
        // TODO: implement resetGame function here
    }

    private Button makeButton(String name, EventHandler<ActionEvent> handler) {
        Button myButton = new Button(name);
        myButton.setOnAction(handler);
        return myButton;
    }

    public GridPane getBottomViewGP() {
        return this.bottomGrid;
    }
}
