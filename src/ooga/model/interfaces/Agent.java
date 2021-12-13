package ooga.model.interfaces;


import java.util.function.Consumer;
import ooga.model.GameState;
import ooga.model.util.Position;

/**
 * Interface to set/retrieve agents coords.
 **/
public interface Agent {

  /**
   * add consumer to link to view
   *
   * @param consumer
   */
  void addConsumer(Consumer<Agent> consumer);

  /**
   * updates consumer linked to view
   */
  void updateConsumer();

  /**
   * gets agents next potential move.
   *
   * @param state current game state
   * @return position object to move to
   */
  Position getNextMove(GameState state);

  /**
   * @return current agent position.
   */
  Position getPosition();

  /**
   * @return current agent state.
   */
  int getState();

  /**
   * set new coordinates, actually moving agent
   *
   * @param newPosition to move to
   */
  void setCoords(Position newPosition);

  /**
   * set agent direction
   *
   * @param direction string
   */
  void setDirection(String direction);

  /**
   * set agent state
   *
   * @param i state
   */
  void setState(int i);

  /**
   * add agent runnable
   *
   * @param runnable
   */
  void addRunnable(Runnable runnable);

  /**
   * set agent movement strategy
   *
   * @param strategy movable object
   */
  void setStrategy(Movable strategy);
}
