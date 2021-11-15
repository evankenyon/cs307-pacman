package ooga.controller.IO;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.robot.impl.KeyboardRobotImpl;

public class keyTrackerTest {

  private keyTracker tracker;
  private Component c;
  private KeyEvent e;

  @BeforeEach
  void setUp() {
    tracker = new keyTracker();
    c = new Component() {
      @Override
      public String getName() {
        return super.getName();
      }
    };
    e = new KeyEvent(c, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_LEFT, 0, 'S');
  }

  @Test
  void testForCorrectKey() {
    Assertions.assertEquals("left", tracker.getPressedKey(e);
  }

}
