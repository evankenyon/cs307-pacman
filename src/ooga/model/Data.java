package ooga.model;

import java.util.List;
import java.util.Map;
import ooga.model.util.Position;

public class Data implements DataInterface {
  private Map<String, List<Position>> wallMap;
  private String player;
  private Map<String, Boolean> pelletInfo;

  public Data(Map<String, List<Position>> wallMap, String player, Map<String, Boolean> pelletInfo) {
    this.wallMap = wallMap;
    this.player = player;
    this.pelletInfo = pelletInfo;
  }

  @Override
  public Map<String, List<Position>> getWallMap() {
    return wallMap;
  }

  @Override
  public String getPlayer() {
    return player;
  }

  @Override
  public Map<String, Boolean> getPelletInfo() {
    return pelletInfo;
  }
}
