package ooga.view.topView;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;
import static ooga.view.bottomView.BottomView.ICON_SIZE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;
import static ooga.view.mainView.MainView.SCENE_HEIGHT;
import static ooga.view.mainView.MainView.BG_COLOR;

import java.io.File;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.function.Consumer;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import ooga.model.VanillaGame;

public class TopView {

    public static final String TOPVIEW_PACKAGE = "ooga.view.topView.";
    public static final String STYLESHEET = String.format("/%sTopView.css",
        TOPVIEW_PACKAGE.replace(".", "/"));
    public static final double TOP_SPACING = (SCENE_HEIGHT - BOARD_HEIGHT) / 3;
    public static final String PM307 = "data/images/pac_man_307_header.png";
    public static final String SCORE = "data/images/pac_man_307_header.png";

    private BorderPane topGrid;
    private HBox scoreDisplay;
    private HBox lifeDisplay;
    private Label scoreNumber;
    private VanillaGame myGame;
    private Consumer<Integer> scoreConsumer = i -> updateScoreDisplay(i);
    private Consumer<Boolean> livesConsumer = result -> updateLivesDisplay(result);
    private ResourceBundle myResources;
    private VBox topFull;

    public TopView (VanillaGame game, String language) {
        myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
        myGame = game;
        game.getBoard().addScoreConsumer(scoreConsumer);
        initiateTopView();
        topGrid.getStyleClass().add("root");
        topGrid.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    }

    private VBox initiateTopView() {
        topGrid = new BorderPane();
        topGrid.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        topGrid.setMaxWidth(BOARD_WIDTH);
        makeLifeDisplay(3);
        topGrid.setLeft(lifeDisplay);
        makeScoreDisplay();
        topGrid.setRight(scoreDisplay);
        topFull = new VBox();
        ImageView pacMan307 = new ImageView(new Image(new File(PM307).toURI().toString()));
        pacMan307.setPreserveRatio(true);
        pacMan307.setFitWidth(BOARD_WIDTH);
        topFull.getChildren().addAll(pacMan307, topGrid);
        topFull.setAlignment(Pos.CENTER);
        return topFull;
    }

    private void makeScoreDisplay() {
        String scoreLabelPath = "data/images/scoreLabel-" + myResources.getString("score") + ".png";
        ImageView scoreLabelImg = new ImageView(new Image(new File(scoreLabelPath).toURI().toString()));
        scoreLabelImg.setPreserveRatio(true);
        scoreLabelImg.setFitHeight(ICON_SIZE);
        scoreNumber = new Label();
        scoreNumber.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, ICON_SIZE));
        scoreNumber.setTextFill(Color.LIGHTGRAY);
        scoreDisplay = new HBox();
        scoreDisplay.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        scoreDisplay.setAlignment(Pos.BOTTOM_CENTER);
        scoreDisplay.getChildren().addAll(scoreLabelImg, scoreNumber);
    }

    private void loadGame() {
//        FileChooser myFileChooser = new FileChooser();
//        Stage fileStage = new Stage();
//        File gameFile =  myFileChooser.showOpenDialog(fileStage);
//        UserPreferences userPreferences = application.uploadFile(gameFile);
//        MainView mainView = new MainView(application, application.getVanillaGame(), gameStage,
//                userPreferences);
    }

    private void makeLifeDisplay(int lifeCount) {
        lifeDisplay = new HBox();
        lifeDisplay.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView livesLabel = makeIcon("data/images/livesLabel-" + myResources.getString("lives") + ".png");
        ImageView heartDisplay = makeIcon("data/images/hearts-" + Integer.valueOf(lifeCount) + ".png");
        lifeDisplay.getChildren().addAll(livesLabel, heartDisplay);
        lifeDisplay.getStyleClass().add("lives");
        lifeDisplay.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    }

    private ImageView makeIcon(String path) {
        ImageView image = new ImageView(new Image(new File(path).toURI().toString()));
        image.setPreserveRatio(true);
        image.setFitHeight(ICON_SIZE);
        return image;
    }

    private void updateScoreDisplay(int i) {
        scoreNumber.setText(String.format(String.valueOf(i)));
    }

    // TODO: implement lives consumer to change the hearts on the screen
    private void updateLivesDisplay(boolean result) {
        if (result) {
            lifeDisplay.getChildren().remove(lifeDisplay.getChildren().size()-1);
        }
    }

    public VBox getTopViewGP() {
        return this.topFull;
    }
}
