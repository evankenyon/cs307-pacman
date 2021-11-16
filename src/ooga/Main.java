package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.Controller;

public class Main extends Application {
    private static final String LANGUAGE = "English";

    /**
     * Start of the program.
     */
    @Override
    public void start(Stage stage) {
        ooga.view.GameStartupPanel myGameStartupPanel = new ooga.view.GameStartupPanel(stage);
        //Controller application = new Controller(LANGUAGE, stage);
        // TODO: move this to controller when MainView has a more fleshed out implementation
//        stage.setScene(application.setScene());
        //stage.show();
    }
}

