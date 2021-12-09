package ooga.view.topView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.popups.ErrorPopupsTest.TEST_IMAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class TopViewTest extends DukeApplicationTest {

  public static final String TEST_FILE = "data/basic_examples/test_implementation.json";

  private Controller myController;
  private Node myPlayPauseButton;
  private MainView myMainView;
  private Slider mySpeedSlider;
  private Node myProfilePicButton;
  private User myUser;

  @Override
  public void start(Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    UserPreferences prefs = myController.uploadFile(TEST_FILE);
    myUser = new User("test", "test", TEST_IMAGE, 0, 0, 0, null);
    myMainView = new MainView(myController, myController.getVanillaGame(), stage, VIEW_MODE, prefs,
        myUser);
    myPlayPauseButton = lookup("#playPauseButton").query();
    mySpeedSlider = lookup("#speedSlider").query();
    myProfilePicButton = lookup("#profilePic").query();
  }

  @Test
  void testNumberLives() throws InterruptedException {
    clickOn(myPlayPauseButton);
    Thread.sleep(5000);
    HBox lifeDisplay = myMainView.getTopView().getLifeDisplay();
    int expected = 4; // should have 1 node for "PAC", 1 node for "LIVES:" and 2 nodes for 2 hearts after one life lost
    int actual = lifeDisplay.getChildren().size();
    assertEquals(expected, actual);
  }

}
