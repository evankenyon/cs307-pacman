interface settingsTracker {
  public HashMap getUserInput()

  /**
   * loops through input stream to set game settings
   * @param inputStream, HashMap of user setting selections
   */
  public void setSettings(HashMap inputStream)

}