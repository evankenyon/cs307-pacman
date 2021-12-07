package ooga.view.instructions;

import static ooga.Main.LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class InstructionsViewTest extends DukeApplicationTest {

  public static final String TEST_VIEW_MODE = "Dark";

  private InstructionsView instructions;
  private Node nextButton;
  private Node backButton;
  private Node closeButton;
  private Stage myStage;

  @Override
  public void start(Stage stage) {
    myStage = stage;
    instructions = new InstructionsView(stage, LANGUAGE, TEST_VIEW_MODE);
    nextButton = lookup("#next").query();
    backButton = lookup("#back").query();
    closeButton = lookup("#close").query();
  }

  @Test
  void testNextButton() {
    clickOn(nextButton);
    assertEquals(1, instructions.getCurrentIndex());
  }

  @Test
  void testCloseButton() {
    clickOn(closeButton);
    assertFalse(myStage.isShowing());
  }

}
