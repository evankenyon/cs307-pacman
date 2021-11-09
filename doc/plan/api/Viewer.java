public interface Viewer {

  void initiateBoard(Model model);

  void updateCell(int x, int y, int state);

  void makeGameScreen();

  void makeButtonPanel();

}