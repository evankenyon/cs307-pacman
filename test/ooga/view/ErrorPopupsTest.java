package ooga.view;

import static ooga.Main.LANGUAGE;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.popups.ErrorPopups;
import ooga.view.startupView.GameStartupPanel;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorPopupsTest extends DukeApplicationTest {

  private ResourceBundle myResources;
  private Controller myController;

  @Start
  public void start (Stage stage) {
//    myController = new Controller(LANGUAGE, stage);
    new GameStartupPanel(stage);
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, LANGUAGE));
  }


  @Test
  void testFileErrorPopup() {
    String expected = "Please input a new file.";
//    ErrorPopups error = new ErrorPopups(LANGUAGE, "fileError");
    Alert a = new Alert(AlertType.ERROR);
    assertEquals(expected, myResources.getString("InvalidFileMessage"));
  }

}
