package ooga.controller.IO;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.UnsupportedEncodingException;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseReader {
  private FirebaseDatabase db;

  public FirebaseReader() throws IOException {

  }

  public String getFile(String key) throws FirebaseException, UnsupportedEncodingException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    String firebase_baseUrl = "https://ooga-57bdb-default-rtdb.firebaseio.com/";
    Firebase firebase = new Firebase(firebase_baseUrl);
    return (String) firebase.get("/test").getBody().get("alanisawesome");
  }
}
