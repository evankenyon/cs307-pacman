package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import ooga.model.GameBoard;
import ooga.model.GameState;
import ooga.model.interfaces.Game;
import org.json.JSONObject;


public class GameSaver {

  private static int counter = 1;
  private static StringBuilder path = new StringBuilder();

  //json values
  private static String numberOfLives;
  private static String difficultyLevel;
  private static String wallMap;
  // private static GameState testState = GameBoard.getGameState();

  public void saveGame(GameState currentState) throws IOException {
    clearBuilders();
    path.append("data/user_files/user_file");
    path.append("_"+ String.valueOf(counter));
    path.append(".json");
    counter++;
    File jsonFile = new File(String.valueOf(path));

    try {
      FileWriter fileToSave = new FileWriter(jsonFile);
      JSONObject configObject = new JSONObject();
      numberOfLives = "Test-Number-Of-Lives";
      configObject.put("NumberOfLives", numberOfLives);
      difficultyLevel = "Test-Difficulty-Level";
      configObject.put("WallMap", difficultyLevel);
      wallMap = currentState.getMyWalls().toString();
      configObject.put("WallMap", wallMap);
      fileToSave.write(String.valueOf(configObject));
      fileToSave.close();
    } catch (IOException e) {
      System.out.println("SaveGame Exception");
    }


  }
  private void setWallMap(GameState currentState) {
    currentState.getMyWalls();
  }

  private void clearBuilders() {

    path = new StringBuilder();
  }

}
