package ooga.controller.IO;

//@Deprecated
//public record User(String username) {
//
//}

public record User(String username, String imagePath, int highScore, int wins, int losses,
                   String[] favorites) {

}
