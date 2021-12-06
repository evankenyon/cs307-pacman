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

  public WinLossPopup (Stage stage, Controller controller, GameStatus result) {
    myResources = ResourceBundle.getBundle(RESOURCES_PATH_WITH_LANGUAGE);
    mainStage = stage;
    myController = controller;
    winLossStage = new Stage();
    winLossStage.setScene(createWinLossScene(result));
    winLossStage.show();
  }

  private Scene createWinLossScene(GameStatus result) {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setSpacing(WIN_LOSS_SPACING);
    Text text = new Text(myResources.getString(result.name()));
    Button playAgainButton = makeButton(myResources.getString(PLAY_AGAIN_BUTTON_KEY), e -> playAgainAction());
    root.getChildren().addAll(text, playAgainButton);
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

}
