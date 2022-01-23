package ooga.exceptions;

/**
 * Class to notify the view when the game has ended.
 *
 * @author Kyle White
 */
public class EndGameException extends RuntimeException {

  /**
   * Create an exception based on an issue in our code.
   */
  public EndGameException(String message, Object... values) {
    super(String.format(message, values));
  }

  /**
   * Create exception based on a caught exception with a different message.
   */
  public EndGameException(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create exception based on a caught exception, with no additional message.
   */
  public EndGameException(Throwable cause) {
    super(cause);
  }

}
