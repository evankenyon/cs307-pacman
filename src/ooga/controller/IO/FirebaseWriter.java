package ooga.controller.IO;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import com.google.firebase.database.ValueEventListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.annotation.processing.Completion;
import javax.naming.Reference;
import netscape.javascript.JSObject;
import ooga.model.VanillaGame;
import org.apache.commons.logging.Log;
import org.json.JSONObject;

public class FirebaseWriter {

  JSONConfigObjectBuilder builder;
  VanillaGame myVanillaGame;
  private FirebaseDatabase db;
  private DatabaseReference configRef;
  private JSONObject object;


  public FirebaseWriter() throws IOException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    FileInputStream fis = new FileInputStream("./data/ooga-57bdb-firebase-adminsdk-5d0am-78aa9ce61c.json");

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
    configRef = db.getReference(); //no params
    System.out.println(String.valueOf(configRef));
  }
  //TODO: have child name update based on # of existing user files

  public void saveObject(VanillaGame vanillaGame) throws FileNotFoundException {
    myVanillaGame = vanillaGame;
    builder = new JSONConfigObjectBuilder(myVanillaGame);
    object = builder.setConfig();
    configRef.child("test0List").child("item1").setValueAsync(true);
    //configRef.push().setValue(object, (databaseError, databaseReference) -> {
     // if (databaseError != null) {
     //   System.out.println("SAVE FAILED :(");
     // }
    //});

  }

  public void onCancelled(DatabaseError error) {
    System.out.print("Error: " + error.getMessage());
  }
}
