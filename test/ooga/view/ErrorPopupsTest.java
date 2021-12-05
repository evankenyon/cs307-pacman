package ooga.view;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.view.popups.ErrorPopups;
import ooga.view.startupView.GameStartupPanel;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class ErrorPopupsTest extends DukeApplicationTest {

  private ResourceBundle myResources;
  private Controller myController;
  private User myUser;

  @Start
  public void start (Stage stage) {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    new GameStartupPanel(stage, null, myController);
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, LANGUAGE));
  }

  @Test
  void testFileErrorPopup() {
    String expected = "Please input a new file.";
    checkPopupMessage(expected, "InvalidFile");
    checkPopupMessage(expected, "invalidfile");
    checkPopupMessage(expected, "invalidFile");
    checkPopupMessage(expected, "Invalidfile");
  }


  @Test
  void testNoFilePopup() {
    String expected = "Please input a file.";
    checkPopupMessage(expected, "NoFile");
    checkPopupMessage(expected, "Nofile");
    checkPopupMessage(expected, "noFile");
    checkPopupMessage(expected, "nofile");
  }

  @Test
  void testRequiredFieldsPopup() {
    String expected = "Please fill out all fields.";
    checkPopupMessage(expected, "RequiredFields");
    checkPopupMessage(expected, "requiredFields");
    checkPopupMessage(expected, "Requiredfields");
    checkPopupMessage(expected, "requiredfields");
  }

  @Test
  void testReflectionErrorPopup() {
    String expected = "Error occurred in backend reflection.";
    checkPopupMessage(expected, "ReflectionError");
    checkPopupMessage(expected, "reflectionError");
    checkPopupMessage(expected, "Reflectionerror");
    checkPopupMessage(expected, "reflectionerror");
  }

  @Test
  void testSaveErrorPopup() {
    String expected = "Error saving file. Please try again.";
    checkPopupMessage(expected, "SaveError");
    checkPopupMessage(expected, "saveError");
    checkPopupMessage(expected, "Saveerror");
    checkPopupMessage(expected, "saveerror");
  }

  private void checkPopupMessage(String expected, String errorType) {
    runAsJFXAction(() -> {
      ErrorPopups error = new ErrorPopups(LANGUAGE, errorType);
      assertEquals(expected, error.getErrorMessage());
    });
  }
}
