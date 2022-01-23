package ooga.controller;

import javafx.stage.Stage;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.display.ProfileEditorMenu;
import java.util.HashMap;

/**
 * a controller for managing the Profile Editor menu and
 * the related displays
 */


public class ProfileEditorController {

    private final ProfileEditorMenu profileEditor;

    /**
     * constructor for ProfileEditor Controller
     */

    public ProfileEditorController(String language, Stage stage, Profile currentPlayer) {
        profileEditor = new ProfileEditorMenu(language, stage, currentPlayer);
        stage.setScene(profileEditor.setupDisplay());
        stage.show();
    }

    /**
     * Gets the Profile Menu
     */

    public ProfileEditorMenu getProfileEditorMenu() {
        return profileEditor;
    }

}