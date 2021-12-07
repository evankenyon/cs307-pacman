package ooga.controller.IO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileGeneratorTest {

  private static final String PATH = "./data/profiles_test.json";
  private static final File DEFAULT_IMAGE = new File("./data/images/ms_pacman.png");
  private ProfileGenerator profileGenerator;

  @BeforeEach
  void setUp() throws IOException {
    File profiles = new File(PATH);
    PrintWriter writer = new PrintWriter(profiles);
    writer.print("{}");
    writer.close();
    profileGenerator = new ProfileGenerator(PATH);
  }

  @Test
  void createUserSimple() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    Assertions.assertEquals(0, actual.getJSONObject("evankenyon").getInt("high-score"));
    Assertions.assertEquals(0, actual.getJSONObject("evankenyon").getInt("wins"));
    Assertions.assertEquals(0, actual.getJSONObject("evankenyon").getInt("losses"));
    Assertions.assertEquals(DEFAULT_IMAGE.getPath(),
        actual.getJSONObject("evankenyon").getString("image-path"));
    Assertions.assertTrue(
        actual.getJSONObject("evankenyon").getJSONArray("favorite-files").isEmpty());
  }

  @Test
  void createTwoUsers() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.createUser("evankenyon1", "test1234", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    Assertions.assertEquals("test1234", actual.getJSONObject("evankenyon1").getString("password"));
  }

  @Test
  void profileGeneratorWrongPath() {
    try {
      profileGenerator = new ProfileGenerator("bad");
      profileGenerator.createUser("test", "test", DEFAULT_IMAGE);
    } catch (Exception e) {
      Assertions.assertEquals("Username already exists, please choose a different one",
          e.getMessage());
    }
    profileGenerator = new ProfileGenerator("bad");
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.createUser("test", "test", DEFAULT_IMAGE));
  }

  @Test
  void profileGeneratorBadJson() {
    profileGenerator = new ProfileGenerator("./data/no_json.json");
    Assertions.assertThrows(JSONException.class,
        () -> profileGenerator.createUser("test", "test", DEFAULT_IMAGE));
  }

  @Test
  void profileGeneratorTwoSameUsersErrorMessage() {
    try {
      profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
      Assertions.assertThrows(IllegalArgumentException.class,
          () -> profileGenerator.createUser("evankenyon", "test1234", DEFAULT_IMAGE));
    } catch (Exception e) {
      Assertions.assertEquals("Username already exists, please choose a different one",
          e.getMessage());
    }
  }

  @Test
  void profileGeneratorTwoSameUsers() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.createUser("evankenyon", "test1234", DEFAULT_IMAGE));
  }

  @Test
  void loginSimple() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    Assertions.assertEquals("evankenyon",
        profileGenerator.login("evankenyon", "test123").username());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon", "test123").wins());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon", "test123").losses());
    Assertions.assertEquals(DEFAULT_IMAGE.getPath(),
        profileGenerator.login("evankenyon", "test123").imagePath());
    assertEquals(0, profileGenerator.login("evankenyon", "test123").favorites().length);
  }

  @Test
  void loginAfterChange() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateProfilePicture("evankenyon", "test123",
        new File("./data/images/fruit.png"));
    profileGenerator.changeProfileUsername("evankenyon", "test123", "evankenyon1");
    Assertions.assertEquals("evankenyon1",
        profileGenerator.login("evankenyon1", "test123").username());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon1", "test123").wins());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon1", "test123").losses());
    Assertions.assertEquals("./data/images/fruit.png",
        profileGenerator.login("evankenyon1", "test123").imagePath());
    assertEquals(0, profileGenerator.login("evankenyon1", "test123").favorites().length);
  }

  @Test
  void loginNoUsername() {
    try {
      profileGenerator.login("evankenyon", "test123");
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.login("evankenyon", "test123"));
  }

  @Test
  void loginWrongPassword() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    try {
      profileGenerator.login("evankenyon", "test1234");
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.login("evankenyon", "test1234"));
  }

  @Test
  void updateUserStatsBasic() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateUserStats("evankenyon", "test123", 10, false);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals(10, actual.getJSONObject("evankenyon").getInt("high-score"));
    Assertions.assertEquals(0, actual.getJSONObject("evankenyon").getInt("wins"));
    Assertions.assertEquals(1, actual.getJSONObject("evankenyon").getInt("losses"));
  }

  @Test
  void updateUserStatsHighScore() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateUserStats("evankenyon", "test123", 10, false);
    profileGenerator.updateUserStats("evankenyon", "test123", 5, true);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals(10, actual.getJSONObject("evankenyon").getInt("high-score"));
    Assertions.assertEquals(1, actual.getJSONObject("evankenyon").getInt("wins"));
    Assertions.assertEquals(1, actual.getJSONObject("evankenyon").getInt("losses"));
  }

  @Test
  void updateUserStatsHighScoreBad() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    try {
      profileGenerator.updateUserStats("bad", "bad", 10, false);
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.updateUserStats("bad", "bad", 10, false));
  }

  @Test
  void updateUserProfileImage() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateProfilePicture("evankenyon", "test123",
        new File("./data/images/fruit.png"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/images/fruit.png",
        actual.getJSONObject("evankenyon").getString("image-path"));
  }

  @Test
  void updateUserProfileImageTwice() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateProfilePicture("evankenyon", "test123",
        new File("./data/images/fruit.png"));
    profileGenerator.updateProfilePicture("evankenyon", "test123",
        new File("./data/images/heart.png"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/images/heart.png",
        actual.getJSONObject("evankenyon").getString("image-path"));
  }

  @Test
  void updateUserProfileImageBad() {
    try {
      profileGenerator.updateProfilePicture("evankenyon", "test123",
          new File("./data/images/fruit.png"));
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.updateProfilePicture("evankenyon", "test123",
            new File("./data/images/fruit.png")));
  }

  @Test
  void updateProfileUsername() throws IOException {
    updateProfileUsernameOnce();
  }

  @Test
  void updateProfileUsernameTwice() throws IOException {
    updateProfileUsernameOnce();
    profileGenerator.changeProfileUsername("evankenyon1", "test123", "evankenyon2");
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon2"));
  }

  private void updateProfileUsernameOnce() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon"));
    profileGenerator.changeProfileUsername("evankenyon", "test123", "evankenyon1");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon1"));
  }

  @Test
  void updateProfileUsernameBad() {
    try {
      profileGenerator.changeProfileUsername("evankenyon", "test123",
          "evankenyon1");
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.changeProfileUsername("evankenyon", "test123",
            "evankenyon1"));
  }

  @Test
  void updateProfilePassword() throws IOException {
    updateProfilePasswordOnce();
  }

  @Test
  void updateProfilePasswordTwice() throws IOException {
    updateProfilePasswordOnce();
    profileGenerator.changeProfilePassword("evankenyon", "test1234", "test12345");
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test12345", actual.getJSONObject("evankenyon").getString("password"));
  }

  private void updateProfilePasswordOnce() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    profileGenerator.changeProfilePassword("evankenyon", "test123", "test1234");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test1234", actual.getJSONObject("evankenyon").getString("password"));
  }

  @Test
  void updateProfilePasswordBad() {
    try {
      profileGenerator.changeProfilePassword("evankenyon", "test123",
          "test1234");
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.changeProfilePassword("evankenyon", "test123",
            "test1234"));
  }

  @Test
  void addFavoriteFile() throws IOException {
    addOneFavoriteFile();
  }

  @Test
  void addTwoFavoriteFiles() throws IOException {
    addOneFavoriteFile();
    profileGenerator.addFavoriteFile("evankenyon", "test123",
        new File("./data/basic_examples/big_board.json"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/basic_examples/big_board.json",
        actual.getJSONObject("evankenyon").getJSONArray("favorite-files").getString(1));
  }

  @Test
  void addFavoriteFileBad() {
    try {
      profileGenerator.addFavoriteFile("evankenyon", "test123",
          new File("./data/basic_examples/big_board.json"));
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.addFavoriteFile("evankenyon", "test123",
            new File("./data/basic_examples/big_board.json")));
  }

  @Test
  void addFavoriteFileNotJson() {
    try {
      profileGenerator.addFavoriteFile("evankenyon", "test123",
          new File("./data/images/blinky_up.gif"));
    } catch (Exception e) {
      Assertions.assertEquals("Invalid file type, must be .json", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.addFavoriteFile("evankenyon", "test123",
            new File("./data/images/blinky_up.gif")));
  }

  private void addOneFavoriteFile() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.addFavoriteFile("evankenyon", "test123",
        new File("./data/basic_examples/ghost_test_implementation.json"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/basic_examples/ghost_test_implementation.json",
        actual.getJSONObject("evankenyon").getJSONArray("favorite-files").getString(0));
  }

  @Test
  void removeFavoriteFile() throws IOException {
    addOneFavoriteFile();
    profileGenerator.removeFavoriteFile("evankenyon", "test123",
        "./data/basic_examples/ghost_test_implementation.json");
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(
        actual.getJSONObject("evankenyon").getJSONArray("favorite-files").isEmpty());
  }

  @Test
  void removeTwoFavoriteFiles() throws IOException {
    addOneFavoriteFile();
    profileGenerator.addFavoriteFile("evankenyon", "test123",
        new File("./data/basic_examples/big_board.json"));
    profileGenerator.removeFavoriteFile("evankenyon", "test123",
        "./data/basic_examples/ghost_test_implementation.json");
    profileGenerator.removeFavoriteFile("evankenyon", "test123",
        "./data/basic_examples/big_board.json");
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(
        actual.getJSONObject("evankenyon").getJSONArray("favorite-files").isEmpty());
  }

  @Test
  void removeFavoriteFileBadLogin() {
    try {
      profileGenerator.removeFavoriteFile("evankenyon", "test123",
          "./data/basic_examples/big_board.json");
    } catch (Exception e) {
      Assertions.assertEquals("Username or password incorrect", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.removeFavoriteFile("evankenyon", "test123",
            "./data/basic_examples/big_board.json"));
  }

  @Test
  void removeFavoriteFileBadDoesNotExist() throws IOException {
    addOneFavoriteFile();
    try {
      profileGenerator.removeFavoriteFile("evankenyon", "test123",
          "./data/basic_examples/big_board.json");
    } catch (Exception e) {
      Assertions.assertEquals("File does not exist", e.getMessage());
    }
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> profileGenerator.removeFavoriteFile("evankenyon", "test123",
            "./data/basic_examples/big_board.json"));
  }
}