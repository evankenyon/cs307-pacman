package ooga.model;

import java.util.List;
import java.util.Map;
import ooga.model.util.Position;

public record Data(Map<String, List<Position>> wallMap, String player, int numLives, Map<String, Boolean> pelletInfo,
                   int mapCols, int mapRows){
}
