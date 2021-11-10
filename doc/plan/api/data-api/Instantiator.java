interface engineAssembler {

  /**
   * call jsonParser’s add consumer methods and then its uploadFile method, which will then update
   * all of the consumers so that EngineAssembler has all of the required information to set up
   * the Game model
   * @param file, a json Pac Man game file
   */
  public void startGame(File file)
}