package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.factories.AgentFactory;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameStateNew {
  private GameStateData myGameStateData;
  private final int DX = 1;




  public GameStateNew(Data vanillaGameData)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myGameStateData = new GameStateData();
    myGameStateData.initialize(vanillaGameData.wallMap(), vanillaGameData.pelletInfo());
  }


  public List<String> getLegalActions(Agent agent){
    List<String> directions = new ArrayList<>();
    Position agentPos = agent.getPosition();
    if (!myGameStateData.isWall(agentPos.getCoords()[0] + DX, agentPos.getCoords()[1])){
      directions.add("EAST");
    }
    if (!myGameStateData.isWall(agentPos.getCoords()[0] - DX, agentPos.getCoords()[1])){
      directions.add("WEST");
    }
    if (!myGameStateData.isWall(agentPos.getCoords()[0], agentPos.getCoords()[1] + DX)){
      directions.add("NORTH");
    }
    if (!myGameStateData.isWall(agentPos.getCoords()[0], agentPos.getCoords()[1] - DX)){
      directions.add("SOUTH");
    }
    return directions;
  }

}
