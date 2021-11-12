package ooga.controller.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.io.IOUtils;
// Decided to use this library after reading article from
// https://coderolls.com/parse-json-in-java/
import org.json.JSONObject;

public class JsonParser implements JsonParserInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      JsonParser.class.getPackageName() + ".resources.";
  private static final String REQUIRED_KEYS_FILENAME = "RequiredKeys";
  private Map<String, Map<String, Integer>> test;
  private ResourceBundle requiredKeys;

  public JsonParser() {
    test = new HashMap<>();
    requiredKeys = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, REQUIRED_KEYS_FILENAME));
  }

  @Override
  public void uploadFile(File file) throws IOException, InputMismatchException {
    // Borrowed code to read in a json file from
    // https://stackoverflow.com/questions/7463414/what-s-the-best-way-to-load-a-jsonobject-from-a-json-text-file

    InputStream is = this.getClass().getClassLoader()
        .getResourceAsStream(file.getPath());
    if(is == null) {
      throw new FileNotFoundException();
    }
    String jsonTxt = IOUtils.toString(is, "UTF-8");
    JSONObject json = new JSONObject(jsonTxt);
    checkForRequiredKeys(json.keySet());
  }

  private void checkForRequiredKeys(Set<String> keySet) throws InputMismatchException{
    List<String> requiredKeysList = List.of(requiredKeys.getString("RequiredKeys").split(","));
    int keysRequired = requiredKeysList.size();
    int numKeys = keySet.size();
    if (keysRequired != numKeys) {
      throw new InputMismatchException("The uploaded file does not have enough keys");
    }
    for (String key : keySet) {
      if (!requiredKeysList.contains(key)) {
        throw new InputMismatchException(
            String.format("Unexpected key %s was found in json file", key));
      }
    }
  }
}
