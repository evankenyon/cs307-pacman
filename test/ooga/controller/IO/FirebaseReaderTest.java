package ooga.controller.IO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import ooga.model.util.Position;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirebaseReaderTest {
  private FirebaseReader firebaseReader;

  @BeforeEach
  void setUp() throws FirebaseException {
    firebaseReader = new FirebaseReader();
  }

  @Test
  void getFileConfig() throws FirebaseException, UnsupportedEncodingException {
    JSONObject json = firebaseReader.getFile("simple");
    Assertions.assertEquals("Pacman", json.getString("Player"));
    Assertions.assertEquals(3, json.getInt("NumberOfLives"));
    Assertions.assertEquals("Dot", json.getJSONArray("RequiredPellets").getString(0));
    Assertions.assertEquals(0, json.getInt("PlayerScore"));
    Assertions.assertEquals("Wall", json.getJSONArray("WallMap").getJSONArray(0).getString(0));
    Assertions.assertEquals("Pacman", json.getJSONArray("WallMap").getJSONArray(0).getString(1));
    Assertions.assertEquals("Dot", json.getJSONArray("WallMap").getJSONArray(0).getString(2));
  }

  @Test
  void getFileNotConfig() throws FirebaseException, UnsupportedEncodingException {
    JSONObject json = firebaseReader.getFile("bad");
    Assertions.assertEquals("bad", json.getString("bad"));
  }

  @Test
  void getFilenames() throws FirebaseException, UnsupportedEncodingException {
    Assertions.assertTrue(firebaseReader.getFileNames().contains("test1"));
    Assertions.assertTrue(firebaseReader.getFileNames().contains("bad"));
  }

  @Test
  void getFileNamesAsArray()
      throws FirebaseException, UnsupportedEncodingException {
    Assertions.assertTrue(List.of(firebaseReader.getFileNames().toArray(new String[0])).contains("test1"));
    Assertions.assertTrue(List.of(firebaseReader.getFileNames().toArray(new String[0])).contains("bad"));
  }

  @Test
  void badDatabaseUrl() {
    Assertions.assertThrows(FirebaseException.class, () -> new FirebaseReader(null));
  }

  @Test
  void badFilename() {
    Assertions.assertThrows(FirebaseException.class, () -> firebaseReader.getFile(null));
  }

  // I did not put a test for the UnsupportedEncodingException since it is never thrown (the library
  // always passed in UTF-8 as the encoding tpye)

}