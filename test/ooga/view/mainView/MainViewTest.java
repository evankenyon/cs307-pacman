package ooga.view.mainView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.bottomView.BottomView.MAX_SLIDER_VAL;
import static ooga.view.bottomView.BottomView.MIN_SLIDER_VAL;
import static ooga.view.popups.ErrorPopupsTest.TEST_IMAGE;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.model.util.GameStatus;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class MainViewTest extends DukeApplicationTest {

  public static final String WIN_LOSS_TEST_FILE = "data/basic_examples/win_loss_test.json";

  private Controller myController;
  private Node myPlayPauseButton;
  private MainView myMainView;
  private User myUser;
  private Stage myStage;
  private ResourceBundle myResources;

  @Override
  public void start(Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myStage = stage;
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, LANGUAGE));
    UserPreferences prefs = myController.uploadFile(WIN_LOSS_TEST_FILE);
    try {
      myUser = myController.createUser("test", "test", new File(TEST_IMAGE));
    } catch (Exception e) {
      myUser = myController.login("test", "test");
    }
    myMainView = new MainView(myController, myController.getVanillaGame(), myStage, VIEW_MODE,
        prefs, myUser);
    myPlayPauseButton = lookup("#playPauseButton").query();
  }

  @Test
  void testWin() throws InterruptedException {
    press(KeyCode.D);
    clickOn(myPlayPauseButton);
    Thread.sleep(1000);
    assertEquals(GameStatus.WIN, myMainView.getGameStatus());
  }

  @Test
  void testLoss() throws InterruptedException {
    clickOn(myPlayPauseButton);
    Thread.sleep(1000);
    clickOn(myPlayPauseButton);
    Thread.sleep(1000);
    clickOn(myPlayPauseButton);
    Thread.sleep(2000);
    assertEquals(GameStatus.LOSS, myMainView.getGameStatus());
  }

}
