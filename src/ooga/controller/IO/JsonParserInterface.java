package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.util.Position;

public interface JsonParserInterface {

  public void uploadFile(File file) throws IOException;

  public void addWallMapConsumer(Consumer<Map<String, List<Position>>> consumer);

  public void addPlayerConsumer(Consumer<String> consumer);

  public void addPelletsConsumer(Consumer<Map<String, Boolean>> consumer);
}
