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
  public void moveAgents() {
    List<Agent> movers = new ArrayList<>();
    //makes assumptions that pacman and ghosts are only things that move)
    movers.add(myState.getPacman());
    movers.addAll(myState.getGhosts());
    for (Agent agent : movers) {
      Position newPosition = agent.getNextMove();
      if (newPosition != null) {
        //only set new coordinate value if move is valid
        if (checkMoveValidity(newPosition)) {
          //set actual new coordinates, check for collisions and apply collision side effects
          agent.setCoords(newPosition);
          checkCollisions(agent);
        }
      }
    }
  }

  //not using checkCollisions separately because that makes pacman and ghost phase through each other
  private void checkCollisions(Agent collider) {
    //makes assumptions that pacman and ghosts are only things that move)
    List<Consumable> consumed = new ArrayList<>(myState.getFood());

    for (Consumable collided : consumed) {
      //we don't check for collisions with ourselves
      if (collider != collided) {
        // if they collide
        if (isOverlapping(collider.getPosition(), collided.getPosition())) {
          // apply effects
          int scoreToAdd = collided.getConsumed();
          // making assumption that only ghosts return 0 when consumed
          if (scoreToAdd == 0) {
            // lots of casting
            Pacman pacman = (Pacman) myState.getPacman();
            pacman.loseOneLife();
          } else {
            myPacScore += scoreToAdd;
          }

          if (myScoreConsumer != null) {
            updateScoreConsumer();
          } else {
            // should have added score consumer in the front end first
            throw new IllegalStateException();
          }
        }
      }
    }
  }

  public void setPlayerDirection(String direction) {
    myState.setPlayerDirection(direction);
  }

  public boolean checkLoss() {
    return false;
  }

  private boolean checkMoveValidity(Position newPosition) {
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return myState.isInBounds(x, y) && !myState.isWall(x, y);
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
}
