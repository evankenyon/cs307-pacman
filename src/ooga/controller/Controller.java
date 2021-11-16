package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import ooga.controller.IO.JsonParser;
import ooga.controller.IO.JsonParserInterface;
import ooga.controller.IO.keyTracker;
import ooga.model.VanillaGame;
import ooga.model.util.Position;

public class Controller implements ControllerInterface {

  private JsonParserInterface jsonParser;
  private Map<String, List<Position>> wallMap;
  private keyTracker keyTracker;
  private VanillaGame vanillaGame;

  public Controller() {
    jsonParser = new JsonParser();
    keyTracker = new keyTracker();
  }

  // TODO: properly handle exception
  @Override
  public void uploadFile(File file) throws IOException {
    jsonParser.addVanillaGameDataConsumer(vanillaGameDataInterface -> wallMap = vanillaGameDataInterface.getWallMap());
    jsonParser.addVanillaGameDataConsumer(
    vanillaGameDataInterface -> {
      try {
        vanillaGame = new VanillaGame(vanillaGameDataInterface);
      } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
        throw new InputMismatchException("Error occurred in backend reflection");
      }
    });
    jsonParser.uploadFile(file);
  }

  public Map<String, List<Position>> getWallMap() {
    return wallMap;
  }

  @Override
  public void updatePressedKey(KeyEvent event) {
    keyTracker.getPressedKey(event);
  }

}
