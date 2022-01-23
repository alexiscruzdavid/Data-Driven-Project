package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ooga.display.Display;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Class to test the functionality of the LogicController
 */
class LogicControllerTest extends DukeApplicationTest {

  @Test
  void loadNewCheckGetMetadata() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage s = new Stage();
        Display disp = new Display(s, Paint.valueOf("green"), "English");
        LogicController controller = new LogicController();
        controller.setDisplay(disp);
        DataController dataController = new DataController();

        try {
          controller.createGameEngine(
              dataController.getGameConfigurationData(new File("data/config/2048.sim")));
        } catch (FileNotFoundException | InvalidParameterException e) {
          e.printStackTrace();
        }
        assertEquals("AllCellsOnBoard", controller.getGameData().getActiveAgentCreator());
      }
    });
  }

  @Test
  void checkInitialScore() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage s = new Stage();
        Display disp = new Display(s, Paint.valueOf("green"), "English");
        LogicController controller = new LogicController();
        controller.setDisplay(disp);
        DataController dataController = new DataController();

        try {
          controller.createGameEngine(
              dataController.getGameConfigurationData(new File("data/config/2048.sim")));
        } catch (FileNotFoundException | InvalidParameterException e) {
          e.printStackTrace();
        }
        assertEquals(0, controller.getCurrentScore());
      }
    });
  }

  @Test
  void checkGameEngineCreated() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage s = new Stage();
        Display disp = new Display(s, Paint.valueOf("green"), "English");
        LogicController controller = new LogicController();
        controller.setDisplay(disp);
        DataController dataController = new DataController();

        try {
          controller.createGameEngine(
              dataController.getGameConfigurationData(new File("data/config/2048.sim")));
        } catch (FileNotFoundException | InvalidParameterException e) {
          e.printStackTrace();
        }
        assertNotNull(controller.getGameEngine());
        controller.restartGame();
      }
    });
  }

  @Test
  void updateScene() {
    new Runnable() {
      @Override
      public void run() {
        Stage s = new Stage();
        Display disp = new Display(s, Paint.valueOf("green"), "English");
        LogicController controller = new LogicController();
        controller.setDisplay(disp);
        boolean successfulUpdate;
        DataController dataController = new DataController();
        try {
          controller.createGameEngine(
              dataController.getGameConfigurationData(new File("data/config/2048.sim")));
          controller.setupViewWithInformation();
          successfulUpdate = controller.step();
        } catch (FileNotFoundException | InvalidParameterException | ArithmeticException e) {
          successfulUpdate = false;
          e.printStackTrace();
        }
        assertEquals(true, successfulUpdate);
      }
    };
  }

}