package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.GameStartupPanel;

public class Main extends Application {
    private static final String LANGUAGE = "English";

    /**
     * Start of the program.
     */
    @Override
    public void start(Stage stage) {
        Controller application = new Controller(LANGUAGE, stage);
    }
}

