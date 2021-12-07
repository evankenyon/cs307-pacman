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
 * Purpose: Represent a user profile
 * Example: This object returned by login in ProfileGenerator with all of the user's information
 *
 * @author Evan Kenyon
 */
public record User(String username, String password, String imagePath, int highScore, int wins, int losses, String[] favorites) {

}
