package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONArray;
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

  public void createUser(String username, String password, File imageFile)
      throws IOException, NullPointerException, JSONException, InterruptedException {
    JSONObject oldFile = JSONObjectParser.parseJSONObject(new File(path));
    PrintWriter profilesFileWriter = new PrintWriter(path);
    if (oldFile.has(username)) {
      profilesFileWriter.print(oldFile);
      profilesFileWriter.close();
      throw new IllegalArgumentException("Username already exists, please choose a different one");
    }
    JSONObject props = new JSONObject();
    props.put("password", password);
    props.put("image-path", getRelativePath(imageFile));
    props.put("high-score", 0);
    props.put("wins", 0);
    props.put("losses", 0);
    props.put("favorite-files", new JSONArray());
    oldFile.put(username, props);
    profilesFileWriter.println(oldFile);
    profilesFileWriter.close();
  }

  public User login(String username, String password) throws IOException {
    JSONObject profiles = JSONObjectParser.parseJSONObject(new File(path));
    if (!profiles.has(username) || !profiles.getJSONObject(username).getString("password").equals(password)) {
      throw new IllegalArgumentException("Username or password incorrect");
    }
    JSONObject userInfo = JSONObjectParser.parseJSONObject(new File(path)).getJSONObject(username);
    String[] favorites = new String[userInfo.getJSONArray("favorite-files").length()];
    for(int index = 0; index < favorites.length; index++) {
      favorites[index] = userInfo.getJSONArray("favorite-files").getString(index);
    }
    return new User(username, userInfo.getString("image-path"), userInfo.getInt("high-score"), userInfo.getInt("wins"), userInfo.getInt("losses"), favorites);
  }

  public void updateProfilePicture(String username, String password, File imageFile)
      throws IOException {
    if(imageFile == null) {
      throw new IOException();
    }
    login(username, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    userInfo.put("image-path", getRelativePath(imageFile));
    allUsersInfo.put(username, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  private String getRelativePath(File imageFile) {
    File currFile = imageFile;
    StringBuilder pathName = new StringBuilder(imageFile.getName());
    while (!currFile.getParentFile().getName().equals("data")) {
      pathName.insert(0, String.format("%s/", currFile.getParentFile().getName()));
      currFile = currFile.getParentFile();
    }
    pathName.insert(0, "./data/");
    return pathName.toString();
  }

  public void addFavoriteFile(String username, String password, File filePath)
      throws IOException {
    login(username, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    userInfo.getJSONArray("favorite-files").put(getRelativePath(filePath));
    allUsersInfo.put(username, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  public void removeFavoriteFile(String username, String password, String filePath)
      throws IOException {
    login(username, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    for (int index = 0; index < userInfo.getJSONArray("favorite-files").length(); index++) {
      if (userInfo.getJSONArray("favorite-files").getString(index).equals(filePath)) {
        userInfo.getJSONArray("favorite-files").remove(index);
        break;
      }
    }
    allUsersInfo.put(username, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  public void changeProfileUsername(String oldUsername, String password, String newUsername)
      throws IOException {
    login(oldUsername, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(oldUsername);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    allUsersInfo.remove(oldUsername);
    allUsersInfo.put(newUsername, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  public void changeProfilePassword(String username, String password, String newPassword)
      throws IOException {
    login(username, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    PrintWriter profilesFileWriter = new PrintWriter(path);
    userInfo.put("password", newPassword);
    allUsersInfo.put(username, userInfo);
    profilesFileWriter.println(allUsersInfo);
    profilesFileWriter.close();
  }

  public void updateUserStats(String username, String password, int score, boolean won)
      throws IOException {
    login(username, password);
    JSONObject allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    JSONObject userInfo = allUsersInfo.getJSONObject(username);
    PrintWriter profilesFileWriter = new PrintWriter(path);
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
