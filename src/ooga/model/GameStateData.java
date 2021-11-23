package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import ooga.factories.AgentFactory;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

public class GameStateData {
  private boolean isWin;
  private boolean isLose;
  private boolean isSuper;
  private int myScore;
  private int foodLeft;
  private final AgentFactory agentFactory = new AgentFactory();
  private List<Agent> myAgentStates;
  private List<Position> myPelletStates;
  private boolean[][] myWallMap;



  public GameStateData(){
    isLose = false;
    isWin = false;
    myAgentStates = new ArrayList<>();

  }
  public GameStateData(GameStateData previous){

  }

  public void initialize(Map<String, List<Position>> gameDict, List<String> pelletInfo){
    int rows = calculateDimension(gameDict, 1);
    int cols = calculateDimension(gameDict, 0);
    isWin = false;
    isLose = false;
    isSuper = false;
    myScore = 0;
    myAgentStates = new ArrayList<>();
    myWallMap = new boolean[rows][cols];
    createWallMap(gameDict, rows, cols);
    createAgentList(gameDict);
    createPelletList(gameDict, pelletInfo);
  }

  private void createPelletList(Map<String, List<Position>> gameDict, List<String> pelletInfo) {
    for (String requiredPellet : pelletInfo){
      List<Position> tempPellets = gameDict.get(requiredPellet);
      for (Position dot : tempPellets){
        myPelletStates.add(dot);
      }
    }
    foodLeft = myPelletStates.size();
  }

  private void createAgentList(Map<String, List<Position>> gameDict) {
    for (String agent : gameDict.keySet()){
      if (agent.equals("Pacman") || agent.equals("Ghost")){
        for (Position agentPos : gameDict.get(agent)){
          int x = agentPos.getCoords()[0];
          int y = agentPos.getCoords()[1];
          myAgentStates.add(agentFactory.createAgent(agent, x, y));
        }
      }
    }
  }

  private void createWallMap(Map<String, List<Position>> gameDict,int rows,int cols) {
    for (int i = 0; i < rows; i++){
      for (int j = 0; j < cols; j++){
        myWallMap[i][j] = false;
      }
    }
    List<Position> walls = gameDict.get("Wall");
    for (Position wall : walls){
      myWallMap[wall.getCoords()[1]][wall.getCoords()[0]] = true;
    }
  }

  private int calculateDimension(Map<String, List<Position>> initialStates, int dim) {
    int maxCol = 0;
    for (String key : initialStates.keySet()) {
      for (Position position : initialStates.get(key)) {
        maxCol = Math.max(position.getCoords()[dim], maxCol);
      }
    }
    return maxCol;
  }

}
