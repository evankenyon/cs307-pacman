package ooga.model.util;

public class Position {

  int[] myCoords;

  public Position(int x, int y) {
    myCoords = new int[2];
    myCoords[0] = x;
    myCoords[1] = y;
  }

  public int[] getCoords() {
    return myCoords;
  }

  public void setCoords(int x, int y) {
    myCoords[0] = x;
    myCoords[1] = y;
  }
}
