package ooga.controller;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.popups.ErrorPopupsTest.TEST_IMAGE;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.model.util.GameStatus;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

  public static final String WIN_TEST_FILE = "data/basic_examples/cheat_easy_win.json";

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
    UserPreferences prefs = myController.uploadFile(WIN_TEST_FILE);
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
  void testWin()
      throws InterruptedException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    press(KeyCode.D);
    clickOn(myPlayPauseButton);
    Thread.sleep(1000);
    runAsJFXAction(() -> {
      UserPreferences prefs = null;
      try {
        prefs = myController.uploadFile(WIN_TEST_FILE);
      } catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {}
      try {
        myUser = myController.createUser("test", "test", new File(TEST_IMAGE));
        myUser = myController.login("test", "test");
      } catch (Exception e) {}
      myMainView = new MainView(myController, myController.getVanillaGame(), myStage, VIEW_MODE,
          prefs, myUser);
    });
    myController.pauseOrResume();
    assertEquals(Status.RUNNING, myController.getAnimationStatus());
  }

}
