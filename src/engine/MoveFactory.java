package ooga.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.exceptions.ReflectionException;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.moves.Move;

/**
 * Class for generating moves to be executed on the agent.
 */
public class MoveFactory {

  private static final String MOVE_PACKAGE_PATH = "ooga.gamelogic.moves.%s";
  private static final String NEW_MOVE_ERROR_STRING = "new move";

  /**
   * Method that uses reflection to generate the correct move object when given the current move to
   * execute on the object.
   *
   * @param moveToExecute the move we want to execute on the object
   * @param boundaryList  the boundaries we want to check when applying the move
   * @return the newly created move object
   * @throws ReflectionException indicating that there was an error in the reflection for creating
   *                             the move
   */
  public Move generateMove(String moveToExecute, List<Boundary> boundaryList)
      throws ReflectionException {
    Move newMove = null;
    try {
      newMove = (Move) Class.forName(String.format(MOVE_PACKAGE_PATH, moveToExecute))
          .getConstructor(List.class).newInstance(boundaryList);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
        | NoSuchMethodException | ClassNotFoundException e) {
      throw new ReflectionException(NEW_MOVE_ERROR_STRING);
    }
    return newMove;
  }
}
