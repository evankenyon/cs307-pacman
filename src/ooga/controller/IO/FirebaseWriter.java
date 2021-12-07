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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Completion;
import javax.naming.Reference;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import net.thegreshams.firebase4j.service.Firebase.FirebaseRestMethod;
import netscape.javascript.JSObject;
import ooga.model.VanillaGame;
import org.apache.commons.logging.Log;
import org.json.JSONObject;

/**
 * @author Dania Fernandez
 * @author Evan Kenyon
 * dependencies: JSONObjectBuilder
 * Saves a JSONObject of the current game configuration to Firebase
 */
public class FirebaseWriter {

  private JSONConfigObjectBuilder builder;
  private VanillaGame myVanillaGame;
  private Firebase firebase;
  private FirebaseResponse response;
  private DatabaseReference configRef;
  private JSONObject object;
  private String userObjectName;
  private int counter = 0;

  /**
   * Sets up firebase and response based on the ooga Firebase url
   * @throws FirebaseException
   * @throws UnsupportedEncodingException
   */
  public FirebaseWriter() throws FirebaseException, UnsupportedEncodingException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    String firebase_baseUrl = "https://ooga-57bdb-default-rtdb.firebaseio.com/";
    firebase = new Firebase(firebase_baseUrl);
    response = firebase.get();
  }
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    //FileInputStream fis = new FileInputStream("./data/ooga-57bdb-firebase-adminsdk-5d0am-78aa9ce61c.json");

    //FirebaseOptions options = FirebaseOptions.builder()
       // .setCredentials(GoogleCredentials.fromStream(fis))
       // .setDatabaseUrl("https://ooga-57bdb-default-rtdb.firebaseio.com/")
       // .build();

    //try {
     // FirebaseApp.initializeApp(options);
      //db = FirebaseDatabase.getInstance();
    //} catch (IllegalStateException e) {
     // db = FirebaseDatabase.getInstance();
    //}
    //configRef = db.getReference(); //no params
    //System.out.println(String.valueOf(configRef));
  //}
  //TODO: have child name update based on # of existing user files

  /**
   * Saves the JSONObject of the current game configuration to Firebase
   * @param vanillaGame, current vanillaGame
   * @throws JacksonUtilityException
   * @throws FirebaseException
   * @throws UnsupportedEncodingException
   */
  public void saveObject(VanillaGame vanillaGame)
      throws JacksonUtilityException, FirebaseException, UnsupportedEncodingException {
    myVanillaGame = vanillaGame;
    builder = new JSONConfigObjectBuilder(myVanillaGame);
    object = builder.setConfig();

    System.out.println(counter);
    userObjectName = "user-file-" + String.valueOf(counter);


    Map<String, Object> objectMap = new HashMap<>();

    objectMap.put(userObjectName, object);
    firebase.put(userObjectName, object.toMap());


    //configRef.child("test0List").child("item1").setValueAsync(true);
    //configRef.push().setValue(object, (databaseError, databaseReference) -> {
     // if (databaseError != null) {
     //   System.out.println("SAVE FAILED :(");
     // }
    //});

  }

}
