package ooga.view.popups;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class GameEndPopups {

    public void GameEndPopups() {}

    public void winLosePopup(String winLose, Integer score, Integer playTime) {
        Alert winPopup = new Alert(Alert.AlertType.NONE);
        // TODO: Wire all text to resources files
        winPopup.setTitle("YOU " + winLose + "!!!");
        winPopup.setContentText(
                "All time high score: " + "\n" +  // TODO: Wire high score here
                "Your score: " + score + "\n" +
                "Play time: " + playTime + "\n" );
        winPopup.showAndWait();
    }

    public void beatHiScore(Integer score) {
        TextInputDialog beatHiScorePopup = new TextInputDialog();
        // TODO: Wire all text to resources files
        beatHiScorePopup.setTitle("YOU BEAT THE HIGH SCORE!!!");
        beatHiScorePopup.setHeaderText(
                "Old high score: " + "\n" +  // TODO: Wire high score here
                "Old high scorer: " + "\n" +  // TODO: Wire high scorer here
                "New high score: " + score + "\n");
        beatHiScorePopup.setContentText("Enter your name:");
    }
}
