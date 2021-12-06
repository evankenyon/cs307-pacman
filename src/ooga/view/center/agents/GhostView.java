package ooga.view.center.agents;

import static ooga.model.agents.consumables.Ghost.DEAD_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.io.File;
import java.util.Random;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

/**
 * Subclass of MovableView, which is a subclass of AgentView. GhostView creates a View Agent that
 * shows the ghosts in the game.
 *
 * @author Dane Erickson
 */
public class GhostView extends MovableView {

  public static final String GHOST_NAMES[] = {"blinky", "pinky", "inky", "clyde", "blue"}; // blue (consumable) ghost must be the last one
  public static final int CONSUMABLE_STATE = 1;
  public static final String GHOST_PATH = "%s%s_right.gif";
  public static final String CHARGED_GHOST_PATH = "%s%s_right_charged.gif";

  private ImageView ghostImage;
  private Agent myAgent;
  private int currGhostNum;
  private int ogGhostNum;
  private Consumer<Agent> updateGhost = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;
  private double verticalImageBuffer;
  private double horizontalImageBuffer;
  private String myOrientation;
  private int myState;

  /**
   * Constructor to create the GhostView object using the default image path for the ghost images
   *
   * @param ghost    is the Agent from the backend that corresponds to the front end Agent
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public GhostView(Agent ghost, int gridRows, int gridCols) {
    this(ghost, String.format(GHOST_PATH, IMAGE_PATH, GHOST_NAMES[new Random().nextInt(GHOST_NAMES.length-1)]), gridRows, gridCols);
  }

  /**
   * Constructor to create the GhostView object using the inputted image path for the ghost images
   * from the preferences file.
   *
   * @param ghost     is the Agent from the backend that corresponds to the front end Agent
   * @param imagePath is the inputted path from the preferences file for the Agent's image
   * @param gridRows  is the row position of the Agent
   * @param gridCols  is the column position of the Agent
   */
  public GhostView(Agent ghost, String imagePath, int gridRows,
      int gridCols) { // make just 1 ghost (not 4) for first test?
    myAgent = ghost;
    myState = 1;
    numRows = gridRows;
    numCols = gridCols;
    myOrientation = "right";
    ogGhostNum = getGhostNum(imagePath);
    makeLayoutSettings();
    ghostViewSetup(imagePath);
  }

  private int getGhostNum(String imagePath) {
    if (imagePath != null) {
      String name = imagePath.split("/")[2].split("_")[0];
      for (int i = 0; i < GHOST_NAMES.length; i++) {
        if (GHOST_NAMES[i].equals(name))
          currGhostNum = i;
      }
    }
    return currGhostNum;
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
    ghostImage.setX(gridWidth * myAgent.getPosition().getCoords()[0] + horizontalImageBuffer);
    ghostImage.setY(gridHeight * myAgent.getPosition().getCoords()[1] + verticalImageBuffer);
    myAgent.addConsumer(updateGhost);
  }

  private ImageView makeGhostImage(String path) {
    ImageView ghost = new ImageView(new Image(new File(path).toURI().toString()));
    ghost.setFitWidth(imageBuffer);
    ghost.setFitHeight(imageBuffer);
    return ghost;
  }

  @Override
  protected void moveX(int x) {
    ghostImage.setX(gridWidth * x + horizontalImageBuffer);
  }

  @Override
  protected void moveY(int y) {
    ghostImage.setY(gridHeight * y + verticalImageBuffer);
  }

  @Override
  protected void updateState(int state) {
    myState = state;
    switch (state) {
      case 0 -> ghostImage.setVisible(state != DEAD_STATE);
      case 1 -> {
        ghostImage.setImage(new Image(new File(String.format("%s%s_%s.gif", IMAGE_PATH, GHOST_NAMES[currGhostNum], myOrientation)).toURI().toString()));
        currGhostNum = ogGhostNum;
      }
      case 2 -> {
        ghostImage.setImage(new Image(new File(String.format("%s%s_%s.gif", IMAGE_PATH, GHOST_NAMES[GHOST_NAMES.length-1], myOrientation)).toURI().toString()));
        currGhostNum = GHOST_NAMES.length-1;
      }
    }
  }

  @Override
  protected void updateOrientation(String orientation) {
    //TODO: account for case of user input image
    ghostImage.setImage(new Image(new File(String.format("%s%s_%s.gif", IMAGE_PATH, GHOST_NAMES[currGhostNum], orientation)).toURI().toString()));
    myOrientation = orientation;
  }
}
