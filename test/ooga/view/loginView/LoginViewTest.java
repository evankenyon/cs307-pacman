package ooga.view.loginView;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static ooga.view.loginView.LoginView.SIGN_IN_ID;
import static ooga.view.loginView.LoginView.SIGN_UP_ID;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.Controller;

import ooga.controller.IO.User;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

@TestMethodOrder(OrderAnnotation.class)
public class LoginViewTest extends DukeApplicationTest {

  public static final String TEST_IMAGE = "data/images/fruit.png";

  private ResourceBundle myResources;
  private Controller myController;
  private LoginView myLoginView;
  private Node mySignInButton;
  private Node mySignUpButton;


  @Start
  public void start(Stage stage) {
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    myLoginView = new LoginView(stage, myController);
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, LANGUAGE));
    mySignInButton = lookup(String.format("#%s", SIGN_IN_ID)).query();
    mySignUpButton = lookup(String.format("#%s", SIGN_UP_ID)).query();
  }

  @Test
  @Order(1)
  void testSignUp() throws IOException, InterruptedException {
    String username = makeRandomString();
    String password = makeRandomString();
    System.out.println(username + "  " + password);
    clickOn(mySignUpButton);
    writeInputsToDialog(username);
    writeInputsToDialog(password);
    press(KeyCode.ENTER);
    User user = myLoginView.makeUserNoImage(username, password, TEST_IMAGE);
    assertEquals(username, user.username(), "Username test failed");
    assertEquals(password, user.password(), "Password test failed");
  }

  //  @Test
//  void testSignUpError() throws IOException, InterruptedException {
//    System.out.println(username + "  " + password);
//    User user = myLoginView.makeUserNoImage(username, password, null);
//    String expected = "Invalid Username or Password";
//    assertEquals(expected, myLoginView.getError().getErrorMessage());
//  }
//
  @Test
  @Order(2)
  void testSignIn() throws IOException, InterruptedException {
    try {
      myLoginView.makeUserNoImage("test", "test", TEST_IMAGE);
    } catch (Exception e) {
    }
    clickOn(mySignInButton);
    writeInputsToDialog("test");
    writeInputsToDialog("test");
    User user = myLoginView.getUser();
    assertEquals("test", user.username(), "Username test failed");
    assertEquals("test", user.password(), "Password test failed");
  }

  // modified from https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
  private String makeRandomString() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    StringBuilder str = new StringBuilder();
    Random rnd = new Random();
    while (str.length() < 18) { // length of the random string.
      int index = (int) (rnd.nextFloat() * chars.length());
      str.append(chars.charAt(index));
    }
    String randString = str.toString();
    return randString;
  }
}
