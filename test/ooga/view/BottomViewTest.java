package ooga.view;

import static ooga.Main.LANGUAGE;
import static ooga.view.bottomView.BottomView.MAX_SLIDER_VAL;
import static ooga.view.bottomView.BottomView.MIN_SLIDER_VAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class BottomViewTest extends DukeApplicationTest {

  public static final long DELAY = 1000;
  public static final String TEST_FILE = "data/basic_examples/test_implementation.json";

  private Controller myController;
  private Node myPlayPauseButton;
  private MainView myMainView;
  private Slider mySpeedSlider;

  @Override
  public void start (Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController = new Controller(LANGUAGE, stage);
    UserPreferences prefs = myController.uploadFile(new File(TEST_FILE));
    myMainView = new MainView(myController, myController.getVanillaGame(), stage, prefs);
    myPlayPauseButton = lookup("#playPauseButton").query();
    mySpeedSlider = lookup("#speedSlider").query();

  }

  @Test
  void testPlayPause() throws InterruptedException {
    press(KeyCode.D);
    clickOn(myPlayPauseButton);
    assertEquals(true, myController.getPlayPause());
    clickOn(myPlayPauseButton);
    assertEquals(false, myController.getPlayPause());
  }

  @Test
  void testSpeedSliderMin() {
    setValue(mySpeedSlider, MIN_SLIDER_VAL);
    double actual = myController.getAnimationSpeed();
    assertEquals(MIN_SLIDER_VAL, actual);
  }

  @Test
  void testSpeedSliderMax() {
    setValue(mySpeedSlider, MAX_SLIDER_VAL);
    double actual = myController.getAnimationSpeed();
    assertEquals(MAX_SLIDER_VAL, actual);
  }

  @Test
  void testSpeedSlider() {
    setValue(mySpeedSlider, (MAX_SLIDER_VAL+MIN_SLIDER_VAL)/2);
    double actual = myController.getAnimationSpeed();
    assertEquals((MAX_SLIDER_VAL+MIN_SLIDER_VAL)/2, actual);
  }

}
