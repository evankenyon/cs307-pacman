package ooga.model.util;

public class Utility {

  /**
   * translates string to coordinates
   *
   * @param coordinates      int[]
   * @param currentDirection string (right, left, up, down)
   * @return new position object.
   */
  public static Position translateDirectionToPosition(int[] coordinates, String currentDirection) {
    return switch (currentDirection) {
      case "left" -> new Position((coordinates[0] - 1), coordinates[1]);
      case "right" -> new Position((coordinates[0] + 1), coordinates[1]);
      case "up" -> new Position(coordinates[0], (coordinates[1] - 1));
      case "down" -> new Position(coordinates[0], (coordinates[1] + 1));
      default -> null;
    };
  }

}
