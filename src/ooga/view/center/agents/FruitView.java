package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;
import static ooga.view.center.agents.MovableView.IMAGE_PATH;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

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


  public FruitView(Agent fruit, int gridRows, int gridCols) {
    this(fruit, String.format("%s%s", IMAGE_PATH, FRUIT_IMAGE), gridRows, gridCols);
  }

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
    myImage.setX(gridWidth*myAgent.getPosition().getCoords()[0] + horizontalImageBuffer);
    myImage.setY(gridHeight*myAgent.getPosition().getCoords()[1] + verticalImageBuffer);
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
