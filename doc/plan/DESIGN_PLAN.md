## View API Description:

### Overview:

* View classes call on the controller, which calls on the model classes to get necessary information
  and data to be displayed on the screen.

### Classes:

Board

* PacManBoard - creates the actual square with dots, walls, ghosts, and pac-man
    * update(): method that iterates through each space in the Grid and updates each in the view
        * Walls stay as walls but dots, pac man, ghosts, fruit may change
        * Uses a Consumer for the backend to notify the front end when a cell in the grid changes
          state. Then, a method in front is called to update that spot in the Grid.

|PacManBoard| |
|---|---|
|void initiateBoard(Model model) | Model |
|void iterateGrid(Grid grid) | ModelGrid |
|void updateCell(x,y, status) | |
|void getStatus(x, y) | |
| | |

* Agent (abstract) - hierarchy that creates the different agents that go on the PacManBoard

|Agent| PacManAgent, DotAgent, FruitAgent, GhostAgent |
|---|---|
|void move()| ModelAgent |
|void update(x,y, status) | |
| | |

* PacManAgent: creates the PacMan that is put on the PacManBoard to be displayed

|PacManAgent| extends Agent |
|---|---|
| void move() | ModelPacMan |
| void killPacMan() | |
| | |

* DotAgent: creates the dots that are scattered throughout the screen
    * Method called to change the size of the dot so you can have large and small dots

  |DotAgent| extends Agent |
                                        |---|---|
  |void changeSize(int newSize)|  |
  | | |

* FruitAgent: creates the fruit power ups that are on the board

  |FruitAgent| extends Agent |
                                        |---|---|
  | | |

* GhostAgent: creates the Ghost that goes on the screen

  |GhostAgent| extends Agent |
                                        |---|---|
  |void move()| ModelGhost |
  |void update(x,y, status) | |
  |void getStatus(x, y) | |
  | | |

Scene and Surrounding Controls

* Play - button - Starts the game motions (step function?)
* Pause - button - Pauses the game motions
* Save game - button - Lets the user save the game state to a file name and location of their choice
* Load game - button - Lets the user load a game file of their choice
* Language - drop-down - Sets the language of the display
* View mode - drop-down - Sets the styling of the display

|SceneView||
|---|---|
|void makeButtonPanel()||
|void makeGameScreen()||
|void statsPopup()||
|void savePopup()||
|void loadPopup()||
|void errorPopup()|
|void youWin()||
|void youLose()||

### Example:

```java
public interface Viewer {

  void initiateBoard(Model model);

  void updateCell(int x, int y, int state);

  void makeGameScreen();

  void makeButtonPanel();

}
```

### Details:

* The API takes in a ModelGame from the back end and creates a screen with buttons and a Pac Man
  Board with the correct initial locations given from the data file.
* Then, Consumers are used so the back end calls a particular method in the front end (updateCell())
  to update each cell in the front end depending on the actions of the Pac Man in the back end.
* The API allows the user to create the screen and buttons as well as initiate the board and update
  a cell.

### Considerations:

* The main issue that needs to be solved is how to implement the Consumer in the front end. The back
  end will use this to call on a front end method when a change occurs in the back end.
    * This eliminates the need of iterating over every cell in the grid.
* Additionally, there may need to be additional public methods in the Viewer API for changing the
  functionality of the example games beyond ghost control and Super Pac Man.

## Engine API Description:

### Overview:

* Framework of general classes to support any kind of game of Pacman.

### Module Organization:

#### Overall Hierarchy:

##### Game Interface

- public void setupBoard()
    - sets up board holding specific agents in locations according to necessary input from Data
      public
- void step()
    - drives updates of board positions of agents, and score updates.
- public boolean isWin()
    - checks for win condition as determined by Data
- public boolean isLoss()
    - checks for loss condition as determined by Data

##### Board Interface

- BasicBoard(Pacman + dots)
- GhostBoard(Pacman + ghosts + dots)
- NormalBoard(Pacman + ghosts + dots + fruit)
- SuperBoard(Pacman + ghosts + keys + doors + fruit + super pellet)

##### Agent Interface

- public void setPosition(int x, int y)
- public int getXPosition() returns x coordinate public int getYPosition() returns y coordinate

##### Controllable Interface

- public void setDirection(String direction)

##### Movement Interface

- public void setMovementAlgorithm(String algorithm)
    - sets a particular automatic movement algorithm, which will be in private methods detailing its
      rules.

##### Consumable Interface

- public void consume()
    - sets consumed instance variable to true
- private void reaction()
    - reaction to being consumed, be it to disappear or some alternate.
- private int applyEffects() returns 0 on success, -1 on failure.
    - For example, this can concern Pacman, who becomes invincible when consuming a power up, or
      when a fruit is consumed, the score should increase by some fixed amount.

### Classes:

Classes are able to implement any number of necessary interfaces, depending on what kind of game
genre is instantiated. Defined below are the classes that would need to be assembled together to
create each game type. The individual methods for each class aren’t specified because they would be
the ones that the interface requires them to implement.

- **VanillaPacman Game**
    - VanillaPacman implements Controllable, Consumable
    - VanillaGhost implements Movement, Consumable
    - VanillaFruit implements Consumable
    - VanillaDot implements Consumable
    - VanillaPowerup implements Consumable
- **GhostPacman Game**
    - GhostPlayer implements Controllable, Consumable
    - AutomatedPacman implements Movement, Consumable
    - VanillaFruit
    - VanillaDot
    - VanillaPowerup
- **SuperPacman Game**
    - VanillaPacman
    - VanillaGhost
    - Door implements Consumable
    - SuperFruit implements Consumable
    - Key implements Consumable
    - SuperPellet implements Consumable

### Details:

- This API provides the service of doing the necessary logical calculations for the interactions
  between agents on the board.
- The placement of agents will be determined by the Data API through the initial map input, and
  every interaction from then on will be determined by the engine.
- The engine will also hold statistical state intrinsic to each game instance, such as current score
  and lives.
- Through this design, we hope to be able to be extremely flexible, allowing us to potentially
  create more additional game functionalities than we have already planned if we wanted to.

### Considerations:

- The goal of this design is so that we can create any sort of agent we need for each kind of game
  through piecing together interfaces, instead of having a game type in the data file, and
  instantiating that game type particularly, which is a relatively stiff implementation. In this
  way, Data can assemble together a game using the Engine API, maximizing flexibility.
- That means that beyond just implementing the needed classes for each category, we need to figure
  out a way with Data to correctly instantiate each class depending on what the parsed data is. 


### Data API Description

### Overview 
Classes that read configuration files, assets, and preferences to error check and assemble the
correct combination of engine classes.

### Classes:
EngineAssembler - responsible for instantiating the correct combination of engine classes
public void startGame(File file)
This will call jsonParser’s add consumer methods and then its uploadFile method, which will then update all of the consumers so that EngineAssembler has all of the required information to set up the Game model
jsonParser - responsible for parsing json game files (for all game types) into a usable format to set up a game
public void addPlayerConsumer(Consumer<String> consumer)
public void addNumLivesConsumer(Consumer<Integer> consumer)
public void addDifficultyLevelConsumer(Consumer<Integer> consumer)
public void addWallMapConsumer(Consumer<List<List<String>>> consumer)
public void addOriginalFileConsumer(Consumer<File> file)
public void uploadFile(File file)
jsonGenerator - responsible for saving the states of an existing (paused) game as a json file that can be used to start a new game with those states
Public void saveGame(Game game)
scoreTracker - keeps track of scores, lives, and other stats and offers access to these stats by saving them to json file
public void eatableObjectEaten(EatableObject eatableObject)
public void playerDied()
playerController - listens for user keystrokes and controls player’s movements/actions accordingly
public void movePlayer() 

### Considerations 
Which states users are allowed to change (if files are inputted with changes to these states then this will lead to exceptions)
States that users cannot change:
Number of rows and columns in a map
….
Indicators of certain game types (such as whether player is Pac Man or ghost) 

###Example
Parse in example3.json, set up Vanilla Pac Man game according to configuration. Play game. Save 
final game score and states as json files.