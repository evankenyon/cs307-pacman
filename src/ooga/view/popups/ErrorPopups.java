package ooga.view.popups;

import javafx.scene.control.Alert;

public class ErrorPopups {
    public void ErrorPopups(){}

    private void fileErrorPopup() {
        // TODO: Wire all text to resources files
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("ERROR: INVALID FILE INPUT");
        alert.setContentText("Please input a new file.");
        alert.showAndWait();
    }
}
