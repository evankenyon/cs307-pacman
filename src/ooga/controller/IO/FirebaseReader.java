package ooga.controller.IO;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;
import org.json.JSONObject;

/**
 * Purpose: This class provides an API for accessing a firebase database in order to get the files
 * available to the user and to get a JSONObject representing a starting config or preferences file
 * Dependencies: firebase4j (linked in default constructor), Set, UnsupportedEncodingException,
 * JSONObject/json-java
 * Example: Instantiate this class in a Controller and add it to a method that takes in a filename
 * in order to instantiate the model (this would be used as an intermediary step to get a JSONObject)
 * and to allow the frontend to get the files available in firebase
 *
 * @author Evan Kenyon
 */
public class FirebaseReader {
  private Firebase firebase;

  /**
   * Purpose: Construct this class with a provided firebase database
   * @param firebase_baseURL firebase database to use for getting files and filenames
   * @throws FirebaseException thrown if issue with firebase URL passed in
   */
  public FirebaseReader(String firebase_baseURL) throws FirebaseException {
    firebase = new Firebase(firebase_baseURL);
  }

  /**
   * Purpose: Construct this class with default firebase database that we created
   * @throws FirebaseException thrown if issue with our firebase database
   */
  public FirebaseReader() throws FirebaseException {
    // Borrowed code for basic setup from
    // https://github.com/bane73/firebase4j
    this("https://ooga-57bdb-default-rtdb.firebaseio.com/");
  }

  /**
   * Purpose: Get the available filenames from the firebase database
   * Assumptions: filenames are kept at root directory
   * @return Set of filenames as Strings
   * @throws FirebaseException thrown if issue with firebase database
   * @throws UnsupportedEncodingException never thrown, this is a result of bad design in the
   * library we rely on
   */
  public Set<String> getFileNames() throws FirebaseException, UnsupportedEncodingException {
    return firebase.get("").getBody().keySet();
  }

  /**
   * Purpose: Get the JSONObject representing a file in the firebase database
   * @param fileName
   * @return the JSONObject representing a file in the firebase database
   * @throws FirebaseException thrown if file is not in the database
   * @throws UnsupportedEncodingException never thrown, this is a result of bad design in the
   *    * library we rely on
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
