package ooga.model.util;

public class AgentInfo {
  private int[] myCoords;
  private int myState;

  public AgentInfo(int x, int y, int state){
    myCoords = new int[2];
    myCoords[0] = x;
    myCoords [1] = y;
    myState = state;
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

  @Override
  public boolean equals(Object o) {
    // Borrowed from cell society code written by Tarun Amasa
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgentInfo other = (AgentInfo) o;
    return other.getState() == this.getState() && other.getX() == this.getX() && other.getY() == this.getY();
  }
}
