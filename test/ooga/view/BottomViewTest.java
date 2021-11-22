package ooga.view;

import static ooga.Main.LANGUAGE;

import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.mainView.MainView;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class BottomViewTest extends DukeApplicationTest {

  private Controller myController;

  @Override
  public void start (Stage stage) {
    myController = new Controller(LANGUAGE, stage);
  }

  @Test
  void testPlayPause() {

  }



}
