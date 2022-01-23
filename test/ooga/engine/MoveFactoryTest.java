package ooga.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import ooga.exceptions.ReflectionException;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.boundary.EdgeBoundary;
import ooga.gamelogic.boundary.OtherEntityBoundary;
import ooga.gamelogic.moves.DownOneCell;
import ooga.gamelogic.moves.LeftUntilBoundary;
import ooga.gamelogic.moves.Move;
import ooga.gamelogic.moves.SwapLeft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveFactoryTest {

  private MoveFactory moveFactory;
  private List<Boundary> myBoundaries;

  @BeforeEach
  void setUp() {
    moveFactory = new MoveFactory();
    myBoundaries = new ArrayList<>();
    myBoundaries.add(new EdgeBoundary());
    myBoundaries.add(new OtherEntityBoundary());

  }

  @Test
  void generateMoveLeftUntilBoundary() {
    Move m;
    try {
      m = moveFactory.generateMove("LeftUntilBoundary", myBoundaries);
    } catch (Exception e) {
      m = null;
    }
    assertEquals(m.getClass(), new LeftUntilBoundary(myBoundaries).getClass());
  }

  @Test
  void generateSwapMove() {
    Move m;
    try {
      m = moveFactory.generateMove("SwapLeft", myBoundaries);
    } catch (Exception e) {
      m = null;
    }
    assertEquals(m.getClass(), new SwapLeft(myBoundaries).getClass());
  }

  @Test
  void generateDownOneCellMove() {
    Move m;
    try {
      m = moveFactory.generateMove("DownOneCell", myBoundaries);
    } catch (Exception e) {
      m = null;
    }
    assertEquals(m.getClass(), new DownOneCell(myBoundaries).getClass());
  }

  @Test
  void invalidMovePassed() {
    assertThrows(new ReflectionException("").getClass(),
        () -> moveFactory.generateMove("Sideways", myBoundaries));
  }

  @Test
  void noMovePassed() {
    assertThrows(new ReflectionException("").getClass(),
        () -> moveFactory.generateMove("", myBoundaries));
  }
}