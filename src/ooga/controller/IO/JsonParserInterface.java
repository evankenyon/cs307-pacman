package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import ooga.model.GameData;

public interface JsonParserInterface {

  public void uploadFile(File file) throws IOException;

  public void addVanillaGameDataConsumer(Consumer<GameData> consumer);

  public int getRows();

  public int getCols();

  public void reset();
}
