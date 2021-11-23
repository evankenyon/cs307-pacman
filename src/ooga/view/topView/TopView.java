package ooga.view.topView;

import static ooga.view.bottomView.BottomView.ICON_SIZE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;
import static ooga.view.mainView.MainView.SCENE_HEIGHT;
import static ooga.view.mainView.MainView.SCENE_WIDTH;

import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.util.function.Consumer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ooga.model.VanillaGame;


public class TopView {

    public static final String HEART_PATH = "data/images/heart.png";
    public static final String TOPVIEW_PACKAGE = "ooga.view.topView.";
    public static final String STYLESHEET = String.format("/%sTopView.css",
        TOPVIEW_PACKAGE.replace(".", "/"));
    public static final double TOP_SPACING = (SCENE_HEIGHT - BOARD_HEIGHT) / 3;

    private BorderPane topGrid;
    private Label scoreDisplay;
    private HBox lifeDisplay;
    private VanillaGame myGame;
    private Consumer<Integer> scoreConsumer = i -> updateScoreDisplay(i);
    private Consumer<Boolean> livesConsumer = result -> updateLivesDisplay(result);
    private ResourceBundle myResources;


    public TopView (VanillaGame game) {
        myResources = ResourceBundle.getBundle("ooga.view.resources.English");
        myGame = game;
        game.getBoard().addScoreConsumer(scoreConsumer);
        initiateTopView();
        topGrid.getStyleClass().add("root");
        topGrid.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    }

    private Node initiateTopView() {
        topGrid = new BorderPane();
        topGrid.setMaxWidth(BOARD_WIDTH);
//        topGrid.setPrefWidth(SCENE_WIDTH);
//        topGrid.setAlignment(Pos.BOTTOM_CENTER);
        topGrid.setTop(makeLoadSaveGP());
        makeLifeDisplay();
        topGrid.setLeft(lifeDisplay);
        makeScoreDisplay();
        topGrid.setRight(scoreDisplay);
        return topGrid;
    }

    private Node makeLoadSaveGP() {
        HBox loadSave = new HBox();
        loadSave.setAlignment(Pos.TOP_CENTER);
        loadSave.setMinHeight(TOP_SPACING);
        Button loadButton = makeButton(myResources.getString("LoadGame"), e -> loadGame());
        Button saveButton = makeButton(myResources.getString("SaveGame"), e -> saveGame());
        loadSave.getChildren().addAll(loadButton, saveButton);
        loadSave.getStyleClass().add("loadSave");
        loadSave.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
//        return new VBox(loadSave, new Rectangle(TOP_SPACING, TOP_SPACING));
        return loadSave;
    }

    private Node makeStatsGP() {
        HBox statsGP = new HBox();
//        statsGP.setAlignment(Pos.CENTER);
//        statsGP.setSpacing(TOP_SPACING);
        statsGP.getStyleClass().add("scoreLIfe");
        statsGP.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
        scoreDisplay = new Label(myResources.getString("Score"));
        makeLifeDisplay();
        statsGP.getChildren().addAll(scoreDisplay, lifeDisplay);
        return statsGP;
    }

    private void makeScoreDisplay() {
        scoreDisplay = new Label(myResources.getString("Score"));
    }

    private void loadGame() {
        // TODO: Implement
    }

    private void saveGame() {
        // TODO: Implement
    }

    private void makeLifeDisplay() {
        lifeDisplay = new HBox();
        ImageView heart1 = makeIcon(HEART_PATH);
        ImageView heart2 = makeIcon(HEART_PATH);
        ImageView heart3 = makeIcon(HEART_PATH);
        lifeDisplay.getChildren().addAll(heart1, heart2, heart3);
        lifeDisplay.getStyleClass().add("lives");
        lifeDisplay.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
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

    // TODO: implement lives consumer to change the hearts on the screen
    private void updateLivesDisplay(boolean result) {
        if (result) {
            lifeDisplay.getChildren().remove(lifeDisplay.getChildren().size()-1);
        }
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

    public Node getTopViewGP() {
        return this.topGrid;
    }
}
