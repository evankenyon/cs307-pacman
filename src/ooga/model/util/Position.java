package ooga.model.util;

public class Position {

  private static final String NULL = "NULL";
  int[] myCoords;
  String myDirection;

  public Position(int x, int y) {
    myCoords = new int[2];
    myCoords[0] = x;
    myCoords[1] = y;
    myDirection = NULL;
  }

  public int[] getCoords() {
    return myCoords;
  }

  public void setCoords(int x, int y) {
    myCoords[0] = x;
    myCoords[1] = y;
  }

  public void setDirection(String direction){
    myDirection = direction;
  }

  public String getDirection(){
    return myDirection;
  }
}
