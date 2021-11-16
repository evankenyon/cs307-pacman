package ooga.controller.IO;

import java.awt.Component;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class keyTrackerTest {

  private keyTracker tracker;
  private Component c;
  private KeyEvent left;
  private KeyEvent right;
  private KeyEvent up;
  private KeyEvent down;
  private KeyEvent nonArrow;

  @BeforeEach
  void setUp() {
    tracker = new keyTracker();
    c = new Component() {
      @Override
      public String getName() {
        return super.getName();
      }
    };
  }

  @Test
  void testForCorrectKey() {
    left = new KeyEvent(c, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_LEFT, 37);
    right = new KeyEvent(c, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_RIGHT, 27);
    up = new KeyEvent(c, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UP, 26);
    down = new KeyEvent(c, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_DOWN, 28);
    Assertions.assertEquals("left", tracker.getPressedKey(left));
    Assertions.assertEquals("right", tracker.getPressedKey(right));
    Assertions.assertEquals("up", tracker.getPressedKey(up));
    Assertions.assertEquals("down", tracker.getPressedKey(down));
  }

  @Test
  void testForNonArrowKey() {
    nonArrow = new KeyEvent(c, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_N, 78);
    Assertions.assertEquals("not-arrow", tracker.getPressedKey(nonArrow));
  }

}
