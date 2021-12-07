package ooga.view.popups;

import static ooga.view.bottomView.BottomView.ICON_SIZE;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;

import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.util.GameStatus;

public class WinLossPopup {

  public static final int WIN_WIDTH = 400;
  public static final int WIN_HEIGHT = 400;
  public static final int LOSS_WIDTH = 400;
  public static final int LOSS_HEIGHT = 300;
  public static final int TEXT_HEIGHT = 30;
  public static final int PADDING = 20;
  public static final int BUTTON_WIDTH = 200;
  public static final int WIN_LOSS_SPACING = 20;
  public static final int BORDER_WIDTH = 6;

  private ResourceBundle myResources;
  private Stage mainStage;
  private Stage winLossStage;
  private Controller myController;
  private Text message;
  private String STYLESHEET;

  @Deprecated
  public WinLossPopup (Stage stage, Controller controller, GameStatus result) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    mainStage = stage;
    myController = controller;
    winLossStage = new Stage();
//    winLossStage.setScene(createWinLossScene(result));
    winLossStage.show();
  }

  public WinLossPopup (Stage stage, Controller controller, GameStatus result,
                       int score, String selectedViewMode) {
    this.STYLESHEET = "/ooga/view/resources/" + selectedViewMode + ".css";
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    mainStage = stage;
    myController = controller;
    winLossStage = new Stage();
    Image favicon = new Image(
            new File("data/images/pm_favicon.png").toURI().toString());
    winLossStage.getIcons().add(favicon);
    winLossStage.setScene(createWinLossScene(result, score));
    winLossStage.show();
  }

  private Scene createWinLossScene(GameStatus result, int score) {
    if (result.name().equals("WIN")) {
      return createWinScene(score);
    } else if (result.name().equals("LOSS")) {
      return createLossScene(score);
    } else {
      return new Scene(new HBox());
    }
  }

  private Scene createWinScene(int score) {
    winLossStage.setTitle(myResources.getString("winTitle"));
    VBox root = new VBox();
    ImageView youWin = makeImage("youwon", WIN_WIDTH - (2 * PADDING));
    HBox scoreBox = makeScoreBox(score);
    HBox playAgainButton = makePlayAgainButton();
    root.getChildren().addAll(youWin, scoreBox, playAgainButton);
    Group myScene = addBackground(root);
    return new Scene(myScene, WIN_WIDTH, WIN_HEIGHT);
  }

  private Scene createLossScene(int score) {
    winLossStage.setTitle(myResources.getString("lossTitle"));
    VBox root = new VBox();
    String lolGhostsPath = "data/images/lolGhosts.gif";
    ImageView lolGhosts = new ImageView(new Image(new File(lolGhostsPath).toURI().toString()));
    lolGhosts.setPreserveRatio(true);
    lolGhosts.setFitWidth(LOSS_WIDTH - (2 * PADDING));
    Text awYouLost = new Text(myResources.getString("aw"));
    awYouLost.setFont(Font.font("Verdana", FontWeight.BOLD,
            FontPosture.REGULAR, TEXT_HEIGHT));
    awYouLost.setFill(Color.LIGHTGRAY);
    HBox scoreBox = makeScoreBox(score);
    HBox playAgainButton = makePlayAgainButton();
    root.getChildren().addAll(lolGhosts, awYouLost, scoreBox, playAgainButton);
    Group myScene = addBackground(root);
    return new Scene(myScene, LOSS_WIDTH, LOSS_HEIGHT);
  }

  private HBox makePlayAgainButton() {
    ImageView playAgainImg = makeImage("playagain", BUTTON_WIDTH);
    playAgainImg.setOnMouseReleased(e -> playAgainAction());
    HBox buttonFrame = new HBox();
    buttonFrame.setMaxWidth(BUTTON_WIDTH);
    buttonFrame.getChildren().add(playAgainImg);
    buttonFrame.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
            new CornerRadii(BORDER_WIDTH), new BorderWidths(BORDER_WIDTH))));
    return buttonFrame;
  }

  private void playAgainAction() {
    myController.toggleAnimation();
    winLossStage.close();
    myController.restartGame(mainStage);
  }

  private ImageView makeImage(String key, int width) {
    String myPath = "data/images/" + key + "Img-" + myResources.getString(key) + ".png";
    ImageView myImg = new ImageView(new Image(new File(myPath).toURI().toString()));
    myImg.setPreserveRatio(true);
    myImg.setFitWidth(width);
    return myImg;
  }

  private HBox makeScoreBox(int score) {
    HBox scoreBox = new HBox();
    scoreBox.setAlignment(Pos.BOTTOM_CENTER);
    String scoreLabelPath = "data/images/scoreLabel-" + myResources.getString("score") + ".png";
    ImageView scoreLabelImg = new ImageView(new Image(new File(scoreLabelPath).toURI().toString()));
    scoreLabelImg.setPreserveRatio(true);
    scoreLabelImg.setFitHeight(TEXT_HEIGHT);
    Label scoreNum = new Label(Integer.toString(score));
    scoreNum.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, TEXT_HEIGHT));
    scoreNum.setTextFill(Color.LIGHTGRAY);
    scoreBox.getChildren().addAll(scoreLabelImg, scoreNum);
    return scoreBox;
  }

  private Group addBackground(VBox root) {
    root.setPadding(new Insets(PADDING));
    root.setAlignment(Pos.CENTER);
    root.setSpacing(WIN_LOSS_SPACING);
    Group holder = new Group();
    Rectangle bgColor = new Rectangle(0, 0, WIN_WIDTH, WIN_HEIGHT);
    bgColor.setId("myBackgroundColor");
    holder.getChildren().addAll(bgColor, root);
    holder.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    return holder;
  }

  // Used for testing
  protected String getMessage() { return message.getText(); }
  protected Stage getWinLossStage() { return winLossStage; }

}
