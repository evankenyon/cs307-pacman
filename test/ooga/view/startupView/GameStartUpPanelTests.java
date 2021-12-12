package ooga.view.startupView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.BottomView.BottomViewTest.TEST_FILE;
import static ooga.view.popups.ErrorPopupsTest.TEST_IMAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.view.startupView.GameStartupPanel;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class GameStartUpPanelTests extends DukeApplicationTest {

  private Controller myController;
  private GameStartupPanel myStartupPanel;
  private ComboBox<String> selectGameType;
  private ComboBox<String> selectLanguage;
  private ComboBox<String> selectViewMode;
  private Node playButton;
  private User myUser;
  private Node myPlayPauseButton;

  @Override
  public void start(Stage stage) {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    myUser = new User("test", "test", TEST_IMAGE, 0, 0, 0, null);
    myStartupPanel = new GameStartupPanel(stage, myUser, myController);
    selectGameType = lookup("#GameFile").queryComboBox();
    selectLanguage = lookup("#Language").queryComboBox();
    selectViewMode = lookup("#ViewingMode").queryComboBox();
    playButton = lookup("#startButton").query();
  }

  @Test
  void testSelectFile() {
    select(selectGameType, "Select Locally");
    assertEquals("Select Locally", selectGameType.getValue());
    select(selectGameType, "Select From Database");
    assertEquals("Select From Database", selectGameType.getValue());
    select(selectGameType, "Select From Favorites");
    assertEquals("Select From Favorites", selectGameType.getValue());
  }

  @Test
  void testSelectLanguage() {
    select(selectLanguage, "English");
    assertEquals("English", selectLanguage.getValue());
  }

  @Test
  void testSelectViewMode() {
    select(selectViewMode, "Dark");
    assertEquals("Dark", selectViewMode.getValue());
  }

  @Test
  void testChangeLanguage() {
    select(selectLanguage, "German");
    select(selectViewMode, "Dark");
    select(selectGameType, "Select Locally");
    applyPath(TEST_FILE);
    clickOn(playButton);
    assertEquals("German", myStartupPanel.getNewLanguage());
  }

  private void applyPath(String filePath) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection stringSelection = new StringSelection(filePath);
    clipboard.setContents(stringSelection, stringSelection);
    press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
    push(KeyCode.ENTER);
  }

}
