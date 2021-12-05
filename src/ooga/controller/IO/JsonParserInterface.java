package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.function.Consumer;
import ooga.model.GameData;
import org.json.JSONException;
import org.json.JSONObject;

public interface JsonParserInterface {

  @Deprecated
  public void uploadFile(File file) throws IOException;

  public void addVanillaGameDataConsumer(Consumer<GameData> consumer);
  public void parseJSON(JSONObject json) throws InputMismatchException, JSONException;

  public int getRows();

  public int getCols();

//  public void reset();
}
