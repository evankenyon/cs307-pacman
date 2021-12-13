## Names of all people who worked on the project:

* Evan Kenyon (ek168)
* Dania Fernandez
* Dane Erickson (dte12)
* Michelle Zhang (xmz)
* Kat Cottrell
* Asher Early

### Each person's role in developing the project:

* **Evan Kenyon**
    * I worked on data, specifically, anything to do with the logic for reading in a file (locally
      from a starting config, locally from a preferences file, and from the firebase database),
      which included JsonParser, FirebaseReader, and PreferencesParser, the logic for profiles as
      well as the outline for the profile info frontend view and its edit forms, and most of the
      Controller. I also created both of the factories that the backend used for assembling
* **Dania Fernandez**
    * I worked on the data side of the project, mainly on saving games into configuration files.
      This consisted of the JSONConfigObjectBuilder, GameSaver, and FirebaseWriter classes. The
      first of these classes processed the current game into a JSON object, and the latter two saved
      this object locally and to a database, respectively.
* **Dane Erickson**
    * I worked on the view side of the project, particularly connecting the view and model. I
      created consumers to connect each agent in the view with the corresponding agent in the model
      and displayed all the items on the Pacman Board. I also created the functionality of player
      profiles and uploading files in the view and developed the functionality of the notification
      popups and some other buttons throughout the view.
* **Katherine Cottrell**
    * I also worked on the view side of the project. My focus was on the views other than the actual
      gameboard: I formatted the login window, startup window, main game window, and win/loss
      windows, as well as other windows with less formatting. I implemented several of the buttons
      and handlers and hooked things up to the controller, and implemented the languages and view
      modes in CSS. I also designed all the graphics, like buttons, pac men, ghosts, fruits, etc.
* **Michelle Zhang**
    * I worked mainly on the Engine. I implemented agent movement schemes, win/loss schemes, as well
      as helped ideate and implement general API design for the Game, the Board, and the State.
      Helped debug general engine/movement related issues, as well as connecting information coming
      from the Controller from the JSON file.

### What are the project's design goals, specifically what kinds of new features did you want to make easy to add:

* The overall design goal of this project was to maximize flexibility in order to be able to allow
  for extensive game variations that did not require breaking the open-close principle. One of the
  main ways we did this was by formatting our JSON configuration files in such a way that no game
  type ever had to be specified; the user specifies the game type simply by the values they set the
  required JSON keys to. For instance, if they want to play a version of the game where they play as
  a ghost rather than as Pacman, they simply set the “Player” JSON key to “Ghost”. This also allowed
  us to have just one JSON parser, rather than having a different parser for every game type and
  consequently requiring a new JSON parser to be written if someone wanted to add a new game type.
  Our approach also makes it easier to add newly-designed game features, such as power-ups. For
  example, once the new powerup has been implemented on the backend, all that needs to be done is
  add this newly defined powerup to one of the pellet arrays in the configuration file and add it to
  the required properties files.
* Using JSON for our configuration files also facilitates adding new ways of saving them. Once a
  JSON object is created from the current game configuration using JSONConfigObjectBuilder, this
  object can be saved by any save implementation. For instance, our current project uses such
  objects to save both locally and remotely to a database.
* Our other main design goal was to simplify the points of connection between our view, model, and
  controller as much as possible. One way we achieved this was by using consumers. Once a consumer
  was implemented for an element on the backend, it was packaged neatly for the frontend to
  implement on their end, while also allowing us to avoid use of getters and setters in this area.
  Each time the Agent object changed in the model, it would call its consumer with .accept() to
  update that Agent in the view.

### Describe the high-level design of your project, focusing on the purpose and interaction of the core classes:

The project is separated into data, model, and view.

* Data: Once the JSON file is read in, it is sent to the data side to parse the file. First, the
  Controller creates a JSONObject from the uploaded file and calls JsonParser.parseJSON on that
  JSONObject. If the file is uploaded from Firebase, the Controller calls on the FirebaseReader to
  read and send the file to JsonParser. A UserPreferences record is created from the parsed
  information for the view A GameEngine object is created with a GameData record with information
  from the file for the model
* Model: The GameEngine constructor makes a new GameBoard, which holds all the Agents for the game.
  The GameBoard creates a GameState object, which has information about the whole game, so ghosts
  can decide where to move and win/loss statuses can be checked. The GameState constructor creates a
  GameStateData object, which holds all the data for the game status. GameState provides methods to
  interact with the data in GameStateData. The GameStateData calls on the AgentFactory to create the
  Agents from the data files and give them their state and position. Each Agent is assigned a
  movement strategy in the Agent classes. Static agents get a static movement strategy and movable
  agents get a movable movement strategy. The movement strategy is called in GameState There is an
  Agent class for each Agent type (Pacman, Ghost, Pellet, Wall, etc.)
  The Controller calls step, which calls the step method in GameEngine to move the items, check
  collisions, and check if the game is over.
* View: A MainView is created after the file is uploaded, which is a BorderPane with a TopView,
  BoardView, and BottomView. The TopView displays the score and lives, which are updated with
  consumers connected to the model. The BottomView holds buttons to restart, save game, and get
  stats, as well as some simulation controls. The BoardView is created using the WallMap from the
  UserPreferences and using reflection to create an AgentView at each spot in the board. Each
  AgentView object uses a consumer to connect the view agent to the model agent so the view agent
  automatically changes when the model agent changes and calls update on the consumer. There are
  other notification popups in the view for different situations such as win/loss at the end of the
  game.

### What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features:

Below are a list of assumptions that we make, which affect features to varying degrees:

* Movement is discrete, so agents only move from coordinate to coordinate. This is an assumption
  modeled after the original Pacman game, which does not allow you to move “continuously” by pixel.
  This means that Pacman would never miss a tunnel because it decided to turn too early and hit a
  corner, or something like that.
* That someone would only reasonably want to play one game at a time. We made the observation that
  it doesn’t make sense for someone to have multiple games of Pacman open at the same time. In fact,
  for most games of that format, one usually only plays one game from beginning to end and decides
  to either replay the same game or reload another one, which is the interpretation we took and
  implemented.
* In the general game, we assume that the only collision interactions that can happen are between
  pacman and ghosts, and pacman and foods. This can be seen in the checkCollisions method. This
  means that to add any more collision interactions between other agents will involve adding extra
  code to the checkCollisions method in the Board.
* We implemented movement and collision order in a way that makes it so that every agent makes a
  move first, and then the collisions between the agents are evaluated and the effects are applied.
  This is the alternative to the other option, which would be to move one agent and evaluate its
  collisions and effects immediately, and do that for every single agent on the board. This created
  some bugs that we had to address, such as agents moving to collision positions and then the second
  agent moving away, which would result in no collision detected since we run that algorithm after
  everyone has already moved.

  ### Address significant differences between the original plan and the final version of the project:

  The two most significant differences that come to mind are that we decided to implement user
  profiles and firebase read/writes instead of artificial players and game data producer and viewer.
  These led to significant differences from our original API plans, as the features we implemented
  were completely different. There were a few reasons that we made these changes. First, the
  original features we wanted to implement would have required more backend work, and during our
  last sprint, the backend subteam had a significant amount of core functionality to finish. So, we
  did not want to burden them, especially since the data subteam was mostly done with core
  functionality, so they had time to work on the basic/challenging features. Second, the features we
  decided to go with were less ambiguous. For example, having an artificial player in Pacman could
  have meant multiple things, and we did not design our program with artificial players in mind (
  except for the ghosts). Finally, we had more experience with the features we decided to go with. A
  few members of our team had worked with REST APIs and json before, so it made the most sense based
  on our experience to work on the features that involved these things.

  These changes directly led to the most significant data and view changes in the final version of
  the project. In data, a few new classes were included (FirebaseReader, FirebaseWriter, and
  ProfileGenerator), each with new API methods that were not in the original plan in order to
  provide their desired services. Since data APIs were interfaced with through the Controller, the
  Controller’s API had to be added to in order to support interfacing with these new data APIs. The
  view now had a UserInformationView (for seeing and editing usr information) and LoginView (for
  logging in and creating users) as a result of the features we decided to implement. The Controller
  instantiated a LoginView on construction rather than a GameStartupPanel. BottomView and
  GameStartupPanel now instantiated UserInformationView, and LoginView instantiated a
  GameStartupPanel. Also, GameStartupPanel now interfaced with FirebaseReader through the Controller
  in order to get file names from the database and to tell the Controller which file from the
  database the user wanted to use. These were all quite significant changes to meet our new feature
  goals, however after the changes, our overall design remained intact. The classes we made to
  handle the features we implemented were active, and their implementation in our overall project
  did not lead to any major impacts on our design.

  On the Engine side, we made some big changes such as changing the original class structure to add
  GameState and GameStateData. The reasoning for this is that we wanted to separate the state a bit
  more from the board as it was becoming increasingly unwieldy (and just a huge class). We did that
  by creating GameState. As we kept working, we then found the need for GameStateData, which was
  born from the same reasoning: the GameState class was becoming too large. In the end, the purpose
  of GameState is to expose useful methods that make calculations on the data structures that
  GameStateData holds. Other classes which need to affect GameStateData only have access to
  GameState. One more change was making our main data structure from 2D array to a series of lists.
  This one was born from a more clear reason, which was that we were holding redundant position
  values once in the agent’s own position variable, and again in the agent’s grid index. To move an
  agent in our original flawed design, we had to update both the variable in the agent as well as
  its position in the grid. Using just lists, we were able to avoid that problem by only checking
  the variable in the agent, and even creating new functionality like checking whether all the
  required pellets have been consumed by simply checking if the required pellets list is empty.

### Describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline:

* Because there are separate classes for each Agent on the model that correspond to each Agent in
  the view, new Agents can easily be added with new functionality. One feature that could be
  implemented is adding Fruit to the game. In the model, The Fruit would be a Consumable Agent that
  implements the Consumable and Agent interface. There are some different functionalities the Fruit
  could have, but one important one is to give the pacman power ups in the super pacman game such as
  increase speed and become invincible for a time period. Because the AgentView hierarchy in the
  view is similar to the Agent hierarchy in the model, this new feature would also be easy to
  implement in the view. A new AgentView subclass would be created that extends the StationaryView
  abstract class. Then, a consumer would be created to connect the FruitView to the model Fruit.
  When the fruit is consumed, a runnable in the model would be called to make the necessary changes
  in the model. This flexible design with hierarchies for agents in the model and view makes it easy
  to add new agents.
* Additionally, the save game feature could be fully implemented in the view. Right now, the save
  game works perfectly in the back end and passes all the tests, but is not currently connected to
  the buttons in the view. The Save Game button is in the BottomView, and a ChoiceDialog popups up
  when the Save Game button is selected. The two options are to save locally or save to Firebase.
  When the user selects a save option, reflection needs to be used to call the respective methods in
  the controller to save the game as a new JSON file. Additionally, a final dialog needs to pop up
  to prompt the user to type in a name for the file to be saved. These methods are already all
  created, they just need to be connected to the buttons in the view.
* Also, finishing Ghost as player mode. Right now, when a player loses control of the Ghost when
  Pacman eats a Super Pellet, the Ghost returns to a BFS strategy, and not the Controllable
  strategy. To finish this, we would set it up using a property file similar to the end conditions
  set up. The properties file would for example say that Ghost=Controllable if ghost were a player,
  and that string from the file would be used to perform reflection within the Ghost class where the
  movement strategy is changed. 


