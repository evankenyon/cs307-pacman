package ooga.view.topView;

import static ooga.view.bottomView.BottomView.ICON_SIZE;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.function.Consumer;
import javafx.scene.layout.HBox;
import ooga.model.VanillaGame;


public class TopView {

    public static final String HEART_PATH = "data/images/heart.png";

    private GridPane topGrid;
    private Label scoreDisplay;
    private Node lifeDisplay;
    private VanillaGame myGame;
    private Consumer<Integer> scoreConsumer = i -> updateScoreDisplay(i);
    private ResourceBundle myResources;


    public TopView (VanillaGame game) {
        myResources = ResourceBundle.getBundle("ooga.view.resources.English");
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
        Button loadButton = makeButton(myResources.getString("LoadGame"), e -> loadGame());
        Button saveButton = makeButton(myResources.getString("SaveGame"), e -> saveGame());
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
        lifeDisplay = makeLifeDisplay();
        scoreDisplay = new Label(myResources.getString("Score"));
        statsGP.add(lifeDisplay, 1,2);
        statsGP.add(scoreDisplay, 1,1);
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

    private Node makeLifeDisplay() {
        HBox lives = new HBox();
        String lifeDisplayText = myResources.getString("Lives");
        Label lifeDisplay = new Label(lifeDisplayText);
        ImageView heart1 = makeIcon(HEART_PATH);
        ImageView heart2 = makeIcon(HEART_PATH);
        ImageView heart3 = makeIcon(HEART_PATH);
        lives.getChildren().addAll(lifeDisplay, heart1, heart2, heart3);
        return lives;
    }

    private ImageView makeIcon(String path) {
        ImageView image = new ImageView(new Image(new File(path).toURI().toString()));
        image.setFitHeight(ICON_SIZE);
        image.setFitWidth(ICON_SIZE);
        return image;
    }

    private void updateScoreDisplay(int i) {
        String newText = String.format("%s%s", myResources.getString("Score"),String.valueOf(i));
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
