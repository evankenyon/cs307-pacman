package ooga.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class VanillaGame implements Game {

  private static final String BOARD_CONFIGURATION = "board";
  private Controllable myPlayer;
  private GameBoard myBoard;
  private List<Consumer<String>> myObservers;

  //private GameScore myScore; potential data structure to hold score, highscore, time played, etc.?

  public VanillaGame(Map<String, String> properties, List<List<String>> boardLayout) {
    //Use reflection and properties.get(MY_PLAYER)to initialize myPlayer to either a ghostPlayer or pacPlayer
    myBoard = new GameBoard(boardLayout.get(0).size(), boardLayout.size(), boardLayout, myPlayer);
  }

  //get board layout from controller, then controller (?) needs to access the map again to see if the keystoke is valid, and then return the coordinate pacman should be at again.

  public void initializeGame() {

  }


  public void step() {

  }

  public boolean isWin() {
    return false;
  }

  public boolean isLoss() {
    return false;
  }

}

