package ooga.view.popups;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.util.GameStatus;

public class WinLossPopup {

  public static final int WIN_LOSS_WIDTH = 200;
  public static final int WIN_LOSS_HEIGHT = 200;
  public static final String PLAY_AGAIN_BUTTON_KEY = "PlayAgain";
  public static final int WIN_LOSS_SPACING = 50;

  private ResourceBundle myResources;
  private Stage mainStage;
  private Stage winLossStage;
  private Controller myController;
  private Text message;

  @Deprecated
  public WinLossPopup (Stage stage, Controller controller, GameStatus result) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    mainStage = stage;
    myController = controller;
    winLossStage = new Stage();
//    winLossStage.setScene(createWinLossScene(result));
    winLossStage.show();
  }

  public WinLossPopup (Stage stage, Controller controller, GameStatus result, int score) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    mainStage = stage;
    myController = controller;
    winLossStage = new Stage();
    winLossStage.setScene(createWinLossScene(result, score));
    winLossStage.show();
  }

  private Scene createWinLossScene(GameStatus result, int score) {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setSpacing(WIN_LOSS_SPACING);
    message = new Text(myResources.getString(result.name()));
    Text scoreText = new Text(String.format("%s: %d", myResources.getString("Score"), score));
    Button playAgainButton = makeButton(myResources.getString(PLAY_AGAIN_BUTTON_KEY), e -> playAgainAction());
    root.getChildren().addAll(message, scoreText, playAgainButton);
    return new Scene(root, WIN_LOSS_WIDTH, WIN_LOSS_HEIGHT);
  }

  private Button makeButton(String label, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setOnAction(handler);
    button.setText(label);
    return button;
  }

  private void playAgainAction() {
    myController.toggleAnimation();
    winLossStage.close();
    myController.restartGame(mainStage);
  }

  // Used for testing
  protected String getMessage() { return message.getText(); }
  protected Stage getWinLossStage() { return winLossStage; }

}
