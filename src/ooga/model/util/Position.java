package ooga.model.util;

import java.util.Arrays;

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

  public Position(int[] array) {
    myCoords = array;
    myDirection = NULL;
  }

  public int[] getCoords() {
    return myCoords;
  }

  public void setCoords(int x, int y) {
    myCoords[0] = x;
    myCoords[1] = y;
  }

  public void setDirection(String direction) {
    myDirection = direction;
  }

  public String getDirection() {
    return myDirection;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return myCoords[0] == position.myCoords[0] && myCoords[1] == position.myCoords[1];
  }

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(myCoords);
    return result;
  }
}
