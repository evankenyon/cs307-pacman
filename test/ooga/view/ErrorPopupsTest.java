package ooga.view;

import static ooga.Main.LANGUAGE;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.popups.ErrorPopups;
import ooga.view.startupView.GameStartupPanel;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class ErrorPopupsTest extends DukeApplicationTest {

  private ResourceBundle myResources;
  private Controller myController;

  @Start
  public void start (Stage stage) {
    new GameStartupPanel(stage);
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, LANGUAGE));
  }

  @Test
  void testFileErrorPopup() {
    String expected = "Please input a new file.";
    checkPopupMessage(expected, "fileError");
  }


  @Test
  void testNoFilePopup() {
    String expected = "Please input a file.";
    checkPopupMessage(expected, "noFile");
  }

  @Test
  void testRequiredFieldsPopup() {
    String expected = "Please fill out all fields.";
    checkPopupMessage(expected, "requiredFields");
  }

  @Test
  void testReflectionErrorPopup() {
    String expected = "Error occurred in backend reflection.";
    checkPopupMessage(expected, "reflectionError");
  }

  private void checkPopupMessage(String expected, String errorType) {
    runAsJFXAction(() -> {
      ErrorPopups error = new ErrorPopups(LANGUAGE, errorType);
      assertEquals(expected, error.getErrorMessage());
    });
  }
}
