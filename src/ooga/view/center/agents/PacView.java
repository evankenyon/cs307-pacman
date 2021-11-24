package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.model.agents.players.Pacman.DEAD_STATE;
import static ooga.model.agents.players.Pacman.SUPER_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.io.File;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.interfaces.Agent;

public class PacView extends MovableView {

  public static final String PAC_IMAGE = String.format("%spacman.png", IMAGE_PATH);
  public static final String SUPER_PAC_IMAGE = String.format("%ssuper_pacman.png", IMAGE_PATH);

  private ImageView pacImage;
  private Image originalImage;
  private Agent myAgent; //TODO: change to subclass of Agent
  private Consumer<Agent> updatePacMan = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;
  private double verticalImageBuffer;
  private double horizontalImageBuffer;

  public PacView(Agent pac, int gridRows, int gridCols) {
    this(pac, PAC_IMAGE, gridRows, gridCols);
  }

  public PacView(Agent pac, String imagePath, int gridRows, int gridCols) {
    myAgent = pac;
    numRows = gridRows;
    numCols = gridCols;
    makeLayoutSettings();
    originalImage = new Image(new File(imagePath).toURI().toString());
    pacImage = new ImageView(originalImage);
    pacImage.setFitWidth(imageBuffer);
    pacImage.setFitHeight(imageBuffer);
    setImage(pacImage);
    pacImage.setX(gridWidth*myAgent.getPosition().getCoords()[0] + horizontalImageBuffer);
    pacImage.setY(gridHeight*myAgent.getPosition().getCoords()[1] + verticalImageBuffer);
// add the Consumers to the List<Consumer<Integer>> in the model
    myAgent.addConsumer(updatePacMan);
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
    verticalImageBuffer = (gridHeight - imageBuffer) / 2;
    horizontalImageBuffer = (gridWidth - imageBuffer) / 2;
  }

  @Override
  protected void moveX(int x) {
    pacImage.setX(gridWidth * x + horizontalImageBuffer);
  }

  @Override
  protected void moveY(int y) {
//    setY(y);
    pacImage.setY(gridHeight * y + verticalImageBuffer);
  }

  @Override
  protected void updateState(int state) {
//    pacImage.setVisible(state == ALIVE_STATE);
    ImageView oldPac = pacImage;
    switch (state) {
      case DEAD_STATE -> pacImage.setVisible(false);
      case ALIVE_STATE -> pacImage.setImage(originalImage);
      case SUPER_STATE -> pacImage.setImage(new Image(new File(SUPER_PAC_IMAGE).toURI().toString()));
    }
    pacImage.setRotate(oldPac.getRotate());
  }

  @Override
  protected void updateOrientation(String orientation) {
    //can delete when null in the map has been merged
    if (ORIENTATION_MAP.get(orientation) != null) {
      pacImage.setRotate(ORIENTATION_MAP.get(orientation));
    }
  }
}
