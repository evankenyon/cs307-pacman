package ooga.controller.IO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import net.thegreshams.firebase4j.error.FirebaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirebaseReaderTest {
  private FirebaseReader firebaseReader;

  @BeforeEach
  void setUp() throws IOException {
    firebaseReader = new FirebaseReader();
  }

  @Test
  void getValue() throws FirebaseException, UnsupportedEncodingException {
    Assertions.assertEquals("test", firebaseReader.getFile("test"));
  }
}