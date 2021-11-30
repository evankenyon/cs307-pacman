package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import ooga.model.GameState;
import org.json.JSONObject;


public class SaveGame {

  private static int counter = 1;
  private static StringBuilder path = new StringBuilder();

  //json values
  private static String Title;
  private static String Player;
  private static String requiredPellets;
  private static String optionalPellets;
  private static String powerUps;
  private static String numberOfLives;
  private static String opponentTypes;
  private static String difficultyLevel;
  private static String wallMap;

  public static void saveGame() throws IOException {

    clearBuilders();
    path.append("data/user_files/user_file");
    path.append("_"+ String.valueOf(counter));
    path.append(".json");
    counter++;
    File jsonFile = new File(String.valueOf(path));

    try {
      FileWriter fileToSave = new FileWriter(jsonFile);
      JSONObject configObject = new JSONObject();
      Player = "Pac-Man"; //default setting until made dynamic
      configObject.put("Player", Player);
      requiredPellets = "Test-RequiredPellets"; //assume it will be pellet types found in map; dcurrently default
      configObject.put("RequiredPellets", requiredPellets);
      numberOfLives = "Test-Number-Of-Lives";
      configObject.put("NumberOfLives", numberOfLives);
      difficultyLevel = "Test-Wall-Map";
      configObject.put("WallMap", difficultyLevel);
      fileToSave.write(String.valueOf(configObject));
      fileToSave.close();
    } catch (IOException e) {
      System.out.println("SaveGame Exception");
    }


  }

  private static void clearBuilders() {
    path = new StringBuilder();
  }

}
