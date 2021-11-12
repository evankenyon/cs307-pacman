interface jsonParser {

  /**
   * allows user to choose game file to upload and parses this file into usable game
   * @param json Pac Man game file
   */
  public void uploadFile(File file);
  public void addOriginalFileConsumer(Consumer<File> file);
  public void addWallMapConsumer(Consumer<Map<String, List<Position>>> consumer);
  public void addDifficultyLevelConsumer(Consumer<Integer> consumer);
  public void addNumLivesConsumer(Consumer<Integer> consumer);
  public void addPlayerConsumer(Consumer<String> consumer);
  public void addPelletsConsumer(Consumer<Map<String, Boolean>> consumer);
}

interface jsonGenerator {

  /**
   * saves the states of an existing (paused) game as a json file that can be used to start a new
   * game with those states
   * @param game that is either paused or completed
   */
  public void saveGame(Game game)
  public void getOriginalFileConsumer(Consumer<File> file)
  public void getWallMapConsumer(Consumer<List<List<String>>> consumer)
  public void getDifficultyLevelConsumer(Consumer<Integer> consumer)
  public void getNumLivesConsumer(Consumer<Integer> consumer)
  public void getPlayerConsumer(Consumer<String> consumer)
}

