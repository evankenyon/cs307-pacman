## Description

When saving to Firebase, if there are no RequiredPellets, then the RequiredPellets key will not save
to our database. Similarly, if there are no OptionalPellets, then the OptionalPellets key will not 
save to our database.

## Expected Behavior
The RequiredPellets and OptionalPellets keys should both save to our database even if there are no 
required pellets or optional pellets. 

## Current Behavior
When there are no RequiredPellets and/or OptionalPellets specified in a configuration file, 
FirebaseWriter does not save a RequiredPellets and/or OptionalPellets JSON key to our Firebase 
database because Firebase will not save keys with null values. 

## Steps to Reproduce
Provide detailed steps for reproducing the issue.
1. Write a configuration file with an empty JSOnArray as the value of the OptionalPellets key. 
2. Save this configuration file to firebase by making a new FirebaseWriter object and calling 
.saveObject() on it. 
3. When you go to check the firebase database, you should see that there is no
OptionalPellets key value pair in the database. 
4. Try opening this file by making a new FirebaseReader object and calling .getFile() on it. This
should result in an error indicating that OptionalPellets was not found (see below).

## Failure Logs
org.json.JSONException: JSONObject["OptionalPellets"] not found.

## Hypothesis for Fixing the Bug
The test that should identify this bug should build a GameData object consisting of a configuration 
that has no pellets listed in the OptionalPellets JSONArray, save this configuration to firebase, 
and finally try to read it using FirebaseReader.

The code fix I believe will solve this issue is having FirebaseReader and/or JSONParser handle the 
case where one of these 2 JSONArrays is empty and fill in this missing key incoming from the 
firebase database as an empty JSONArray rather than throwing an error.
