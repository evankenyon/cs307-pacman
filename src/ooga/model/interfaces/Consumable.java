package ooga.model.interfaces;

import ooga.model.agents.players.Pacman;

/**
 * Interface to allow controller to track the state of objects that are able to be consumed,
 * including how many points they are worth
 **/
public interface Consumable extends Agent {

  /**
   * implemenrts the logic for how an agent reacts after being consumed
   **/
  int getConsumed();
}
