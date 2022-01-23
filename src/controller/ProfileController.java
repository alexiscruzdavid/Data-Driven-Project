package ooga.controller;

import javafx.stage.Stage;
import ooga.display.ProfileMenu;

/**
 * A Controller for managing the profile menu and the
 * related displays
 */
public class ProfileController {

    private final ProfileMenu profile;

    public ProfileController(String language, Stage stage) {
        profile = new ProfileMenu(language, stage);
        stage.setScene(profile.setupDisplay());
        stage.show();
    }

    public ProfileMenu getProfileMenu() {
        return profile;
    }

}
