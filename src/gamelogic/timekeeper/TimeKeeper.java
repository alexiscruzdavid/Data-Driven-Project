package ooga.gamelogic.timekeeper;

/**
 * Class to represent timer.
 *
 * @author Kyle White
 */
public class TimeKeeper {

  private static final int NO_TIME_LEFT = 0;
  private int myIncrement;
  private int currentTime;
  private int myTotal;

  /**
   * Constructor that takes in total time and time increment.
   *
   * @param total     total time
   * @param increment time increment
   */
  public TimeKeeper(int total, int increment) {
    myIncrement = increment;
    myTotal = total;
    currentTime = total;
  }

  /**
   * Gets the current time in the game.
   *
   * @return currentTime
   */
  public int getTime() {
    return currentTime;
  }

  /**
   * Sets current time to original total.
   */
  public void reset() {
    currentTime = myTotal;
  }

  /**
   * Sets the time in the game to a particular time.
   *
   * @param time specific time to set to
   */
  public void setTime(int time) {
    currentTime = (time < NO_TIME_LEFT) ? currentTime : time;
  }

  /**
   * Sets the amount with which to increment the timer.
   *
   * @param increment specific increment to set to
   */
  public void setIncrement(int increment) {
    myIncrement = increment;
  }

  /**
   * Updates time based on increment.
   */
  public void update() {
    currentTime =
        (currentTime + myIncrement < NO_TIME_LEFT) ? NO_TIME_LEFT : currentTime + myIncrement;
  }

}
