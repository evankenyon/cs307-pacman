package ooga.model.interfaces;

/**
 * Interface to allow controller to initialize a game
 **/
public interface Game {

  /**
   * steps through an iteration of the game, updating all agents, and the score
   **/
  void step();
}
