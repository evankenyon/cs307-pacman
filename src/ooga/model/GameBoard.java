package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.GameStatus;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameBoard {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String TYPES_FILENAME = "types";
  private static final Logger LOG = LogManager.getLogger(GameBoard.class);
  private final GameState myState;
  private int myPacScore;
  private int myGhostScore;
  private Consumer<Integer> myScoreConsumer;
  private Consumer<Integer> myLivesConsumer;
  private Consumer<GameStatus> myGameStatusConsumer;
  private GameStatus currentGameStatus;


  // TODO: handle exceptions
  public GameBoard(GameData vanillaGameData)
      throws
      InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myState = new GameState(vanillaGameData);
    myPacScore = 0;
    myGhostScore = 0;
    currentGameStatus = GameStatus.RUNNING;
//    updateGameStatusConsumer();
  }

  //move every agent in the board by one step
  public void movePawns() {
    List<Agent> movers = new ArrayList<>();
    //movers.add(myState.getMyPlayer());
    //movers.addAll(myState.getMyWalls());
    //movers.addAll(myState.getMyOtherAgents());
    movers.add(myState.getPacman());
    movers.addAll(myState.getGhosts());
//    movers.addAll(myState.getFood());
//    movers.addAll(myState.getWalls());
    for (Agent agent : movers) {
      Position newPosition = agent.getNextMove(myState);
      if (newPosition != null) {
        //only set new coordinate value if move is valid
        if (checkMoveValidity(newPosition)) {
          //set coordinates after effects have been applied
          newPosition = myState.portal(newPosition);
          if (myState.isInBounds(newPosition.getCoords()[0], newPosition.getCoords()[1])) {
            agent.setCoords(newPosition);
          }
        }
      }
    }
  }

  public void checkCollisions() {
    Agent pacman = myState.getPacman();
    Position pacPos = pacman.getPosition();
    List<Consumable> foods = myState.getFood();
    List<Agent> ghosts = myState.getGhosts();
    //movers.add(myState.getMyPlayer());
    //movers.addAll(myState.getMyWalls());
    //movers.addAll(myState.getMyOtherAgents());
    for (Agent ghost : ghosts) {
      if (isOverlapping(ghost.getPosition(), pacman.getPosition())) {
        if (myState.isSuper() && ghost.getState() != 0) {
          Consumable g = (Consumable) ghost;
          myPacScore += g.getConsumed();
          myState.resetGhosts();
          updateScoreConsumer();
        } else {
          myState.decreaseLives();
          updateLivesConsumer();
          resetBoard();
        }
      }
    }
    List<Position> foodsToDelete = new ArrayList<>();
    for (Consumable food : foods) {
      if (isOverlapping(food.getPosition(), pacman.getPosition())) {
        // update score & change food state to eaten.
        myPacScore += food.getConsumed();
        foodsToDelete.add(food.getPosition());
        updateScoreConsumer();
      }
    }
    myState.deleteFoods(foodsToDelete);
  }

  private void resetBoard() {
    myState.resetGhosts();
    myState.resetPacman();
  }

  public void checkGameEnd() {
    checkWin();
    checkLoss();
  }

  public void checkWin() {
    if (myState.getRequiredPelletsLeft() == 0) {
      currentGameStatus = GameStatus.WIN;
      updateGameStatusConsumer();
      System.out.println("Game won!");
    }
  }

  public void checkLoss() {
    if (myState.getLives() == 0) {
      currentGameStatus = GameStatus.LOSS;
      updateGameStatusConsumer();
      System.out.println("Game lost!");
    }
  }

  public void setPlayerDirection(String direction) {
    myState.setPlayerDirection(direction);
  }

  private boolean checkMoveValidity(Position newPosition) {
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return !myState.isWall(x, y);
  }

  public GameState getGameState() {
    return myState;
  }


  private boolean isOverlapping(Position aPos, Position bPos) {
    return (aPos.getCoords()[0] == bPos.getCoords()[0]
        && aPos.getCoords()[1] == bPos.getCoords()[1]);
  }

  public void addScoreConsumer(Consumer<Integer> consumer) {
    myScoreConsumer = consumer;
  }

  public void updateScoreConsumer() {
    myScoreConsumer.accept(myPacScore);
  }

  public void addLivesConsumer(Consumer<Integer> consumer) {
    myLivesConsumer = consumer;
  }

  public void updateLivesConsumer() {
    myLivesConsumer.accept(myState.getLives());
  }

  public void addGameStatusConsumer(Consumer<GameStatus> consumer) {
    myGameStatusConsumer = consumer;
  }

  public void updateGameStatusConsumer() {
    myGameStatusConsumer.accept(currentGameStatus);
  }

  public int getMyPacScore() {
    return myPacScore;
  }

  public int getMyGhostScore() {
    return myGhostScore;
  }
}
