package ooga.controller.IO;

//@Deprecated
//public record User(String username) {
//
//}

//@Deprecated
//public record User(String username, String imagePath, int highScore, int wins, int losses, String[] favorites) {
//
//}

public record User(String username, String password, String imagePath, int highScore, int wins, int losses, String[] favorites) {

}
