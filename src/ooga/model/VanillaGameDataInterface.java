package ooga.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.util.AgentInfo;

public interface VanillaGameDataInterface {
  public Map<String, List<AgentInfo>> getWallMap();

  public String getPlayer();

  public Map<String, Boolean> getPelletInfo();
}
