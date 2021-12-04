package ooga.controller.IO;

import java.io.File;
import java.io.FileNotFoundException;
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

  public void createUser(String username, String password, File imageFile) throws IOException, NullPointerException, JSONException {
    PrintWriter profilesFileWriter = new PrintWriter(path);
    JSONObject oldFile = JSONObjectParser.parseJSONObject(new File(path));
    if (oldFile.has(username)) {
      profilesFileWriter.println(oldFile);
      profilesFileWriter.close();
      throw new IllegalArgumentException("Username already exists, please choose a different one");
    }
    JSONObject props = new JSONObject();
    props.put("password", password);
    props.put("image-path", imageFile.getPath());
    props.put("high-score", 0);
    props.put("wins", 0);
    props.put("losses", 0);
    oldFile.put(username, props);
    profilesFileWriter.println(oldFile);
    profilesFileWriter.close();
  }

  public User login(String username, String password) throws IOException {
    JSONObject profiles = JSONObjectParser.parseJSONObject(new File(path));
    if (!profiles.has(username) || !profiles.getJSONObject(username).getString("password").equals(password)) {
      throw new IllegalArgumentException("Username or password incorrect");
    }
    return new User(username);
  }

  public void updateProfilePicture(String username, String password, File imageFile)
      throws IOException {
    login(username, password);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    userInfo.put("image-path", imageFile.getPath());
    allUsersInfo.put(username, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  public void changeProfileUsername(String oldUsername, String password, String newUsername)
      throws IOException {
    login(oldUsername, password);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(oldUsername);
    allUsersInfo.remove(oldUsername);
    allUsersInfo.put(newUsername, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  public void updateUserStats(String username, String password, int score, boolean won)
      throws IOException {
    login(username, password);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    if(userInfo.getInt("high-score") < score) {
      userInfo.put("high-score", score);
    }
    if(won) {
      userInfo.put("wins", userInfo.getInt("wins") + 1);
    } else {
      userInfo.put("losses", userInfo.getInt("losses") + 1);
    }
    allUsersInfo.put(username, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }
}
