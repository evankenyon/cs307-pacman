package ooga.controller.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileGenerator {
  private String path;

  public ProfileGenerator() {
    this("./data/profiles.json");
  }

  public ProfileGenerator(String path) {
    this.path = path;
  }

  public void createUser(String username, String password) throws IOException, NullPointerException, JSONException {
    PrintWriter profilesFileWriter = new PrintWriter(path);
    JSONObject oldFile = JSONObjectParser.parseJSONObject(new File(path));
    if (oldFile.has(username)) {
      throw new IllegalArgumentException("Username already exists, please choose a different one");
    }
    JSONObject props = new JSONObject();
    props.put("password", password);
    props.put("high-score", 0);
    props.put("wins", 0);
    props.put("losses", 0);
    oldFile.put(username, props);
    profilesFileWriter.println(oldFile);
    profilesFileWriter.flush();
    profilesFileWriter.close();
  }

  public User login(String username, String password) throws IOException {
    JSONObject profiles = JSONObjectParser.parseJSONObject(new File(path));
    if (!profiles.has(username) || !profiles.getJSONObject(username).getString("password").equals(password)) {
      throw new IllegalArgumentException("Username or password incorrect");
    }
    return new User(username);
  }

  public void updateUserStats(String username, String password, int score, boolean won)
      throws IOException {
    User user = login(username, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
  }

}
