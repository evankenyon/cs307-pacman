package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import ooga.model.agents.players.Pacman;
import ooga.model.endConditions.EndConditionContext;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.interfaces.EndCondition;
import ooga.model.util.GameStatus;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameBoard {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      GameBoard.class.getPackageName());
  private static final String END_CONDITIONS_PACKAGE = String.format("%s.endConditions.",
      GameBoard.class.getPackageName());
  private static final String ENDCONDITIONS_FILENAME = "endconditions";
  private static final Logger LOG = LogManager.getLogger(GameBoard.class);
  private final GameState myState;
  private int myPacScore;
  private int myGhostScore;
  private Consumer<Integer> myScoreConsumer;
  private Consumer<Integer> myLivesConsumer;
  private final List<Consumer<GameStatus>> myGameStatusConsumer;
  private GameStatus currentGameStatus;
  private final EndConditionContext endConditionWin;
  private final EndConditionContext endConditionLoss;

  /**
   * Constructor for GameBoard object.
   *
   * @param vanillaGameData from controller
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   */
  public GameBoard(GameData vanillaGameData)
      throws
      InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
    myState = new GameState(vanillaGameData);
    myPacScore = 0;
    myGhostScore = myState.getFood().size() * 20;
    currentGameStatus = GameStatus.RUNNING;
    myGameStatusConsumer = new ArrayList<>();
    endConditionWin = new EndConditionContext();
    endConditionLoss = new EndConditionContext();
    setUpGameEndConditions(vanillaGameData.player());
  }

  private void setUpGameEndConditions(String player)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle endConditionKeys = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, ENDCONDITIONS_FILENAME));
    String endConditionFileName = endConditionKeys.getString(player);

    ResourceBundle endConditionReflectionKeys = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, endConditionFileName));
    EndCondition winCondition = null;
    winCondition = (EndCondition) Class.forName(
            String.format("%s%s", END_CONDITIONS_PACKAGE,
                endConditionReflectionKeys.getString("winConditionString")))
        .getConstructor()
        .newInstance();

    EndCondition endCondition = null;
    endCondition = (EndCondition) Class.forName(
            String.format("%s%s", END_CONDITIONS_PACKAGE,
                endConditionReflectionKeys.getString("loseConditionString")))
        .getConstructor()
        .newInstance();

    endConditionWin.setStrategy(winCondition);
    endConditionLoss.setStrategy(endCondition);

  }

  /**
   * Moves every agent by one step.
   */
  public void movePawns() {
    List<Agent> movers = new ArrayList<>();
    movers.add(myState.getPacman());
    movers.addAll(myState.getGhosts());
    movers.addAll(myState.getFood());
    movers.addAll(myState.getWalls());
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

  /**
   * Checks collisions between agents and applies effects.
   */
  public void checkCollisions() {
    Agent pacman = myState.getPacman();
    List<Consumable> foods = myState.getFood();
    List<Agent> ghosts = myState.getGhosts();
    for (Agent ghost : ghosts) {
      if (isOverlapping(ghost.getPosition(), pacman.getPosition())) {
        System.out.println(ghost.getPosition());
        System.out.println(pacman.getPosition());
        if (myState.isSuper() && ghost.getState() != 0) {
          System.out.println("ghost and pacman overlapping");
          System.out.print("coords are for ghost and pac: ");
          System.out.print(Arrays.toString(ghost.getPosition().getCoords()));
          System.out.print(" ");
          System.out.print(Arrays.toString(pacman.getPosition().getCoords()));

          Consumable g = (Consumable) ghost;
          myPacScore += g.getConsumed();
          myGhostScore -= g.getConsumed();
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
        myPacScore += food.getConsumed();
        myGhostScore -= food.getConsumed();
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

  /**
   * Checks whether game has ended, be it win or loss.
   */
  public void checkGameEnd() {
    checkWin();
    checkLoss();
  }

  private void checkWin() {
    if (endConditionWin.checkEnd(myState)) {
      currentGameStatus = GameStatus.WIN;
      updateGameStatusConsumer();
    }
  }

  private void checkLoss() {
    if (endConditionLoss.checkEnd(myState)) {
      currentGameStatus = GameStatus.LOSS;
      updateGameStatusConsumer();
    }
  }

  /**
   * sets direction for player object
   *
   * @param direction of player.
   */
  public void setPlayerDirection(String direction) {
    myState.setPlayerDirection(direction);
  }

  private boolean checkMoveValidity(Position newPosition) {
    int x = newPosition.getCoords()[0];
    int y = newPosition.getCoords()[1];
    return !myState.isWall(x, y) || (myState.isWall(x, y) && myState.findAgent(newPosition).getState() == 1);
  }

  /**
   * @return state
   */
  public GameState getGameState() {
    return myState;
  }

  private boolean isOverlapping(Position aPos, Position bPos) {
    return (aPos.getCoords()[0] == bPos.getCoords()[0]
        && aPos.getCoords()[1] == bPos.getCoords()[1]);
  }

  /**
   * Attaches consumer for score for View
   *
   * @param consumer from View
   */
  public void addScoreConsumer(Consumer<Integer> consumer) {
    myScoreConsumer = consumer;
  }

  public void updateScoreConsumer() {
    if (myState.getMyPlayer().getClass().equals(Pacman.class)){
      myScoreConsumer.accept(myPacScore);
    }
    else myScoreConsumer.accept(myGhostScore);
  }

  public void addLivesConsumer(Consumer<Integer> consumer) {
    myLivesConsumer = consumer;
  }

  public void updateLivesConsumer() {
    myLivesConsumer.accept(myState.getLives());
  }

  public void addGameStatusConsumer(Consumer<GameStatus> consumer) {
    myGameStatusConsumer.add(consumer);
  }

  public void updateGameStatusConsumer() {
    myGameStatusConsumer.forEach(consumer -> consumer.accept(currentGameStatus));
  }

  public int getMyPacScore() {
    return myPacScore;
  }

  public int getMyGhostScore() {
    return myGhostScore;
  }
}
