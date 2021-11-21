package ooga.controller.IO;

import java.util.List;
import java.util.Map;
import ooga.model.util.Position;

public record UserPreferences(Map<String, List<Position>> wallMap, Map<String, String> imagePaths, Map<String, List<Double>> colors, String style) {

}
