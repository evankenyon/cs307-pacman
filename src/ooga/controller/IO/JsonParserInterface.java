package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.VanillaGameData;
import ooga.model.VanillaGameDataInterface;

public interface JsonParserInterface {

  public void uploadFile(File file) throws IOException;

  public void addVanillaGameDataConsumer(Consumer<VanillaGameDataInterface> consumer);
}
