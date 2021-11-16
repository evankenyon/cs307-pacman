package ooga.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.util.Position;

public interface DataInterface {
  public Map<String, List<Position>> getWallMap();

  public String getPlayer();

  public Map<String, Boolean> getPelletInfo();
}
