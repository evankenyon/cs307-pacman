package ooga.controller.IO.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Purpose: Use the parseJSONObject method to parse a json file into a JSONObject
 * Dependencies: File, IOException, Files, Path, json-java
 * Example: Call parseJSONObject on a json file to get a JSONObject which represents the data that
 * it contains
 *
 * @author Evan Kenyon
 */
public class JSONObjectParser {

  /**
   * Purpose: Parse a json file into a JSONObject
   * @param file a json file
   * @return a JSONObject representing the data in file
   * @throws IOException thrown if file is invalid
   * @throws JSONException thrown if file does not contain valid json information
   */
  public static JSONObject parseJSONObject(File file) throws IOException, JSONException {
    //Borrowed method for turning file into String from
    // https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

    return new JSONObject(new String(Files.readAllBytes(Path.of(file.getPath()))));
  }
}
