package ooga.controller.IO;
import java.io.IOException;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.Data;
import ooga.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SaveGameTest {

@Test
void testGameSaver()
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
  //map of only pacman and dot to its right
  Map<String, List<Position>> wallMap = Map.of("Pacman", List.of(new Position(0, 0)), "Dot",
      List.of(new Position(1, 0)));
  Map<String, Boolean> pelletInfo = Map.of("Dot", true);
  Data vanillaGameData = new Data(wallMap, "Pacman", pelletInfo, 1, 1);
  GameState currentState = new GameState(vanillaGameData);
  Stage stage = new Stage();
  Controller controller = new Controller("English", stage);
  controller.saveFile();
}


}
