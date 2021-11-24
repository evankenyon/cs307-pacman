package ooga.view.popups;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorPopups {

    private ResourceBundle myResources;

    public ErrorPopups(String language) {
        myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    }

    public void fileErrorPopup() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("Error"));
        alert.setHeaderText(myResources.getString("InvalidFileHeader"));
        alert.setContentText(myResources.getString("InvalidFileMessage"));
        alert.showAndWait();
    }

    public void noFilePopup() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("Error"));
        alert.setHeaderText(myResources.getString("NoFileHeader"));
        alert.setContentText(myResources.getString("NoFileMessage"));
        alert.showAndWait();
    }

    public void requiredFieldsPopup() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("Error"));
        alert.setHeaderText(myResources.getString("RequiredFieldsHeader"));
        alert.setContentText(myResources.getString("RequiredFieldsMessage"));
        alert.showAndWait();
    }

    public void reflectionErrorPopup() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("Error"));
        alert.setHeaderText(myResources.getString("ReflectionErrorHeader"));
        alert.setContentText(myResources.getString("ReflectionErrorMessage"));
        alert.showAndWait();
    }
}
