package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileGenerator {

  private static final String DEFAULT_RESOURCE_PACKAGE = String.format("%s.resources.",
      ProfileGenerator.class.getPackageName());
  private static final String USER_STATS_REFLECTION_FILENAME = "UpdateUserStatsReflection";

  private String path;
  private PrintWriter profilesFileWriter;
  private JSONObject userInfo;
  private JSONObject allUsersInfo;

  public ProfileGenerator() {
    this("./data/profiles.json");
  }

  public ProfileGenerator(String path) {
    if(!new File(path).exists()) {
      this.path = "./data/profiles.json";
    } else {
      this.path = path;
    }
  }

  public void createUser(String username, String password, File imageFile)
      throws IOException, NullPointerException, JSONException, IllegalArgumentException {
    if (username == null || password == null || imageFile == null) {
      throw new IOException();
    }
    JSONObject oldFile = JSONObjectParser.parseJSONObject(new File(path));
    profilesFileWriter = new PrintWriter(path);
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
    closePrintWriter(oldFile);
  }

  public User login(String username, String password) throws IOException {
    JSONObject profiles = JSONObjectParser.parseJSONObject(new File(path));
    if (!profiles.has(username) || !profiles.getJSONObject(username).getString("password")
        .equals(password)) {
      throw new IllegalArgumentException("Username or password incorrect");
    }
    JSONObject userInfo = JSONObjectParser.parseJSONObject(new File(path)).getJSONObject(username);
    String[] favorites = new String[userInfo.getJSONArray("favorite-files").length()];
    for (int index = 0; index < favorites.length; index++) {
      favorites[index] = userInfo.getJSONArray("favorite-files").getString(index);
    }
    return new User(username, password, userInfo.getString("image-path"),
        userInfo.getInt("high-score"), userInfo.getInt("wins"), userInfo.getInt("losses"),
        favorites);
  }

  public void updateProfilePicture(String username, String password, File imageFile)
      throws IOException {
    if (imageFile == null) {
      throw new IOException();
    }
    updateUserAttribute(username, password,
        userInfo -> userInfo.put("image-path", getRelativePath(imageFile)));
  }

  public void addFavoriteFile(String username, String password, File filePath)
      throws IOException {
    updateUserAttribute(username, password,
        userInfo -> userInfo.getJSONArray("favorite-files").put(getRelativePath(filePath)));
  }

  public void removeFavoriteFile(String username, String password, String filePath)
      throws IOException {
    updateUserAttribute(username, password, userInfo -> {
      for (int index = 0; index < userInfo.getJSONArray("favorite-files").length(); index++) {
        if (userInfo.getJSONArray("favorite-files").getString(index).equals(filePath)) {
          userInfo.getJSONArray("favorite-files").remove(index);
          break;
        }
      }
    });
  }

  public void changeProfileUsername(String oldUsername, String password, String newUsername)
      throws IOException {
    setupCurrentUserData(oldUsername, password);
    allUsersInfo.remove(oldUsername);
    allUsersInfo.put(newUsername, userInfo);
    closePrintWriter(allUsersInfo);
  }

  public void changeProfilePassword(String username, String password, String newPassword)
      throws IOException {
    updateUserAttribute(username, password, userInfo -> userInfo.put("password", newPassword));
  }

  public void updateUserStats(String username, String password, int score, boolean won)
      throws IOException {
    login(username, password);
    ResourceBundle userStatsReflection = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, USER_STATS_REFLECTION_FILENAME));
    updateUserAttribute(username, password, userInfo -> {
      userInfo.put("high-score", Math.max(userInfo.getInt("high-score"), score));
    });
    updateUserAttribute(username, password, userInfo -> {
      String key = userStatsReflection.getString(String.format("%s%s", "Won", won));
      userInfo.put(key, userInfo.getInt(key) + 1);
    });
  }

  private void updateUserAttribute(String username, String password, Consumer<JSONObject> consumer)
      throws IOException {
    setupCurrentUserData(username, password);
    consumer.accept(userInfo);
    allUsersInfo.put(username, userInfo);
    closePrintWriter(allUsersInfo);
  }

  private void setupCurrentUserData(String username, String password) throws IOException {
    login(username, password);
    allUsersInfo = JSONObjectParser.parseJSONObject(new File(path));
    userInfo = allUsersInfo.getJSONObject(username);
    profilesFileWriter = new PrintWriter(path);
  }

  private void closePrintWriter(JSONObject json) {
    profilesFileWriter.println(json);
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
}
