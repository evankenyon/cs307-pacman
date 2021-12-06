package ooga.controller.IO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import net.thegreshams.firebase4j.error.FirebaseException;
import java.util.List;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
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
  void getFileSimple() throws FirebaseException, UnsupportedEncodingException {
    JSONObject json = firebaseReader.getFile("test1");
    Assertions.assertEquals("Pacman", json.getString("Player"));
    Assertions.assertEquals(3, json.getInt("NumberOfLives"));
    Assertions.assertEquals("Dot", json.getJSONArray("RequiredPellets").getString(0));
    Assertions.assertEquals("Super", json.getJSONArray("RequiredPellets").getString(1));
    // TODO: add more
  }

  @Test
  void getFileError() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> firebaseReader.getFile("adfjsalfkj"));
  }

  @Test
  void getFilenames() throws FirebaseException, UnsupportedEncodingException {
    Assertions.assertTrue(firebaseReader.getFileNames().contains("test1"));
    Assertions.assertTrue(firebaseReader.getFileNames().contains("test2"));
  }

  @Test
  void getFileNamesAsArray()
      throws FirebaseException, UnsupportedEncodingException {
    Assertions.assertTrue(List.of(firebaseReader.getFileNames().toArray(new String[0])).contains("test1"));
    Assertions.assertTrue(List.of(firebaseReader.getFileNames().toArray(new String[0])).contains("test2"));
  }
  
}