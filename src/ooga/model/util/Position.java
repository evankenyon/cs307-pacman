package ooga.model.util;

import java.util.Arrays;

/**
 * Position object.
 */
public class Position {

  private static final String NULL = "NULL";
  int[] myCoords;
  String myDirection;

  /**
   * Constructor for position, direction initially set to null.
   *
   * @param x coordinate
   * @param y coordinate
   */
  public Position(int x, int y) {
    myCoords = new int[2];
    myCoords[0] = x;
    myCoords[1] = y;
    myDirection = NULL;
  }

  /**
   * Can also construct using an array.
   *
   * @param array position coordinates.
   */
  public Position(int[] array) {
    myCoords = array;
    myDirection = NULL;
  }

  /**
   * @return position coordinates
   */
  public int[] getCoords() {
    return myCoords;
  }

  /**
   * set new coordinates
   *
   * @param x
   * @param y
   */
  public void setCoords(int x, int y) {
    myCoords[0] = x;
    myCoords[1] = y;
  }

  /**
   * set new direction
   *
   * @param direction
   */
  public void setDirection(String direction) {
    myDirection = direction;
  }

  /**
   * @return direction string
   */
  public String getDirection() {
    return myDirection;
  }

  // no need to compare directions in our use case when decided whether two objects are equal.
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

  // only using coordinates for hash code comparison
  @Override
  public int hashCode() {
    int result = Arrays.hashCode(myCoords);
    return result;
  }
}
