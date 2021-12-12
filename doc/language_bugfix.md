## Description

When a game is created from the GameStartupPanel, the language is automatically set to English, not
the selected language.

## Expected Behavior

The language in the MainView game should change to the selected language.

## Current Behavior

The language is always English no matter what the user selects

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. load a game, select a view mode, and select a non-English language in the GameStartupPanel
1. click play button
1. Notice the language is English even though English wasn't selected

## Failure Logs

n/a

## Hypothesis for Fixing the Bug

Create a new Controller for each new MainView so the file is loaded and the UserPreference is
created with the selected language, not default English.