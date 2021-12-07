package ooga.view.popups;

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
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class WinLossPopupTest extends DukeApplicationTest {

  public static final String WIN_TEST_FILE = "data/basic_examples/easy_win.json";

  private Controller myController;
  private Node myPlayPauseButton;
  private MainView myMainView;
  private Slider mySpeedSlider;
  private User myUser;
  private ResourceBundle myResources;

  @Override
  public void start (Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    UserPreferences prefs = myController.uploadFile(new File(WIN_TEST_FILE));
    myUser = new User("test", "test", TEST_IMAGE, 0,0,0, null);
    myMainView = new MainView(myController, myController.getVanillaGame(), stage, VIEW_MODE, prefs, myUser);
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, LANGUAGE));
    myPlayPauseButton = lookup("#playPauseButton").query();
  }

  @Test
  void testWin() {
    clickOn(myPlayPauseButton);
    press(KeyCode.D);
  }

}
