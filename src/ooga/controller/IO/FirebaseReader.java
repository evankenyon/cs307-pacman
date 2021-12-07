package ooga.controller.IO;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.service.Firebase;
import org.json.JSONObject;

public class FirebaseReader {
  private FirebaseDatabase db;
  private Firebase firebase;

  public FirebaseReader(String firebase_baseURL) throws FirebaseException {
    firebase = new Firebase(firebase_baseURL);
  }
  public FirebaseReader() throws FirebaseException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    this("https://ooga-57bdb-default-rtdb.firebaseio.com/");
  }

  public Set<String> getFileNames() throws FirebaseException, UnsupportedEncodingException {
    return firebase.get("").getBody().keySet();
  }

  public JSONObject getFile(String fileName) throws FirebaseException, UnsupportedEncodingException {
    if(!getFileNames().contains(fileName)) {
      throw new FirebaseException("Invalid file name");
    }
    try {
      String rawBody = firebase.get(fileName).getRawBody();
      System.out.println(rawBody);
      return new JSONObject(rawBody);
    } catch (FirebaseException | UnsupportedEncodingException e) {
      throw new FirebaseException("Invalid file name");
    }
  }

}
