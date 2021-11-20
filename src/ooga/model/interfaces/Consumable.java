package ooga.model.interfaces;

/**Interface to allow controller to track the state of objects that are able to be consumed, including how many points they are worth**/
public interface Consumable extends Agent {

  /** implemenrts the logic for how an agent reacts after being consumed**/
  void agentReact();

  /** implements the external effects of that consumable being consumed; fruits and dots increase point values, super-dots set Pacman into super mode, ghosts increase the score
   * @param agent**/
  void applyEffects(Agent agent);

  /** returns the point value for consuming the given object **/
  int applyPoints();

}
