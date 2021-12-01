package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
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



  // TODO: handle exceptions
  public GameBoard(Data vanillaGameData)
      throws
      InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myState = new GameState(vanillaGameData);
    myPacScore = 0;
    myGhostScore = 0;
  }

  private void addScore(Integer score) {
    //called whenever a pellet/super-pellet/ghost is consumed, add the score of that pellet to the total score
  }

  private void superState() {
    //set the state of the game to "super" which in turn sets the states of ghosts + pacman to super and starts a countdown
  }


  //TODO: change when we implement list of required consumables?
  public boolean checkWin() {
    //check if the game is a win
    return false;
  }

  //move every agent in the board by one step
  public void moveAll() {
    List<Agent> movers = new ArrayList<>();
    //movers.add(myState.getMyPlayer());
    //movers.addAll(myState.getMyWalls());
    //movers.addAll(myState.getMyOtherAgents());
    movers.add(myState.getPacman());
    movers.addAll(myState.getGhosts());
    for (Agent agent : movers) {
      Position newPosition = agent.step();
      if (newPosition != null) {
        //only set new coordinate value if move is valid
        if (checkMoveValidity(newPosition)) {
          //apply effects first because if we move first we'll have two agents with the same coords
          applyEffects(agent, newPosition);
          //set coordinates after effects have been applied
          agent.setCoords(newPosition);
        }
      }
    }
  }

  private void applyEffects(Agent agent, Position newPosition) {
    if (myState.isFood(newPosition.getCoords()[0],
        newPosition.getCoords()[1])) {
      Consumable colliding = (Consumable) myState.findAgent(newPosition);
      myPacScore += agent.consume(colliding);
      //call this when consumer has actually been added
      updateScoreConsumer();
      LOG.info("score is now {}", myPacScore);
    }
  }

  public void setPlayerDirection(String direction) {
    myState.setPlayerDirection(direction);
  }

  private boolean checkMoveValidity(Position newPosition) {
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return myState.isInBounds(x, y) && !myState.isWall(x, y);
  }

  public GameState getGameState() {
    return myState;
  }

//  public int getScore() {
//    return myScore;
//  }

  public void addScoreConsumer(Consumer<Integer> consumer) {
    myScoreConsumer = consumer;
  }

  public void updateScoreConsumer() {
    myScoreConsumer.accept(myPacScore);
  }
}
