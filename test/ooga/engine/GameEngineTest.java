package ooga.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.data.GameData;
import ooga.data.SIMFileReader;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GameEngineTest extends DukeApplicationTest {

  @Test
  void setupGame2048() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Engine gameEngine = new GameEngine();
        GameData gameData = null;
        try {
          gameData = new GameData(
              SIMFileReader.getMetadataFromFile(new File("data/config/2048.sim")));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assertTrue(gameEngine.setupGame(new Grid(10, 10), gameData));
      }
    });

  }

  @Test
  void setupGameTetris() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Engine gameEngine = new GameEngine();
        GameData gameData = null;
        try {
          gameData = new GameData(
              SIMFileReader.getMetadataFromFile(new File("data/config/Tetris.sim")));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assertTrue(gameEngine.setupGame(new Grid(10, 10), gameData));
      }
    });
  }


  @Test
  void setupGameBejeweled() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Engine gameEngine = new GameEngine();
        GameData gameData = null;
        try {
          gameData = new GameData(
              SIMFileReader.getMetadataFromFile(new File("data/config/2048.sim")));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assertTrue(gameEngine.setupGame(new Grid(10, 10), gameData));
      }
    });
  }

  @Test
  void nextGameStateNullCheatCode() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        try {
          assertTrue(gameEngine.nextGameState(null));
        }
        catch (Exception e){

        }
      }
    });
  }


  @Test
  void resetGameCheckScore() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        gameEngine.respondToUserInputKeys("Left");
        gameEngine.nextGameState(null);
        assertTrue(gameEngine.resetGame(new Grid(10, 10)));
        assertEquals(0, gameEngine.getGameScore());
      }
    });
  }


  @Test
  void resetGameCheckTime() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        GameEngine gameEngine = new GameEngine();
        GameData gameData = null;
        try {
          gameData = new GameData(
              SIMFileReader.getMetadataFromFile(new File("data/config/2048.sim")));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        gameEngine.setupGame(new Grid(10, 10), gameData);
        gameEngine.resetGame(new Grid(10, 10));
        assertEquals(12, gameEngine.getTimer().getTime());
      }
    });
  }

  @Test
  void nextGameStateCheatCode() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        try {
          assertTrue(gameEngine.nextGameState("RareCandy"));
        }
        catch(Exception e){

        }
      }
    });
  }


  @Test
  void setState() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertFalse(gameEngine.setState(-1, 0, 0));
        assertFalse(gameEngine.setState(0, -1, 0));
        assertFalse(gameEngine.setState(0, 0, -1));
        assertTrue(gameEngine.setState(0, 0, 0));
      }
    });
  }


  @Test
  void respondToKeyInputValid() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertTrue(gameEngine.respondToUserInputKeys("Left"));
      }
    });
  }


  @Test
  void respondToKeyInputInvalid() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertFalse(gameEngine.respondToUserInputKeys("K"));
      }
    });
  }

  @Test
  void respondToKeyInputNull() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertFalse(gameEngine.respondToUserInputKeys(null));
      }
    });
  }

  @Test
  void respondToMouseInputNotEnabled() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertFalse(gameEngine.respondToUserInputMouse(3, 3));
      }
    });
  }

  @Test
  void respondToMouseInputInvalidLocation() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048",
            Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertFalse(gameEngine.respondToUserInputMouse(40, 40));
      }
    });
  }

  @Test
  void respondToMouseInputValidGameAndLocation() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage stage = new Stage();
        Controller controller = new Controller(stage, "English", "2048", Paint.valueOf("white"));
        controller.setupGame(new File("data/config/2048.sim"), Paint.valueOf("white"));
        Engine gameEngine = controller.getMyLogicController().getGameEngine();
        assertFalse(gameEngine.respondToUserInputMouse(4, 4));
      }
    });
  }
}