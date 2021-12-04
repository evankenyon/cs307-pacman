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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileGeneratorTest {
  private static final String PATH = "./data/profiles_test.json";
  private static final File DEFAULT_IMAGE = new File("./data/images/ms_pacman.png");
  private ProfileGenerator profileGenerator;

  @BeforeEach
  void setUp() throws IOException, InterruptedException {
    File profiles = new File(PATH);
    PrintWriter writer = new PrintWriter(profiles);
    writer.print("{}");
    writer.close();
    profileGenerator = new ProfileGenerator(PATH);
  }

  @Test
  void createUserSimple() throws IOException, InterruptedException {
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
  void createTwoUsers() throws IOException, InterruptedException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    profileGenerator.createUser("evankenyon1", "test1234", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    Assertions.assertEquals("test1234", actual.getJSONObject("evankenyon1").getString("password"));
  }

  @Test
  void profileGeneratorWrongPath() {
    profileGenerator = new ProfileGenerator("bad");
    Assertions.assertThrows(NullPointerException.class, () -> profileGenerator.createUser("test", "test", DEFAULT_IMAGE));
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
  void updateProfileUsername() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon"));
    profileGenerator.changeProfileUsername("evankenyon", "test123", "evankenyon1");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertTrue(actual.has("evankenyon1"));
  }

  @Test
  void updateProfilePassword() throws IOException {
    profileGenerator.createUser("evankenyon", "test123", DEFAULT_IMAGE);
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    profileGenerator.changeProfilePassword("evankenyon", "test123", "test1234");
    actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test1234", actual.getJSONObject("evankenyon").getString("password"));
  }
}