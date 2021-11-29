package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;
import static ooga.view.center.agents.MovableView.IMAGE_PATH;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

/**
 * Subclass of StationaryView, which is a subclass of AgentView. FruitView creates a View Agent the
 * shows the fruit that create power ups in the game.
 *
 * @author Dane Erickson
 */
public class FruitView extends StationaryView {

  public static final String FRUIT_IMAGE = "fruit.png";

  //  private VanillaGame myGame;
//  private AgentInfo myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private ImageView myImage;
  private Consumer<Agent> updatePellet = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;
  private double verticalImageBuffer;
  private double horizontalImageBuffer;

  /**
   * Constructor to create the FruitView object using the default image path for cherries as fruit
   *
   * @param fruit is the Agent from the backend that corresponds to the front end Agent
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public FruitView(Agent fruit, int gridRows, int gridCols) {
    this(fruit, String.format("%s%s", IMAGE_PATH, FRUIT_IMAGE), gridRows, gridCols);
  }

  /**
   * Constructor to create the FruitView object using a given image path from a preferences file
   *
   * @param fruit is the Agent from the backend that corresponds to the front end Agent
   * @param imagePath is the inputted path from the preferences file for the Agent's image
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public FruitView(Agent fruit, String imagePath, int gridRows, int gridCols) {
    myAgent = fruit;
//    myInfo = agentInfo;
    numRows = gridRows;
    numCols = gridCols;
    makeLayoutSettings();
    myAgent.addConsumer(updatePellet);
    myImage = new ImageView(imagePath);
    myImage.setFitWidth(imageBuffer);
    myImage.setFitHeight(imageBuffer);
    myImage.setX(gridWidth * myAgent.getPosition().getCoords()[0] + horizontalImageBuffer);
    myImage.setY(gridHeight * myAgent.getPosition().getCoords()[1] + verticalImageBuffer);
    setImage(myImage);
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
    verticalImageBuffer = (gridHeight - imageBuffer) / 2;
    horizontalImageBuffer = (gridWidth - imageBuffer) / 2;
  }


  @Override
  protected void updateState(int newState) {
    myImage.setVisible(newState == ALIVE_STATE);
  }
}
