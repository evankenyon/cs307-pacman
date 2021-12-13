package ooga.view.center;

import static ooga.Main.LANGUAGE;
import static ooga.Main.VIEW_MODE;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.naming.ldap.Control;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.css.Rect;
import util.DukeApplicationTest;

class BoardViewTest extends DukeApplicationTest {

  public static final String TEST_VIEW_MODE = "Dark";
  private Stage myStage;
  private BoardView boardView;
  private Controller myController;

  @Override
  public void start(Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myStage = stage;
    myController = new Controller(LANGUAGE, stage, VIEW_MODE);
    UserPreferences userPreferences = myController.uploadFile("./data/tests/preferences/SuperPacman.json");
    boardView = new BoardView(myController.getVanillaGame(), myController, userPreferences);
  }

  @Test
  void getBoardPane() {
    int count = 0;
    for (Node child : ((Pane) boardView.getBoardPane()).getChildren()) {
      try {
        Color expected = Color.BLUE;
        Color actual = ((Color) ((Rectangle) child).getFill());
        if(actual == expected) {
          count++;
        }
      } catch (Exception e) {

      }
    }
    Assertions.assertEquals(40, count);
  }
}