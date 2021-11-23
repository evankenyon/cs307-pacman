package ooga.view;

import static ooga.Main.LANGUAGE;
import static ooga.view.BottomViewTest.TEST_FILE;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.view.mainView.MainView;
import util.DukeApplicationTest;

public class StartUpPanelTests extends DukeApplicationTest {

  private Controller myController;
  private GameStartupPanel myStartupPanel;
  private ComboBox<String> selectGameType;
  private ComboBox<String> selectLanguage;
  private ComboBox<String> selectViewMode;

  @Override
  public void start (Stage stage)
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController = new Controller(LANGUAGE, stage);
    UserPreferences prefs = myController.uploadFile(new File(TEST_FILE));
    myStartupPanel = new GameStartupPanel(stage);
  }
}
