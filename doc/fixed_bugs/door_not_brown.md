## Description

Summary of the feature's bug (without describing implementation details)

The color of a door is not changing from the default even when it is specified to in a user
preferences file.

## Expected Behavior

The color of a door is changes from the default when it is specified to in a user
preferences file.

## Current Behavior

The color of a door is not changing from the default even when it is specified to in a user
preferences file.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Open up the data/tests/preferences/SuperPacman.json file as a starting preferences file
 2. Visually confirm that there are no brown rectangles

## Failure Logs

None

## Hypothesis for Fixing the Bug

I think that a test which asserts that 40 rectangles in the BoardView when 
data/tests/preferences/SuperPacman.json is the starting file will verify this bug. I think that 
the bug will likely be fixed by changing the makeAgentViewColor method, however I am not sure what
exactly will fix it.
