package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONObject;

public class ProfileGenerator {
  private String path;

  public ProfileGenerator() {
    this("./data/profiles.json");
  }

  public ProfileGenerator(String path) {
    this.path = path;
  }

  public void createUser(String username, String password) throws IOException {
    PrintWriter profilesFileWriter = new PrintWriter(path);
    JSONObject oldFile = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject props = new JSONObject();
    props.put("password", password);
    oldFile.put(username, props);
    profilesFileWriter.print(oldFile);
    profilesFileWriter.close();
  }

}
