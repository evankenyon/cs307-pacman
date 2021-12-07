package ooga.view.BottomView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.popups.ErrorPopupsTest.TEST_IMAGE;
import static ooga.view.bottomView.BottomView.MAX_SLIDER_VAL;
import static ooga.view.bottomView.BottomView.MIN_SLIDER_VAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.controller.IO.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.matcher.base.WindowMatchers;
import util.DukeApplicationTest;

public class BottomViewTest extends DukeApplicationTest {

  public static final long DELAY = 1000;
  public static final String TEST_FILE = "data/basic_examples/test_implementation.json";

  private Controller myController;
  private Node myPlayPauseButton;
  private MainView myMainView;
  private Slider mySpeedSlider;
  private Node myProfilePicButton;
  private User myUser;

  @Override
  public void start (Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    UserPreferences prefs = myController.uploadFile(new File(TEST_FILE));
    myUser = new User("test", "test", TEST_IMAGE, 0,0,0, null);
    myMainView = new MainView(myController, myController.getVanillaGame(), stage, VIEW_MODE, prefs, myUser);
    myPlayPauseButton = lookup("#playPauseButton").query();
    mySpeedSlider = lookup("#speedSlider").query();
    myProfilePicButton = lookup("#profilePic").query();
  }

  @Test
  void testPlayPause() throws InterruptedException {
    clickOn(myPlayPauseButton);
    assertEquals(false, myController.getPlayPause());
    press(KeyCode.D);
    clickOn(myPlayPauseButton);
    assertEquals(true, myController.getPlayPause());
  }

  @Test
  void testSpeedSliderMin() {
    clickOn(myPlayPauseButton);
    setValue(mySpeedSlider, MIN_SLIDER_VAL);
    double actual = myController.getAnimationSpeed();
    assertEquals(MIN_SLIDER_VAL, actual);
  }

  @Test
  void testSpeedSliderMax() {
    clickOn(myPlayPauseButton);
    setValue(mySpeedSlider, MAX_SLIDER_VAL);
    double actual = myController.getAnimationSpeed();
    assertEquals(MAX_SLIDER_VAL, actual);
  }

  @Test
  void testSpeedSlider() {
    clickOn(myPlayPauseButton);
    setValue(mySpeedSlider, (MAX_SLIDER_VAL+MIN_SLIDER_VAL)/2);
    double actual = myController.getAnimationSpeed();
    assertEquals((MAX_SLIDER_VAL+MIN_SLIDER_VAL)/2, actual);
  }

  @Test
  void testViewProfile() {
    clickOn(myProfilePicButton);
    FxAssert.verifyThat(window(0), WindowMatchers.isShowing());
//    targetWindow(0);
//    clickOn(lookup("#EditUsername").query());
//    writeInputsToDialog("test");
//    clickOn(lookup("#EditPassword").query());
//    writeInputsToDialog("test");
  }

}
