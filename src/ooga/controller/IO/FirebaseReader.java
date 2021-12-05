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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;
import org.json.JSONObject;

public class FirebaseReader {
  private FirebaseDatabase db;
  private Firebase firebase;

  public FirebaseReader() throws IOException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    FileInputStream fis = new FileInputStream("/Users/evankenyon1/Desktop/School_Stuff/CS307/ooga-57bdb-firebase-adminsdk-5d0am-78aa9ce61c.json");

    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(fis))
        .setDatabaseUrl("https://ooga-57bdb-default-rtdb.firebaseio.com/")
        .build();

    try {
      FirebaseApp.initializeApp(options);
      db = FirebaseDatabase.getInstance();
    } catch (IllegalStateException e) {
      db = FirebaseDatabase.getInstance();
    }
  }

//  @Deprecated
//  public Set<String> getFileNames()
//      throws FirebaseException, UnsupportedEncodingException {
//    return firebase.get("").getBody().keySet();
//  }

  public Set<String> getFileNames()
      throws InterruptedException {
    CountDownLatch startSignal = new CountDownLatch(1);
    Set<String> fileNames = new HashSet<>();
    db.getReference("").addValueEventListener(new ValueEventListener() {

      public void onDataChange(DataSnapshot dataSnapshot) {
        dataSnapshot.getChildren().forEach(child -> fileNames.add(child.getKey()));
        startSignal.countDown();
      }


      public void onCancelled(DatabaseError error) {
        System.out.print("Error: " + error.getMessage());
      }
    });

    synchronized (startSignal) {
      startSignal.await();
    }

    return fileNames;
  }

//  @Deprecated
//  public JSONObject getFile(String fileName) throws FirebaseException, UnsupportedEncodingException {
//    String rawBody = firebase.get(fileName).getRawBody();
//    return new JSONObject(rawBody);
//  }

  public JSONObject getFile(String fileName)
      throws InterruptedException {
    CountDownLatch startSignal = new CountDownLatch(1);
    if(!getFileNames().contains(fileName)) {
      throw new IllegalArgumentException("Invalid file name");
    }
    final String[] json = {null};
    db.getReference(fileName).addValueEventListener(new ValueEventListener() {

      public void onDataChange(DataSnapshot dataSnapshot) {
        json[0] = dataSnapshot.getValue().toString();
        json[0] = json[0].replace("=", ":");
        startSignal.countDown();
      }


      public void onCancelled(DatabaseError error) {
        System.out.print("Error: " + error.getMessage());
      }
    });

    synchronized (startSignal) {
      startSignal.await();
    }

    return new JSONObject(json[0]);
  }
}
