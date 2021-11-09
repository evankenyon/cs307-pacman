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
