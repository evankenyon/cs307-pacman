package ooga.controller.IO;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import ooga.controller.IO.utils.JSONObjectParser;
// Decided to use this library after reading article from
// https://coderolls.com/parse-json-in-java/
import org.json.JSONArray;
import org.json.JSONObject;

public class PreferencesParser {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      JsonParser.class.getPackageName() + ".resources.";
  private static final String POSSIBLE_PREFERENCES_FILENAME = "PossiblePreferences";
  private static final String PREFERENCES_VALUES_FILENAME = "PreferencesValues";

  private JSONObject preferencesJson;
  private Map<String, String> imagePaths;
  private Map<String, List<Double>> colors;
  private File startingConfig;
  private String style;

  private ResourceBundle possiblePreferences;
  private ResourceBundle preferencesValues;

  public PreferencesParser() {
    possiblePreferences = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, POSSIBLE_PREFERENCES_FILENAME));
    preferencesValues = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PREFERENCES_VALUES_FILENAME));
    imagePaths = new HashMap<>();
    colors = new HashMap<>();
  }

  public void uploadFile(File file)
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InputMismatchException{
    preferencesJson = JSONObjectParser.parseJSONObject(file);
    checkForExtraKeys(preferencesJson.keySet());
    for (String key : preferencesJson.keySet()) {
      Method method = this.getClass()
          .getDeclaredMethod(String.format("addTo%s", possiblePreferences.getString(key)), String.class);
      method.setAccessible(true);
      try {
        method.invoke(this, key);
      } catch (InvocationTargetException e) {
        throw (InputMismatchException) e.getCause();
      }

    }
  }

  public Map<String, String> getImagePaths() {
    return imagePaths;
  }

  public Map<String, List<Double>> getColors() {
    return colors;
  }

  public String getStyle() {
    return style;
  }

  public File getStartingConfig() {
    return startingConfig;
  }

  private void checkForExtraKeys(Set<String> keySet) throws InputMismatchException {
    for (String key : keySet) {
      if (!possiblePreferences.containsKey(key)) {
        throw new InputMismatchException(
            String.format("Unexpected key %s was found in preferences file", key));
      }
    }
  }

  private void addToStartingConfig(String key) {
    startingConfig = new File(preferencesJson.getString(key));
  }

  private void addToImage(String key) {
    if(!List.of(preferencesValues.getString("Image").split(",")).contains(preferencesJson.getString(key))) {
      throw new InputMismatchException(String.format("Invalid image path %s was passed in for key %s", preferencesJson.get(key), key));
    }
    imagePaths.put(key, preferencesJson.getString(key));
  }

  private void addToColor(String key) {
    List<Double> rgb = new ArrayList<>();
    // Borrowed code for converting BigDecimal to double from
    // https://stackoverflow.com/questions/19650917/how-to-convert-bigdecimal-to-double-in-java
    preferencesJson.getJSONArray(key).iterator().forEachRemaining(color -> rgb.add(((BigDecimal) color).doubleValue()));
    for (Double color : rgb) {
      if(color > 1 || color < 0) {
        throw new InputMismatchException(String.format("Invalid rgb value of %s", color));
      }
    }
    colors.put(key, rgb);
  }

  private void addToStyle(String key) {
    if(!List.of(preferencesValues.getString("Style").split(",")).contains(preferencesJson.getString(key))) {
      throw new InputMismatchException(String.format("Invalid style %s was passed in", style));
    }
    style = preferencesJson.getString(key);
  }

}
