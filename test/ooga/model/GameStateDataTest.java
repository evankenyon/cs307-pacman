package ooga.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.IO.JsonParser;
import ooga.model.agents.consumables.pellet;
import ooga.model.agents.players.Pacman;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateDataTest {

  private GameStateData myData;
  private Map<String, List<Position>> myWallMap;
  private Map<String, boolean> myPelletInfo;

  @BeforeEach
  void setUp() throws IOException {
    myWallMap = createWallMap();
    myData = new GameStateData();
  }

  private Map<String, List<Position>> createWallMap() {
    Map<String, List<Position>> temp  = new HashMap<>();

    temp.put("Wall", new ArrayList<>());
    for(int x = 0; x < 5; x++) {
      temp.get("Wall").add(new Position(x, 0));
      temp.get("Wall").add(new Position(x, 2));
    }
    temp.get("Wall").add(new Position(0, 1));
    temp.get("Wall").add(new Position(4,  1));

    temp.put("Pacman", new ArrayList<>());
    temp.get("Pacman").add(new Position(1, 1));

    temp.put("Dot", new ArrayList<>());
    temp.get("Dot").add(new Position(2, 1));

    temp.put("Pinky", new ArrayList<>());
    temp.get("Pinky").add(new Position(3, 1));
    return temp;
  }


//  {
//    "Title":"Test",
//      "Player":"Pacman",
//      "RequiredPellets":["Dot"],
//    "OptionalPellets":["Fruit"],
//    "PowerUps":[],
//    "NumberOfLives":3,
//      "OpponentTypes":["Inky", "Pinky", "Clyde"],
//    "Difficulty-Level":2,
//      "WallMap":[
//    ["Wall", "Wall", "Wall","Wall", "Wall"],
//    ["Wall","Pacman","Dot","Pinky", "Wall"],
//    ["Wall","Wall","Wall","Wall","Wall"]
//  ]
//  }


  @Test
  void testInitiliaze() {
    myData.initialize(myWallMap, myPelletInfo);
  }
}
