package ooga.controller.IO;

//@Deprecated
//public record User(String username) {
//
//}

//@Deprecated
//public record User(String username, String imagePath, int highScore, int wins, int losses, String[] favorites) {
//
//}

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @author Evan Kenyon
 */
public record User(String username, String password, String imagePath, int highScore, int wins, int losses, String[] favorites) {

}
