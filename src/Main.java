package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.ProfileController;

public class Main extends Application {


    /**
     * The current version of our OOGA program.
     **/
    public static final String CURRENT_VERSION = "0.0.1";

    /**
     * The name of our program's window
     **/
    public static final String WINDOW_NAME = "OOGA";

    /**
     * Start of the program.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle(String.format("%s %s", WINDOW_NAME, CURRENT_VERSION));
        ProfileController profileController = new ProfileController("English", stage);
        stage.show();
    }
}
