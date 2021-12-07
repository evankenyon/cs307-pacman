package ooga.view.userProfileView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.popups.ErrorPopupsTest.TEST_IMAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.User;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.WindowMatchers;
import util.DukeApplicationTest;

public class UserInformationViewTest extends DukeApplicationTest {

  public static final String TEST_FILE = "data/basic_examples/test_implementation.json";

  private Controller myController;
  private Node EditUsernameButton;
  private Node EditPasswordButton;
  private Text UsernameText;
  private UserInformationView profileView;

  @Override
  public void start(Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    try {
      myController.createUser("test", "test", new File(TEST_IMAGE));
    } catch (Exception e) {
      myController.login("test", "test");
    }
    profileView = new UserInformationView(myController, stage, LANGUAGE);
    EditUsernameButton = lookup("#EditUsername").query();
    EditPasswordButton = lookup("#EditPassword").query();
    UsernameText = lookup("#Username").queryText();
  }

  @Test
  void testEditUsername() {
    clickOn(EditUsernameButton);
    writeInputsToDialog("test");
    assertEquals("test", UsernameText.getText().split(": ")[1]);
  }

  @Test
  void testEditPassword() {
    clickOn(EditPasswordButton);
    writeInputsToDialog("test");
  }

}
