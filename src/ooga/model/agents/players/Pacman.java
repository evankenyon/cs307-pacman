package ooga.model.agents.players;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;

public class Pacman extends AbstractAgent {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int SUPER_STATE = 2;

  private int myState;
  private MovementStrategyContext myMover;

  public Pacman(int x, int y) {
    super(x, y);
    myState = ALIVE_STATE;
  }

  @Override
  public int getState() {
    return myState;
  }

  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  public Position step() {
    int[] coords = getPosition().getCoords();
    String currentDirection = getPosition().getDirection();
    return handleMovement(coords, currentDirection);
  }


  private Position handleMovement(int[] coordinates, String currentDirection) {
    //refactor this to not use switch case statements potentially?
    //also argument that we never really need it to recognize other keys to move so it doesn't need to be flexible
    return switch (currentDirection) {
      case "left" -> new Position((coordinates[0] - 1), coordinates[1]);
      case "right" -> new Position((coordinates[0] + 1), coordinates[1]);
      case "up" -> new Position(coordinates[0], (coordinates[1] + 1));
      case "down" -> new Position(coordinates[0], (coordinates[1] - 1));
      default -> null;
    };
  }

  public int consume(Consumable agent) {
    agent.agentReact();
    agent.applyEffects();
    return agent.applyPoints();
  }

}
