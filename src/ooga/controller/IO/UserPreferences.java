package ooga.controller.IO;

import java.util.List;
import java.util.Map;
import ooga.model.util.Position;

// Used the following tutorial to learn about Records
// http://tutorials.jenkov.com/java/record.html

/**
 * Purpose: Represents UserPreferences packaged into an object Dependencies: List, Map, Position
 * Example: This is returned to the frontend from Controller in order to set up the initial images
 * and colors for the game
 *
 * @author Evan Kenyon
 */
public record UserPreferences(Map<String, List<Position>> wallMap, int rows, int cols,
                              Map<String, String> imagePaths, Map<String, List<Double>> colors,
                              String style, String language) {

}
