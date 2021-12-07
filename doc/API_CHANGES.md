
interface Controller {
	public Map<String, List<AgentInfo>> uploadFile(File file)
	public void getInput(ArrowKey move)
}


interface playerController {
//Currently working on
public Move getInput()
public void executeMove(Move move)
}


interface engineAssembler {

 /**
  * call jsonParser’s add consumer methods and then its uploadFile method, which will then update
  * all of the consumers so that EngineAssembler has all of the required information to set up
  * the Game model
  * @param file, a json Pac Man game file
  */
 public ModelData startGame()
}


interface jsonParser {

 /**
  * allows user to choose game file to upload and parses this file into usable game
  * @param json Pac Man game file
  */

// Will only have dummy implementation by test deadline
 public void addOriginalFileConsumer(Consumer<File> file);
 public void addDifficultyLevelConsumer(Consumer<Integer> consumer);
 public void addNumLivesConsumer(Consumer<Integer> consumer);

// Basic implementation done
 public void uploadFile(File file);
 public void addPlayerConsumer(Consumer<String> consumer);
 public void addWallMapConsumer(Consumer<Map<String, List<Position>>> consumer);
 public void addPelletsConsumer(Consumer<Map<String, Boolean>> consumer);
}


// Will be worked on after test deadline
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


