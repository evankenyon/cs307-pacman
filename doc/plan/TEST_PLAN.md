# Test Plan

## Specific strategies to make APIs easily testable

- Use useful parameters and return values
    - Avoid void methods if possible to make design more easily testable through unit testing.
- smaller classes and methods
    - For easier debugging and pinpointing where the faulty line could be.
- throw informative exceptions
    - Never just print stack traces.
    - Attempt to fix internally using try/catch trees first.
    - Through UI if it’s something that can’t be fixed internally, providing suggestions to the user
      as to how they could fix it.

## Test scenarios for project features

### LOAD GAME FILE

- A user tries to load a game file that is invalid according to our parser
    - The expected outcome is that the parser catches it and triggers a view popup telling the user
      that the game file was invalid and which check it failed
    - Deleting the popup should allow the user to input another file to try again.
    - Our design supports testing for it by checking if the popup occurs after trying to upload an
      invalid file

### PAC MAN ENCOUNTERS A GHOST

- The game type is vanilla, and the pac man runs into a ghost, with at least one life left
    - The expected outcome is that the pac man loses a life, and the pacman is reset to the center
    - Our design supports testing for it by checking the values of the pac man’s remaining-life
      count to see if it decrements after he runs into a ghost, and by checking the pac man’s
      location to see if he is moved back to center position when he regenerates
- The game type is vanilla, and the pac man runs into a ghost, with no lives left
    - The expected outcome is that the pac man loses a life, dies, and the game ends with the "You
      Lose” popup.
    - Our design supports testing for it by checking if the remaining lives left decrements to zero
      when the pac man runs into a ghost, and if the game ends, and if the "You Lose” popup occurs
- The game type is the version where the user controls the ghost
    - The expected outcome is that the user gains points by eating pacmen or eating pacmen quickly (
      more points for shorter pac man lives)
    - Our design supports testing for it by checking if the points increments when the ghost runs
      into the pacman and if the pacman dies and regenerates in the center position

### PAC MAN EATS A DOT

- Pac man encounters a dot on the screen and overlaps with its position. The expected outcome is
  that the dot disappears from the view. This is testable in two ways: the dot disappears and the
  current score increases.
- Pac man encounters a dot on the screen and does not overlap with its position. The expected
  outcome is that the dot will not disappear from the UI because pacman’s coordinates are not the
  same as the dot’s coordinates.

### PAC MAN MOVEMENT

- Pac man needs to be able to move according to user input.
- The test scenarios involve some user pressing the arrow keys on the keyboard and checking whether
  Pac man moves to the desired cell in the UI.
- Pressing other keys that aren’t programmed while gameplay is occurring should not affect anything
  in the game. This is testable by inputting other keys between the arrow keys and confirming that
  pacman is only moving according to the arrow keys in the UI.  

