package ooga.controller.IO.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JSONObjectParser {

  public static JSONObject parseJSONObject(File file) throws IOException {
    File currFile = file;
    StringBuilder pathName = new StringBuilder(file.getName());
    while (!currFile.getParentFile().getName().equals("data")) {
      pathName.insert(0, String.format("%s/", currFile.getParentFile().getName()));
      currFile = currFile.getParentFile();
    }

    // Borrowed code to read in a json file from
    // https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file
    InputStream is = JSONObjectParser.class.getClassLoader()
        .getResourceAsStream(String.valueOf(pathName));
    if (is == null) {
      throw new FileNotFoundException();
    }
    String jsonTxt = IOUtils.toString(is, "UTF-8");
    return new JSONObject(jsonTxt);
  }
}
