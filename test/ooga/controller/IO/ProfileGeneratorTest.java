package ooga.controller.IO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import ooga.controller.IO.utils.JSONObjectParser;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileGeneratorTest {
  private static final String PATH = "./data/profiles_test.json";
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
    profileGenerator.createUser("evankenyon", "test123");
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
  }

  @Test
  void createTwoUsers() throws IOException {
    profileGenerator.createUser("evankenyon", "test123");
    profileGenerator.createUser("evankenyon1", "test1234");
    JSONObject actual = JSONObjectParser.parseJSONObject(new File(PATH));
    Assertions.assertEquals("test123", actual.getJSONObject("evankenyon").getString("password"));
    Assertions.assertEquals("test1234", actual.getJSONObject("evankenyon1").getString("password"));
  }

}