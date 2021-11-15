package ooga;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Start of the program.
     */
    @Override
    public void start(Stage stage) {
        ooga.view.GameStartupPanel myGameStartupPanel = new ooga.view.GameStartupPanel(stage);
    }
}

