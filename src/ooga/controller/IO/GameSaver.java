package ooga.controller.IO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.GameBoard;
import ooga.model.GameState;
import ooga.model.VanillaGame;
import ooga.model.interfaces.Agent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Dania Fernandez
 * @author Evan Kenyon
 * dependencies: JSONObjectBuilder
 * Used to save a game configuration file locally
 */

public class GameSaver {

  private int counter = 0;
  private StringBuilder path = new StringBuilder();

  private JSONConfigObjectBuilder objectBuilder;


  /**
   * sets objectBuilder to be the JSONObject corresponding to the passed in VanillaGame
   * @param vanillaGame, the current VanillaGame
   */
  public GameSaver(VanillaGame vanillaGame) {
    objectBuilder = new JSONConfigObjectBuilder(vanillaGame);
  }


  /**
   * Saves current game configuration file locally to the data/user_files package
   * @throws IOException
   */
  public void saveGame() throws IOException {
    clearBuilders();
    path.append("data/user_files/user_file");
    path.append("_"+ String.valueOf(counter));
    path.append(".json");
    counter++;

    File jsonFile = new File(String.valueOf(path));
    try {
      FileWriter fileToSave = new FileWriter(jsonFile);
      fileToSave.write(String.valueOf(objectBuilder.setConfig()));
      fileToSave.close();
    } catch (IOException e) {
      System.out.println("Unable to save game.");
    }

  }

  private void clearBuilders() {
    path = new StringBuilder();
  }













}
