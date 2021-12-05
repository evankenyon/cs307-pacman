package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.Controller;

public class Main extends Application {

  public static final String LANGUAGE = "English";
  public static final String VIEW_MODE = "Dark";

  /**
   * Start of the program.
   */
  @Override
  public void start(Stage stage) {
    Controller application = new Controller(LANGUAGE, stage, VIEW_MODE);
  }
}

