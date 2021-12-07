package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import ooga.model.GameEngine;

/**
 * @author Dania Fernandez dependencies: JSONObjectBuilder Used to save a game configuration file
 * locally
 */

public class GameSaver {

  private String userFileName;
  private StringBuilder path = new StringBuilder();

  private JSONConfigObjectBuilder objectBuilder;


  /**
   * sets objectBuilder to be the JSONObject corresponding to the passed in GameEngine
   *
   * @param gameEngine, the current GameEngine
   */
  public GameSaver(GameEngine gameEngine) {
    objectBuilder = new JSONConfigObjectBuilder(gameEngine);
  }


  /**
   * Saves current game configuration file locally to the data/user_files package
   *
   * @throws IOException
   */
  public void saveGame() throws IOException {
    clearBuilders();
    path.append("data/user_files/user_file");
    path.append("_"+ userFileName);
    path.append(".json");

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
