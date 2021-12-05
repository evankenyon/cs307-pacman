package ooga.controller.IO.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectParser {

  public static JSONObject parseJSONObject(File file) throws IOException, JSONException {
    //Borrowed method for turning file into String from
    // https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

    return new JSONObject(new String(Files.readAllBytes(Path.of(file.getPath()))));
  }
}
