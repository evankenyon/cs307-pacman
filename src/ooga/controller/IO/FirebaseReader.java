package ooga.controller.IO;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;
import java.io.IOException;

public class FirebaseReader {
  private Firebase firebase;

  public FirebaseReader() throws IOException, FirebaseException {
    String firebase_baseUrl = "https://ooga-57bdb-default-rtdb.firebaseio.com/";
    firebase = new Firebase(firebase_baseUrl);
  }

  public Set<String> getFileNames() throws FirebaseException, UnsupportedEncodingException {
    return firebase.get("").getBody().keySet();
  }

  public String getFile(String key) throws FirebaseException, UnsupportedEncodingException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    return (String) firebase.get("/test").getBody().get("alanisawesome");
  }
}
