package ooga.display;

import javafx.application.Platform;
import javafx.scene.paint.Paint;
import ooga.controller.Controller;
import ooga.controller.GameSelectorMenuController;
import util.DukeApplicationTest;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import javafx.scene.control.Button;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisplayTest extends DukeApplicationTest {

  private String language = "English";
  private Paint background= Paint.valueOf("white");
  private Stage myStage;

  @Override
  public void start(Stage stage) {
    myStage = stage;
  }


  @Test
  public void testNewGameButton() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Controller controller = new Controller(myStage, language, "2048", background);
        Viewer view = controller.getDisplay();
        myStage.setScene(view.getScene());
        Button button = lookup("#NewGame").query();
        clickOn(button);
      }
    });

  }

  @Test
  public void testHowToPlayButton(){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Controller controller = new Controller(myStage, language, "2048", background);
        Viewer view = controller.getDisplay();
        myStage.setScene(view.getScene());
        Button button = lookup("#HowToPlay").query();
        clickOn(button);
      }
    });
  }
  @Test
  public void testCheatCodesButton() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Controller controller = new Controller(myStage, language, "2048", background);
        Viewer view = controller.getDisplay();
        myStage.setScene(view.getScene());
        Button button = lookup("#CheatCodes").query();
        clickOn(button);
      }
    });
  }

  @Test
  public void testLeaderboardButton() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Controller controller = new Controller(myStage, language, "2048", background);
        Viewer view = controller.getDisplay();
        myStage.setScene(view.getScene());
        Button button = lookup("#Leaderboard").query();
        clickOn(button);
      }
    });
  }

}
