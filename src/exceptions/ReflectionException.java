package ooga.exceptions;

/**
 * Class to notify the user that an error has occurred that needs technical support.
 *
 * @author Kristen Angell
 */
public class ReflectionException extends RuntimeException {

  /**
   * Create an exception based on an issue in our code.
   */
  public ReflectionException(String message, Object... values) {
    super(String.format(message, values));
  }

  /**
   * Create exception based on a caught exception with a different message.
   */
  public ReflectionException(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create exception based on a caught exception, with no additional message.
   */
  public ReflectionException(Throwable cause) {
    super(cause);
  }

}
