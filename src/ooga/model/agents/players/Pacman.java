package ooga.model.agents.players;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.interfaces.Controllable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;

public class Pacman extends AbstractAgent implements Controllable {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int SUPER_STATE = 2;

  private String currentDirection;
  private int myState;
  private Position myPosition;
  private MovementStrategyContext myMover;

  public Pacman(int x, int y) {
    super(x, y, "PACMAN");
    myState = ALIVE_STATE;
  }

  public void setCoords(Position newPosition) {
    myPosition = newPosition;
  }

  public Position step() {
    int[] coords = myPosition.getCoords();
    return handleMovement(coords, currentDirection);
  }


  private Position handleMovement(int[] coordinates, String currentDirection) {
    //refactor this to not use switch case statements potentially?
    //also argument that we never really need it to recognize other keys to move so it doesn't need to be flexible
    return switch (currentDirection) {
      case "left" -> new Position(coordinates[0], coordinates[1] - 1);
      case "right" -> new Position(coordinates[0], coordinates[1] + 1);
      case "up" -> new Position(coordinates[0] - 1, coordinates[1]);
      case "down" -> new Position(coordinates[0] + 1, coordinates[1]);
      default -> null;
    };
  }

  @Override
  public void setDirection(String direction) {
    currentDirection = direction;
  }

  @Override
  public String getDirection() {
    return currentDirection;
  }

  public int consume(Consumable agent){
    agent.agentReact();
    agent.applyEffects();
    return agent.applyPoints();
  }

}
