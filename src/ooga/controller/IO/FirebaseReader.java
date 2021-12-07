package ooga.controller.IO;

import com.google.firebase.database.FirebaseDatabase;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;
import org.json.JSONObject;

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @author Evan Kenyon
 */
public class FirebaseReader {
  private FirebaseDatabase db;
  private Firebase firebase;

  /**
   * Purpose:
   * Assumptions:
   * @param firebase_baseURL
   * @throws FirebaseException
   */
  public FirebaseReader(String firebase_baseURL) throws FirebaseException {
    firebase = new Firebase(firebase_baseURL);
  }

  /**
   * Purpose:
   * Assumptions:
   * @throws FirebaseException
   */
  public FirebaseReader() throws FirebaseException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    this("https://ooga-57bdb-default-rtdb.firebaseio.com/");
  }

  /**
   * Purpose:
   * Assumptions:
   * @return
   * @throws FirebaseException
   * @throws UnsupportedEncodingException
   */
  public Set<String> getFileNames() throws FirebaseException, UnsupportedEncodingException {
    return firebase.get("").getBody().keySet();
  }

  /**
   * Purpose:
   * Assumptions:
   * @param fileName
   * @return
   * @throws FirebaseException
   * @throws UnsupportedEncodingException
   */
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
