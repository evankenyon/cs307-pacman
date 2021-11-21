package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import ooga.model.Data;

public interface JsonParserInterface {

  public void uploadFile(File file) throws IOException;

  public void addVanillaGameDataConsumer(Consumer<Data> consumer);
}
