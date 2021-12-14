## Description

After a game is completed (won/lost), and a new game is loaded from the GameStartupPanel WITHOUT
clicking "Play Again" on the WinLossPopup, the animation stays paused from the WinLossPopup and the
new game won't do anything.

## Expected Behavior

We expect the new game's animation to be running and game to work.

## Current Behavior

Right now nothing moves on the new game even after the play button is hit since the animation is
paused.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. load an easy to win game from the GameStartupPanel
2. win the game to get the notification and DO NOT press "Play Again"
3. load a new game from the GameStartupPanel
4. click on the play button and notice nothing moves

## Failure Logs

n/a

## Hypothesis for Fixing the Bug

* To start the game, the user must click the play button, so in the pauseOrResume() method in
  Controller, which is called when the play button is pressed, add a line to check if the animation
  is paused and resume the animation if it paused.