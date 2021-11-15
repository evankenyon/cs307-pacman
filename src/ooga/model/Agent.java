package ooga.model;


import ooga.model.util.AgentInfo;

/**
 * Interface to set/retrieve agents coords.
 **/
public interface Agent {

  /**
   * set the coordinates of a given agent
   **/
  void setCoordinates(AgentInfo data);

  /**
   * get the coordinates of a given agent
   **/
  void getCoordinates();

}
