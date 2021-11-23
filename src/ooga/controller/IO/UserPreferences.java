package ooga.controller.IO;

import java.util.List;
import java.util.Map;
import ooga.model.util.Position;

// Used the following tutorial to learn about Records
// http://tutorials.jenkov.com/java/record.html
public record UserPreferences(Map<String, List<Position>> wallMap, int rows, int cols, Map<String, String> imagePaths, Map<String, List<Double>> colors, String style) {

}
