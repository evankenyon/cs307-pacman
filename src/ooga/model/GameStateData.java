package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.factories.AgentFactory;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

public class GameStateData {
  private boolean isWin;
  private boolean isLose;
  private boolean isSuper;
  private int myPacScore;
  private int myGhostScore;
  private int foodLeft;
  private final AgentFactory agentFactory = new AgentFactory();
  private List<Agent> myAgentStates;
  private List<Position> myPelletStates;
  private boolean[][] myWallMap;
  private boolean[][] myDotMap;



  public GameStateData(){
    isLose = false;
    isWin = false;
    myPacScore = 0;
    myGhostScore = 0;
    myAgentStates = new ArrayList<>();
    myPelletStates = new ArrayList<>();

  }
  public GameStateData(GameStateData previous){
    isWin = previous.isWin;
    isLose = previous.isLose;
    myPacScore = previous.myPacScore;
    myGhostScore = previous.myGhostScore;
    isSuper = previous.isSuper;
    foodLeft = previous.foodLeft;
    myAgentStates = previous.myAgentStates;
    myPelletStates = previous.myPelletStates;
    myWallMap = previous.myWallMap;

  }

  public void initialize(Map<String, List<Position>> gameDict, List<String> pelletInfo){
    int rows = calculateDimension(gameDict, 1) + 1;
    int cols = calculateDimension(gameDict, 0) + 1;
    isWin = false;
    isLose = false;
    isSuper = false;
    myPacScore = 0;
    myGhostScore = 0;
    myAgentStates = new ArrayList<>();
    myWallMap = new boolean[cols][rows];
    myDotMap = new boolean[cols][rows];
    createWallMap(gameDict, rows, cols);
    createDotMap(gameDict, rows, cols);
    createAgentList(gameDict);
    createRequiredPelletList(gameDict, pelletInfo);
  }

  public int getFoodLeft() {
    foodLeft = myPelletStates.size();
    return foodLeft;
  }

  public List<Agent> getMyAgentStates() {
    return myAgentStates;
  }

  public List<Position> getMyPelletStates() {
    return myPelletStates;
  }

  public boolean isWin(){
    return isWin;
  }

  public boolean isLose(){
    return isLose;
  }

  public int getMyPacScore(){
    return myPacScore;
  }

  public int getMyGhostScore() {
    return myGhostScore;
  }

  public boolean isWall(int x, int y){
    return myWallMap[x][y];
  }

  public boolean isDot(int x, int y){
    return myDotMap[x][y];
  }

  public Agent getPacman(){
    return myAgentStates.get(0);
  }

  public List<Agent> getGhosts(){
    return myAgentStates.subList(1, myAgentStates.size());
  }

  private void createRequiredPelletList(Map<String, List<Position>> gameDict, List<String> pelletInfo) {
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
        myDotMap[j][i] = false;
      }
    }
    List<Position> walls = gameDict.get("Wall");
    for (Position wall : walls){
      myDotMap[wall.getCoords()[0]][wall.getCoords()[1]] = true;
    }
  }

  private void createDotMap(Map<String, List<Position>> gameDict,int rows,int cols) {
    for (int i = 0; i < rows; i++){
      for (int j = 0; j < cols; j++){
        myWallMap[j][i] = false;
      }
    }
    List<Position> walls = gameDict.get("Dot");
    for (Position wall : walls){
      myWallMap[wall.getCoords()[0]][wall.getCoords()[1]] = true;
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
