package ooga;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import ooga.model.Agent;
import ooga.model.Game;
import ooga.model.GameBoard;

public class keyTracker {
  private int dx;
  private int dy;
  private GameBoard board; //how to actually get from instance of game ?

  private void getPressedKey(KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
      dx = -1;
    }
    else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
      dx = 1;
    }
    else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
      dy = 1;
    }
    else if (event.getKeyCode() == KeyEvent.VK_UP) {
     dy = -1;
    }
  }

  //make sure no movement if hitting wall --> legal move

  public void movePlayer(int currentX, int currentY) {
    int spaceX = currentX + dx;
    int spaceY = currentY + dy;
    boolean isValid = validateMove(spaceX, spaceY);
    if (isValid) {
      //move player to [spaceX][spaceY]
    }
    else {
      //send some indication to user that player cannot move here (maybe as simple as not moving)
    }
  }

  /**
   * Checks whether player can be moved to new space
   * @param spaceX
   * @param spaceY
   * @returns false if new space is a wall, true otherwise
   */
  private boolean validateMove(int spaceX, int spaceY) {
    List<List<Agent>> grid = board.getMyGrid();
    Agent a = grid.get(spaceX).get(spaceY);
    if (a.getAgentType().equals("wall")) {
      return false;
    }
    else {
      return true;
    }
  }

}
