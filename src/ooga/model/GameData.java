package ooga.model;

import java.util.List;
import java.util.Map;
import ooga.model.util.Position;

@Deprecated
record Data(Map<String, List<Position>> wallMap, String player, int playerScore, int numLives, Map<String, Boolean> pelletInfo,
            int mapCols, int mapRows){
}

public record GameData(Map<String, List<Position>> wallMap, String player, int playerScore, int numLives, Map<String, Boolean> pelletInfo,
                       int mapCols, int mapRows){
}