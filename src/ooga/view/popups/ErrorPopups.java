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

  /**
   * Constructor to create an ErrorPopups object. Reflection is used to call the corresponding
   * method based on the error type to display the correct error message.
   *
   * @param language is a String for the language used in the game
   * @param type is a String for the type of error message to be displayed
   */
  public ErrorPopups(String language, String type) {
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    String methodName = String.format("%sPopup", type);
    try {
      Method m = ErrorPopups.class.getDeclaredMethod(methodName, null);
      m.invoke(this, null);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      reflectionErrorPopup();
    }
  }

  private void fileErrorPopup() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("Error"));
    alert.setHeaderText(myResources.getString("InvalidFileHeader"));
    alertMessage = myResources.getString("InvalidFileMessage");
    alert.setContentText(alertMessage);
    alert.showAndWait();
  }

  private void noFilePopup() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("Error"));
    alert.setHeaderText(myResources.getString("NoFileHeader"));
    alertMessage = myResources.getString("NoFileMessage");
    alert.setContentText(alertMessage);
    alert.showAndWait();
  }

  private void requiredFieldsPopup() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("Error"));
    alert.setHeaderText(myResources.getString("RequiredFieldsHeader"));
    alertMessage = myResources.getString("RequiredFieldsMessage");
    alert.setContentText(alertMessage);
    alert.showAndWait();
  }

  private void reflectionErrorPopup() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("Error"));
    alert.setHeaderText(myResources.getString("ReflectionErrorHeader"));
    alertMessage = myResources.getString("ReflectionErrorMessage");
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
