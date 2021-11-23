package ooga.view.center.agents;

import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

public class GhostView extends MovableView {

  public static final String GHOST_NAMES[] = {"blue","blinky","pinky","inky","clyde"};

  public static final int CONSUMABLE_STATE = 1;

  private ImageView ghostImage;
  private Agent myAgent;
  private int ghostNum;
  private Consumer<Agent> updateGhost = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;
  private double verticalImageBuffer;
  private double horizontalImageBuffer;

  public GhostView(Agent ghost, int gridRows, int gridCols) { // make just 1 ghost (not 4) for first test?
    this(ghost, String.format("%s%s_right.png", IMAGE_PATH, GHOST_NAMES[0]), gridRows, gridCols);
  }

  public GhostView (Agent ghost, String imagePath, int gridRows, int gridCols) { // make just 1 ghost (not 4) for first test?
    myAgent = ghost;
    ghostNum = 0; //TODO: Deal with Ghost Number
    numRows = gridRows;
    numCols = gridCols;
    makeLayoutSettings();
    ghostViewSetup(imagePath);
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
    verticalImageBuffer = (gridHeight - imageBuffer) / 2;
    horizontalImageBuffer = (gridWidth - imageBuffer) / 2;
  }

  private void ghostViewSetup(String path) {
    ghostImage = makeGhostImage(path); //TODO: fix Ghost Number
    setImage(ghostImage);
//    setX(myAgent.getPosition().getCoords()[0]);
//    setY(myAgent.getPosition().getCoords()[1]);
    ghostImage.setX(gridWidth * myAgent.getPosition().getCoords()[0] + horizontalImageBuffer);
    ghostImage.setY(gridHeight * myAgent.getPosition().getCoords()[1] + verticalImageBuffer);
    myAgent.addConsumer(updateGhost);
  }

  private ImageView makeGhostImage(String path) {
    ImageView ghost = new ImageView(path);
    ghost.setFitWidth(imageBuffer);
    ghost.setFitHeight(imageBuffer);
    return ghost;
  }

  @Override
  protected void moveX(int x) {
//    setX(x);
    ghostImage.setX(gridWidth * x + horizontalImageBuffer);
  }

  @Override
  protected void moveY(int y) {
//    setY(y);
    ghostImage.setY(gridHeight * y + verticalImageBuffer);
  }

  @Override
  protected void updateState(int state) {
    ghostImage.setVisible(state == CONSUMABLE_STATE);
  }

  @Override
  protected void updateOrientation(String orientation) {
    //TODO: account for case of user input image
    try {
      ghostImage.setImage(new Image(String.format("%s%s_%s.png", IMAGE_PATH, GHOST_NAMES[ghostNum], orientation)));
    }
    catch (Exception e) { // Don't change the image because it's going up or down
      return;
    }
  }
}
