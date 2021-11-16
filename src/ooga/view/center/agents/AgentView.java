package ooga.view.center.agents;

import javafx.scene.Node;
import ooga.model.interfaces.Agent;

public abstract class AgentView {

  private Node myImage;
  private int myX;
  private int myY;

  public abstract void updateAgent(Agent agent);

  public Node getImage() { return myImage; }

  protected void setImage(Node newImage) { myImage = newImage; }

  public int getX() { return myX; }

  protected void setX(int newX) { myX = newX; }

  public int getY() { return myY; }

  protected void setY(int newY) { myY = newY; }

}
