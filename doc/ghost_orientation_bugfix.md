## Description

When a ghost was created, it's orientation was set to NULL instead of right, which resulted in the
image not being found and the ghost disappearing from the screen.

## Expected Behavior

We expect the ghost orientation to be right originally and the ghost to not disappear

## Current Behavior

Right now the ghost orientation is NULL, so the image for the ghost is not found and the ghost
disappears on the first step.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. load a game with a ghost
1. click play button
1. watch ghost disappear

## Failure Logs

n/a

## Hypothesis for Fixing the Bug

* Add back in getPosition().setDirection("right") in the ghost constructor
  * This used to be there but it accidentally got deleted or lost in a git merge
* The testGhostOrientation test in GhostTest shows this error.