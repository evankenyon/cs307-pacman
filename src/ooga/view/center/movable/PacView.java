package ooga.view.center.movable;

import java.util.function.Consumer;
import javafx.scene.image.ImageView;
import ooga.controller.IO.JsonParser;
import ooga.model.VanillaGame;
import ooga.model.util.AgentInfo;

public class PacView extends PlayerView {

  public static final String PAC_IMAGE = "pacman.png";

  private ImageView pacImage;
  private VanillaGame myGame;
  private JsonParser myParser;
//  private Consumer<Integer[]> updatePacMan = newInfo -> updatePlayer(newInfo);
  private Consumer<AgentInfo> updatePacMan = newInfo -> updatePlayer(newInfo);

//  private Consumer<Integer> updateX = x -> moveX(x);
//  private Consumer<Integer> updateY = y -> moveY(y);
//  private Consumer<Integer> updateState = state -> updateState(state);

  public PacView(VanillaGame game, JsonParser parser) { // TODO: JsonParserInterface??
    myGame = game;
    myParser = parser;
    pacImage = new ImageView(String.format("%s%s", IMAGE_PATH, PAC_IMAGE));
    // add the Consumers to the List<Consumer<Integer>> in the model
//    myParser.addPlayerConsumer(updatePacMan); // This should be from the model instead
//    game.addConsumers(agentInfo, updatePacMan); // TODO: make addConsumers functionality in Game
  }


  @Override
  protected void moveX(int x) {

  }

  @Override
  protected void moveY(int y) {

  }

  @Override
  protected void updateState(int state) {

  }

  @Override
  protected void consume(PlayerView prey) {

  }
}
