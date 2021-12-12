## Description
When saving locally, user file is not being created. 

## Expected Behavior
A file should be created under user files.

## Current Behavior
When .savegame() is called on a GameSaver object, a new file is not created under user_files.

## Steps to Reproduce
1. Build a Map<String, List<Position>> for the wallMap. 
2. Build a Map<String, Boolean> for the pelletInfo. 
3. Create a new GameData object using the two maps above.
4. Create a new GameEngine object using this new GameData object.
5. Create a new GameSaver object using this new GameEngine object.
6. Call .saveGame() on this new GameSaver object.

## Failure Logs
java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value 
of "java.util.Map.get(Object)" is null

## Hypothesis for Fixing the Bug
The test that should identify this bug should call .saveGame() on a GameSaver object initialized 
with a GameEngine object. Then, the bug is identified if no new file appears under user_files. 

The code fix I believe will solve this issue is fixing how the filepath is created in 
GameSaver.java.
