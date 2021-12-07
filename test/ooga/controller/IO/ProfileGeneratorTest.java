package ooga.controller.IO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
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
    Assertions.assertEquals(DEFAULT_IMAGE.getPath(), actual.getJSONObject("evankenyon").getString("image-path"));
    Assertions.assertTrue(actual.getJSONObject("evankenyon").getJSONArray("favorite-files").isEmpty());
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
    profileGenerator = new ProfileGenerator("bad");
    Assertions.assertThrows(IllegalArgumentException.class, () -> profileGenerator.createUser("test", "test", DEFAULT_IMAGE));
  }

  @Test
  void profileGeneratorBadJson() {
    profileGenerator = new ProfileGenerator("./data/no_json.json");
    Assertions.assertThrows(JSONException.class, () -> profileGenerator.createUser("test", "test", DEFAULT_IMAGE));
  }

  @Test
  void profileGeneratorTwoSameUsers() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    Assertions.assertThrows(IllegalArgumentException.class, () -> profileGenerator.createUser("evankenyon", "test1234", DEFAULT_IMAGE));
  }

  @Test
  void loginSimple() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    Assertions.assertEquals("evankenyon", profileGenerator.login("evankenyon", "test123").username());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon", "test123").wins());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon", "test123").losses());
    Assertions.assertEquals(DEFAULT_IMAGE.getPath(), profileGenerator.login("evankenyon", "test123").imagePath());
    assertEquals(0, profileGenerator.login("evankenyon", "test123").favorites().length);
  }

  @Test
  void loginAfterChange() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateProfilePicture("evankenyon", "test123",
        new File("./data/images/fruit.png"));
    profileGenerator.changeProfileUsername("evankenyon", "test123", "evankenyon1");
    Assertions.assertEquals("evankenyon1", profileGenerator.login("evankenyon1", "test123").username());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon1", "test123").wins());
    Assertions.assertEquals(0, profileGenerator.login("evankenyon1", "test123").losses());
    Assertions.assertEquals("./data/images/fruit.png", profileGenerator.login("evankenyon1", "test123").imagePath());
    assertEquals(0, profileGenerator.login("evankenyon1", "test123").favorites().length);
  }

  @Test
  void loginNoUsername() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> profileGenerator.login("evankenyon", "test123"));
  }

  @Test
  void loginWrongPassword() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    Assertions.assertThrows(IllegalArgumentException.class, () -> profileGenerator.login("evankenyon", "test1234"));
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
  void updateUserProfileImage() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.updateProfilePicture("evankenyon", "test123",
        new File("./data/images/fruit.png"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/images/fruit.png", actual.getJSONObject("evankenyon").getString("image-path"));
  }

  @Test
  void updateProfileUsername() throws IOException, InterruptedException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon"));
    profileGenerator.changeProfileUsername("evankenyon", "test123", "evankenyon1");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon1"));
  }

  @Test
  void updateProfilePassword() throws IOException, InterruptedException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    profileGenerator.changeProfilePassword("evankenyon", "test123", "test1234");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test1234", actual.getJSONObject("evankenyon").getString("password"));
  }

  @Test
  void addFavoriteFile() throws IOException, InterruptedException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.addFavoriteFile("evankenyon", "test123",
        new File("./data/basic_examples/ghost_test_implementation.json"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/basic_examples/ghost_test_implementation.json", actual.getJSONObject("evankenyon").getJSONArray("favorite-files").getString(0));
  }

  @Test
  void removeFavoriteFile() throws IOException, InterruptedException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.addFavoriteFile("evankenyon", "test123",
        new File("./data/basic_examples/ghost_test_implementation.json"));
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("./data/basic_examples/ghost_test_implementation.json", actual.getJSONObject("evankenyon").getJSONArray("favorite-files").getString(0));
    profileGenerator.removeFavoriteFile("evankenyon", "test123",
        "./data/basic_examples/ghost_test_implementation.json");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.getJSONObject("evankenyon").getJSONArray("favorite-files").isEmpty());
  }
}