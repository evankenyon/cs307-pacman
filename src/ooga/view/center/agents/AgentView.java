package ooga.view.center.agents;

import javafx.scene.Node;

public abstract class AgentView {

  private Node myImage;

  public Node getImage() { return myImage; }

  protected void setImage(Node newImage) { myImage = newImage; }
}
