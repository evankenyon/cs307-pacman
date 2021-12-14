## Description

Right now the number of hearts (lives) in the view do not update correctly. When the pac man is
killed sometimes hearts are added and sometimes they are removed in random combinations.

## Expected Behavior

I expect the number of hearts to decrease by one each time the pacman is killed.

## Current Behavior

Hearts are randomly added and removed and sometimes the label "PAC LIVES:" is changed.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. Run the program with any file that has a ghost (e.g. test_implementation.json).
2. Click play and don't move the pac man. Wait until the ghost kills the pac man.
3. The number of lives in the top left will be messed up and not reflect the current number of
   lives.

## Failure Logs

Number of lives is correct on the back end (as shown with the print statement in the
updateLivesDisplay method in TopView), so there is a bug in the view implementation of the lives.

## Hypothesis for Fixing the Bug

The test asserts that the number of elements in the lifeDisplay HBox equals number of original lives
minus 1 (for 1 death) plus the node for PAC label and other node for LIVES: label. This is done by
first running the simulation and waiting for the ghost to kill the pac man and update the lives.

This issue will be fixed by clearing the lifeDisplay HBox and creating a for loop to repeat adding a
heart for each life instead of trying to remove the last element. Additionally, single-heart images
will be used instead of images with pre-set numbers of hearts. 