package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.function.Consumer;
import ooga.model.Data;
import org.json.JSONException;
import org.json.JSONObject;

public interface JsonParserInterface {

  @Deprecated
  public void uploadFile(File file) throws IOException;

  public void parseJSON(JSONObject json) throws InputMismatchException, JSONException;

  public void addVanillaGameDataConsumer(Consumer<Data> consumer);

  public int getRows();

  public int getCols();

//  public void reset();
}
