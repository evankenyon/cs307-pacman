## Description

Pacman dies when he runs into doors that opened because of him eating a key. This only occurs if
there is a ghost present in the starting configuration.

## Expected Behavior

He should not die when he runs into doors that opened because of him eating a key.

## Current Behavior

Pacman dies when he runs into doors that opened because of him eating a key. This only occurs if
there is a ghost present in the starting configuration.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. Open the tests/super_pacman_bug2.json file for the starting config
2. Eat the key pellet
3. Go to the space where there was previously a door
4. Pacman will die

## Failure Logs

None

## Hypothesis for Fixing the Bug

I think that a test which checks to make sure Pacman hasn't lost any lives after he crosses an 
empty door space would show that this is failing.