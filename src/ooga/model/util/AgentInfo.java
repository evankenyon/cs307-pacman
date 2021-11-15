package ooga.model.util;

public class AgentInfo {
  private int myX;
  private int myY;
  private int[] myCoords;
  private int myState;

  public AgentInfo(int x, int y, int state){
    myCoords[0] = x;
    myCoords [1] = y;
    myState = state;
  }

  public boolean equals(int[] agentOneCoords, int[] agentTwoCoords){
    if(agentOneCoords.length == agentTwoCoords.length){
      return (agentOneCoords[0] == agentTwoCoords[0] && agentOneCoords[1] == agentTwoCoords[1]);
    }
    else return false;
  }

  public int getX(){
    return myCoords[0];
  }

  public int getY(){
    return myCoords[1];
  }

  public int getState() { return myState; }

  public void setCoords(int x, int y){
    myCoords[0] = x;
    myCoords[1] = y;
  }

  public void setState(int newState) {
    myState = newState;
  }
}
