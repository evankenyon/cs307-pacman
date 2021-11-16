package ooga.view.topView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class TopView {

    public void TopView () {
        initiateTopView();
    }

    private GridPane initiateTopView() {
        Button loadButton = makeButton("Load game", e -> loadGame());
        Button saveButton = makeButton("Save game", e -> saveGame());

        GridPane topGrid = new GridPane();
        topGrid.add(loadButton, 1, 1);
        topGrid.add(saveButton, 2, 1);

        return topGrid;
    }

    private void loadGame() {
        // TODO: Implement
    }

    private void saveGame() {
        // TODO: Implement
    }

    private Button makeButton(String name, EventHandler<ActionEvent> handler) {
        Button myButton = new Button(name);
        myButton.setOnAction(handler);
        return myButton;
    }
}
