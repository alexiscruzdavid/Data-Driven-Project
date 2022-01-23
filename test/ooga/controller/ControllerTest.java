package ooga.controller;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ooga.display.Viewer;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Class to test the functionality of the Controller
 */
class ControllerTest extends DukeApplicationTest {

  @Test
  void loadNewDisplay() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        boolean check = controller.setupGame(new File("data/config/2048.sim"),
            Paint.valueOf("black"));
        assertTrue(check);
        check = controller.setupGame(new File("data/config/wrong.sim"),
            Paint.valueOf("black"));
        assertFalse(check);
        check = controller.setupGame(new File("data/config/error_invalid_parameters.sim"),
            Paint.valueOf("black"));
        assertFalse(check);
        check = controller.setupGame(new File("data/config/Reflection_Error.sim"),
            Paint.valueOf("black"));
        assertFalse(check);
      }
    });
  }


  @Test
  void checkDisplayInitialized() {
    new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("black"));
        assertNotNull(controller.getDisplay());
      }
    };
  }

  @Test
  void restartGame() {
    new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("black"));
        assertTrue(controller.restartGame());
        assertEquals(controller.getMyLogicController().getGameEngine().getGameScore(), 0);
        assertEquals(controller.getMyLogicController().getGameEngine().getGameTime(), 12);
      }
    };
  }



  @Test
  void getDisplayCheckClass() {
    new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        assertEquals(Viewer.class, controller.getDisplay().getClass());
      }
    };
  }

  @Test
  void testPauseGame() {
    new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        assertTrue(controller.pauseGame());
      }
    };
  }

  @Test
  void testPlayGame() {
    new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        assertTrue(controller.playGame());
      }
    };
  }


  @Test
  void setupDisplayInvalidFile() {
    new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        Boolean result = controller.setupGame(new File("data/config/cool.sim"), Paint.valueOf("black"));
        assertFalse(result);
      }
    };
  }

  @Test
  void readAndApplyNullFile(){
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        try {
          assertFalse(controller.readAndApplyFilePassed(null));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }



  @Test
  void saveGame() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        try {
          controller.saveGame();
          controller = new Controller(stage, "English", "Tetris", Paint.valueOf("white"));
          controller.saveGame();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  @Test
  void readAndApplyValidFile() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        try {
          assertTrue(controller.readAndApplyFilePassed(new File("data/saved/TEST_SAVED_FILE.csv")));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Test
  void readAndApplyInvalidFile() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        try {
          assertFalse(controller.readAndApplyFilePassed(new File("data/config/Tetris.sim")));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Test
  void loadGame() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        try {
          assertTrue(controller.loadGame());

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

}