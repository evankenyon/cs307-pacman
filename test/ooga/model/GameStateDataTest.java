package ooga.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateDataTest {

  private GameStateData myData;
  private Map<String, List<Position>> myWallMap;
  private Map<String, Boolean> myPelletInfo;

  @BeforeEach
  void setUp() throws IOException {
    myWallMap = createWallMap();
    myData = new GameStateData();
    myPelletInfo = new HashMap<>();
    myPelletInfo.put("Dot", Boolean.TRUE);
    myData.initialize(myWallMap, myPelletInfo);
  }

  private Map<String, List<Position>> createWallMap() {
    Map<String, List<Position>> temp = new HashMap<>();

    temp.put("Wall", new ArrayList<>());
    for (int x = 0; x < 5; x++) {
      temp.get("Wall").add(new Position(x, 0));
      temp.get("Wall").add(new Position(x, 2));
    }
    temp.get("Wall").add(new Position(0, 1));
    temp.get("Wall").add(new Position(4, 1));

    temp.put("Pacman", new ArrayList<>());
    temp.get("Pacman").add(new Position(1, 1));

    temp.put("Dot", new ArrayList<>());
    temp.get("Dot").add(new Position(2, 1));

    temp.put("Ghost", new ArrayList<>());
    temp.get("Ghost").add(new Position(3, 1));
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
//    ["Wall","Pacman","Dot","Ghost", "Wall"],
//    ["Wall","Wall","Wall","Wall","Wall"]
//  ]
//  }


  @Test
  void testInitialize() {
    Assertions.assertEquals(1, myData.getFoodLeft());
    Assertions.assertEquals(0, myData.getMyGhostScore());
    Assertions.assertEquals(0, myData.getMyPacScore());
    Assertions.assertEquals(1, myData.getAgents().get(0).getPosition().getCoords()[0]);
    Assertions.assertEquals(1, myData.getAgents().get(0).getPosition().getCoords()[1]);
    Assertions.assertEquals(3, myData.getAgents().get(1).getPosition().getCoords()[0]);
    Assertions.assertEquals(1, myData.getAgents().get(1).getPosition().getCoords()[1]);
  }

  @Test
  void testWallMap() {
    System.out.println("HHH");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 5; j++) {
        System.out.print(myData.isWall(j, i));
      }
      System.out.println();
    }
    for (int i = 0; i < 5; i++) {
      System.out.println(i);
      Assertions.assertEquals(true, myData.isWall(i, 0));
      Assertions.assertEquals(true, myData.isWall(i, 2));
    }
    Assertions.assertEquals(true, myData.isWall(0, 1));
    Assertions.assertEquals(true, myData.isWall(4, 1));
    Assertions.assertEquals(false, myData.isWall(1, 1));
    Assertions.assertEquals(false, myData.isWall(2, 1));
    Assertions.assertEquals(false, myData.isWall(3, 1));

  }
}
