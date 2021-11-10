package ooga.model;

/**Interface to allow controller to initialize a game**/
public interface Game {

  /** given a parsed JSON input of the board layout, as well as a list of agents, as well as who is the "controllable" agent, initializes the game setup **/
  void initializeGame();

  /** steps through an iteration of the game, updating all agents, and the score **/
  void step();

  /** returns whether the game has been won **/
  boolean isWin();

  /** returns whether the game has been lost **/
  boolean isLoss();

}
