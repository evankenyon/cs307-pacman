## Description

In a game involving doors and keys when the user is playing as the ghost, Pacman teleports to behind 
the doors sometimes.

## Expected Behavior

Pacman should have to eat a key before going past a door.

## Current Behavior

Pacman is teleporting behind doors without eating keys when the user is playing as the ghost.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Open up tests/preferences/SuperPacmanGhost.json as the starting user preferences (or 
tests/super_pacman_ghost.json as the starting config)
 2. Start the game by unpausing it
 3. Visually verify that Pacman teleported to behind a door

## Failure Logs

None

## Hypothesis for Fixing the Bug

I think a test that asserts that Pacman only moves one grid location over at a time (when using 
tests/preferences/SuperPacmanGhost.json as the starting file) will verify this bug (as he teleports).
I think the code that will fix this issue is code that randomly chooses a food item for Pacman to 
chase if the only optimal path for the given chosen food item is directly chasing said food item. 
That way, he will not teleport to the nearest food item if it is behind a door, and instead choose
a different one to chase.
