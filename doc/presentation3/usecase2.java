public class usecase2 {
  public static final String LANGUAGE = "English";
  public static final String VIEW_MODE = "Dark";

  public void start(Stage stage) {
  {
    Controller controller = new Controller(LANGUAGE, stage, VIEW_MODE);
    // Calls public Set<String> getFileNames() in FirebaseReader
    Set<String> fileNames = controller.getFirebaseFilenames();
    List<String> files = new ArrayList<>(fileNames);
    // Calls public JSONObject getFile(String fileName), then uses PreferencesParser and/or
    // JsonParser to parse the necessary information to generate a UserPreferences record
    UserPreferences userPreferences = controller.uploadFirebaseFile(files.get(0));
    BoardView boardView = new BoardView(controller.getVanillaGame(), controller, userPreferences);
    FirebaseWriter writer = new FirebaseWriter(Controller.getVanillaGame(), "test_user_file");
    // Calls public JSONObject setConfig() in JSONConfigObjectUser
    writer.saveObject();
  }
}