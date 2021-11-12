# BACKLOG
## General (applies to all game modes)
1. The points counter on the upper right of the screen increases as pacman interacts with dots and power ups. 
2. Start game 
   1. Player indicates which game-mode they are interested in playing 
   2. Corresponding view/model initializes for that game mode 
   3. Player is prompted to begin game by taking certain action 
3. Pause game 
   1. Player pauses the game by pressing corresponding key/clicking corresponding button 
   2. Timeline pauses 
   3. View/model remain static
4. Player moves pacman successfully to an empty cell
   1. Player presses a key corresponding to a directional movement 
   2. Corresponding direction of movement is an empty cell 
   3. Model and view update pacmans position to that cell 
5. Player moves pacman successfully to a regular dot 
   1. Player presses a key corresponding to a directional movement 
   2. Corresponding direction of movement is a cell with a regular dot 
   3. Model and view update pacmans position to that cell, removing dot from that cell, updating pacmans score +1 
6. Player moves pacman successfully to a power cell (big dot)
   1. Player presses a key corresponding to a directional movement 
   2. Corresponding direction of movement is a power cell 
   3. Model and view update pacmans position to that cell, power dot is removed from that cell 
   4. Ghost agents change to "can be eaten mode”, view is updated correspondingly 
7. Player moves pacman successfully to a fruit cell 
   1. Player presses a key corresponding to a directional movement 
   2. Corresponding direction of movement is a power cell 
   3. Model and view update pacmans position to that cell, fruit icon is removed from that cell 
   4. Model and view update pacmans position to that cell, updating pacmans score by corresponding number of points 
8. Player moves pacman unsuccessfully to a wall 
   1. Player presses a key corresponding to a directional movement 
   2. Corresponding direction of movement is a wall 
   3. Model and view update pacmans orientation to face the wall, pacman is not moved 
9. Player moves pacman successfully to a tunnel 
   1. Player presses a key corresponding to a directional movement 
   2. Corresponding direction of movement is a wall 
   3. Model and view update pacmans orientation to face the wall, pacman is not moved 
10. Player moves pacman successfully to a "consumable” ghost 
    1. Player presses a key corresponding to a directional movement 
    2. Corresponding direction of movement is a "consumable” ghost 
    3. Model and view update pacmans position to that cell, ghost is set to home 
11. Player moves successfully to an "non consumable” ghost and loses a life 
    1. Player presses a key corresponding to a directional movement 
    2. Corresponding direction of movement is a "non consumable” ghost 
    3. Model updates to reflect a lost life, resets pacman/ghost positions, reflected on the front end 
    4. Some visual signal that the player has died, like blinking or turning into a skeleton 
    5. The player’s lifecount to decrement and the lifecount display to decrement 
    6. Player respawns in original location 
12. Player loses 
    1. When the player’s life count equals zero, they lose and the game ends 
    2. The game stops 
    3. A lose display pops up, informing the player that they have lost and what their game stats were 
13. Player wins 
    1. Player eats all the circles 
    2. The game stops 
    3. A win display pops up, informing the player they have won and what their game stats were 
14. Player increases difficulty 
    1. After player wins, they may select to repeat the same difficulty, which makes a new game (with new or same layout) OR 
    2. Player increases difficulty, which creates a larger board with more walls/dead ends to make it harder to win 
15. Ghost moves in "easy” mode 
    1. Ghost initially chooses a random direction and continues moving in the same direction until it kills Pac Man, hits a wall, or hits another ghost. 
    2. Then it chooses a random direction (usually backwards) to start traveling and continues until another obstacle reached 
16. Ghost moves in "hard” mode 
    1. Ghosts become intelligent and constantly move towards Pac Man instead of moving randomly 
    2. Ghosts no longer bounce off each other and can all move along same optimized path towards Pac Man 
17. Load game 
    1. A new game is loaded with the initial statistics and positions loaded from a saved file. 
18. Save game 
    1. All current game statistics and locations are saved to a file. 
    2. The user is allowed to choose where the file is saved to and under what name. 
19. Change preferences 
    1. The user presses the settings button. 
    2. The user changes preferences and controls. 
    3. The preferences are reflected in the game. 
20. Play multiple games at the same time 
    1. The user presses a button that allows them to start up a new game in a different window. 
    2. The user is able to control that new game using 
21. High score is beaten 
    1. After the game is finished and the score is final, the program checks the final score against the all-time high score.  If the score is lower or the same, nothing.  If the score is higher:
    2. The user is notified via popup with the old high score and their high score, and can enter a name as the highest scorer. 
    3. The value for the high score of that game type in the program is updated, and the value for the name of the highest scorer of that game is updated. 
22. Max time is beaten 
    1. After the game is finished and the score is final, the program checks the playing run time and compares it to the max playing runtime. If time is lower or the same, nothing. If higher:
    2. Stats window is updated to show new max time if user clicks on the stats button 
23. Max Ghosts eaten 
    1. After the game is finished and the score is final, the program checks the number of ghosts eaten and compares it to the max number of ghosts eaten. If number is lower or the same, nothing. If higher:
    2. Stats window is updated to show new max ghosts eaten if user clicks on the stats button 
24. Instructions 
    1. If the player presses a button that says "About” or something, it pops up with instructions for how to play the game type they are playing. 
    2. Game should pause if running. 
25. Language switch 
    1. If the player selects a different language, the view should update to display all text in that language 
26. View theme / mode switch 
    1. If a player selects a new view theme/mode, the view should update to be styled in that theme/mode 
27. The user enters their name as the new high scorer and hits enter 
    1. Their new high score and winner name are saved to the program and appear as the high score and winner name next time the game is opened 
28. The user clicks the statistics button 
    1. If not already paused, the game pauses 
    2. A panel pops up with the current statistics (lives left, points accumulated, etc) as well as the all-time high scorer and score

## Regular Pac Man
No modifications from the general cases are required at this time.

## Ghost Mode Pac Man
29. Player starts controlling the ghost with a keyboard. 
    1. Pacman moves according to keystrokes. 
30. Ghost wins 
    1. Player successfully ‘kills’ pacman, causing pacman to either lose a life or causing the player to win the game if pacman has lost all his lives. 
31. Ghost loses 
    1. Pacman eats a powerup and eats the ghost. 
    2. The ghost loses a life. 
    3. If the ghost loses all 3 lives, display the game over screen. 
32. Additional Pac Men added to screen 
    1. Player adds additional Pac Men to screen to increase the difficulty of game 
    2. Computer controls all Pac Men on screen

## Super Pac Man
33. The player "eats” a power up. 
    1. Pacman becomes faster. 
    2. Pacman cannot be hurt by ghosts for a limited period of time. 
34. Pacman eats a key 
    1. Nearby door/s disappear (i.e. if two doors block a pathway to fruit/s, then both doors disappear). 
35. Pacman eats a "super pellet” 
    1. Pacman becomes invincible to ghosts. 
    2. Pacman becomes larger. 
    3. Pacman can eat doors. 
    4. Pacman becomes faster. 
36. Pacman wins 
    1. Pacman eats all the fruits. 



