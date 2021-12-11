package ooga.view.popups;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class to create Alerts based on different Error Messages encountered while playing the game.
 *
 * @author Dane Erickson
 */
public class ErrorPopups {

  private ResourceBundle myResources;
  private String alertMessage;
  private Throwable myException;

  /**
   * Constructor to create an ErrorPopups object. Reflection is used to call the corresponding
   * method based on the error type to display the correct error message.
   *
   * @param language is a String for the language used in the game
   * @param type     is a String for the type of error message to be displayed
   */
  @Deprecated
  public ErrorPopups(String language, String type) {
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    errorPopup(myResources.getString(type));
  }

  /**
   * Constructor to create an ErrorPopups object. Reflection is used to call the corresponding
   * method based on the error type to display the correct error message.
   *
   * @param language is a String for the language used in the game
   * @param type     is a String for the type of error message to be displayed
   */
  public ErrorPopups(Throwable e, String language, String type) {
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    myException = e;
    errorPopup(myResources.getString(type));
  }

  private void errorPopup(String type) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("Error"));
    alert.setHeaderText(myResources.getString(String.format("%sHeader", type)));
    alertMessage = myException.getMessage();
    if (alertMessage == null) {
      alertMessage = myResources.getString(String.format("%sMessage", type));
    }
    alert.setContentText(alertMessage);
    alert.showAndWait();
  }

  /**
   * Getter method to return the content's message for the Alert pop up. This is used to test to
   * ensure the correct Alert was created from reflection.
   *
   * @return String alertMessage that is the text in the content of the Alert
   */
  public String getErrorMessage() {
    return alertMessage;
  }
}
