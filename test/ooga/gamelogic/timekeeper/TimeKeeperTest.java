package ooga.gamelogic.timekeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Class to test the functionality of the timekeeper class
 *
 * @author Kyle White
 */
class TimeKeeperTest {

  @Test
  void update() {
    TimeKeeper timer = new TimeKeeper(100, -10);
    assertEquals(100, timer.getTime());
    timer.setTime(50);
    assertEquals(50, timer.getTime());
    timer.update();
    assertEquals(40, timer.getTime());
    timer.update();
    assertEquals(30, timer.getTime());
    timer.setIncrement(-30);
    timer.update();
    assertEquals(0, timer.getTime());
    timer.update();
    assertEquals(0, timer.getTime());
    timer.reset();
    assertEquals(100, timer.getTime());
  }

}