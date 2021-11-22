package ooga.view.topView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.function.Consumer;
import ooga.model.VanillaGame;


public class TopView {
    private String PAUSE_BUTTON = "https://theologygaming.com/wp-content/uploads/2014/08/Pause.png";
    private String PLAY_BUTTON = "https://cdn-icons-png.flaticon.com/512/109/109197.png";
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
        topGrid = new GridPane();
        topGrid.add(makeLoadSaveGP(), 1, 1);
//        topGrid.add(makePausePlayGP(), 1, 2);
        topGrid.add(makeStatsGP(), 1,3);
        return topGrid;
    }

    private GridPane makeLoadSaveGP() {
        GridPane loadSaveGP = new GridPane();
        Button loadButton = makeButton("Load game", e -> loadGame());
        Button saveButton = makeButton("Save game", e -> saveGame());
        topGrid.add(loadButton, 1, 1);
        topGrid.add(saveButton, 2, 1);
        return loadSaveGP;
    }

//    private GridPane makePausePlayGP() {
//        GridPane pausePlayGP = new GridPane();
//        ImageView playButton = makeImgButton(PLAY_BUTTON, e -> playGame());
//        pausePlayGP.add(playButton, 1, 1);
//        ImageView pauseButton = makeImgButton(PAUSE_BUTTON, e -> pauseGame());
//        pausePlayGP.add(pauseButton, 2, 1);
//        return pausePlayGP;
//    }

    private GridPane makeStatsGP() {
        GridPane statsGP = new GridPane();
        Label lifeDisplay = updateLifeDisplay();
        scoreDisplay = new Label("SCORE: ");
        statsGP.add(lifeDisplay, 1,1);
        statsGP.add(scoreDisplay, 2,1);
        return statsGP;
    }

    private void loadGame() {
        // TODO: Implement
    }

    private void saveGame() {
        // TODO: Implement
    }

    private void playGame() {
        // TODO: Implement
    }

    private void pauseGame() {
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

//    private ImageView makeImgButton(String imgFile, EventHandler handler) {
//        Image myImage = new Image(imgFile);
//        ImageView imgButton = new ImageView(myImage);
//        imgButton.setFitWidth(50);
//        imgButton.setFitHeight(50);
//        imgButton.setOnMouseReleased(handler);
//        return imgButton;
//    }

    public GridPane getTopViewGP() {
        return this.topGrid;
    }
}
