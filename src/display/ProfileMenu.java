package ooga.display;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ooga.controller.DataController;
import ooga.controller.GameSelectorMenuController;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.resources.Config;
import ooga.util.AlertFactory;
import ooga.util.NodeFactory;
import ooga.util.ExceptionHandlerFactory;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import static java.util.Objects.isNull;

public class ProfileMenu {

  private DataController dataController;

  /**
   * resource package file path
   */

  public static final String RESOURCE_PACKAGE = "ooga.resources.";

  /**
   * Button resource package file path
   */

  public static final String BUTTON_RESOURCE_PACKAGE = RESOURCE_PACKAGE.replace(".", "/") + "Buttons";


  /**
   * default style sheet file path
   */
  public static final String DEFAULT_STYLESHEET =
            "/" + RESOURCE_PACKAGE.replace(".", "/") + "styles.css";


  /**
   * resource bundles
   */
  private ResourceBundle resources;
  protected ResourceBundle buttonResources;

  /**
   * factories for buttons, alerts, and exceptions
   */
  private NodeFactory nodeFactory;
  private AlertFactory alertFactory;
  private ExceptionHandlerFactory exceptionFactory;
  private Stage stage;
  private javafx.scene.paint.Color myColor;
  private GridPane root;
  private TextField createAccUserField;
  private TextField createAccPasswordField;
  private TextField loginUserField;
  private TextField loginPasswordField;
  private ColorPicker preferredThemeField;
  private ComboBox<String> selectLang;
  private GameSelectorMenuController gameSelectorMenuController;

  public ProfileMenu(String language, Stage stage) {
    dataController = new DataController();
    resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
    buttonResources = ResourceBundle.getBundle(
        BUTTON_RESOURCE_PACKAGE);
    nodeFactory = new NodeFactory(resources, buttonResources);
    alertFactory = new AlertFactory(resources);
    exceptionFactory = new ExceptionHandlerFactory(resources);
    this.stage = stage;
  }

  /**
   * displays Profile Creator Menu
   */
  public Scene setupDisplay() {
    root = new GridPane();
    root.getStyleClass().add("menu-pane");
    root.setId("ProfileMenu");
    Label createAccTitle = nodeFactory.makeLabel(resources.getString(Config.CREATE_ACCOUNT_TITLE), Config.TITLE_FONT_SIZE, Config.CREATE_ACCOUNT_ID);
    Label usernameLabel = nodeFactory.makeLabel(resources.getString(Config.USERNAME_LABEL), Config.TEXT_FONT_SIZE, Config.CREATE_ACCOUNT_USERNAME_ID);
    Label loginUsernameLabel = nodeFactory.makeLabel(resources.getString(Config.USERNAME_LABEL), Config.TEXT_FONT_SIZE, Config.LOGIN_USERNAME_ID);
    createAccUserField = nodeFactory.makeTextField(Config.CREATE_ACCOUNT_USERNAME_FIELD_ID);
    Label createAccPasswordLabel = nodeFactory.makeLabel(resources.getString(Config.PASSWORD_LABEL), Config.TEXT_FONT_SIZE, Config.CREATE_ACCOUNT_PASSWORD_ID);
    Label usernamePasswordLabel = nodeFactory.makeLabel(resources.getString(Config.PASSWORD_LABEL), Config.TEXT_FONT_SIZE, Config.LOGIN_PASSWORD_ID);
    createAccPasswordField = nodeFactory.makeTextField(Config.CREATE_ACCOUNT_PASSWORD_FIELD_ID);
    Label preferredThemeLabel = nodeFactory.makeLabel(resources.getString(Config.PREFFERED_THEME_LABEL), Config.TEXT_FONT_SIZE, Config.PREFFERED_THEME_LABEL_ID);
    Consumer<javafx.scene.paint.Color> response = color -> myColor = color;
    preferredThemeField = nodeFactory.makeColorPicker(Config.THEME_COLORPICKER_ID, Color.WHITE, response);
    Label languageLabel = nodeFactory.makeLabel(resources.getString(Config.SELECT_LANGUAGE_LABEL), Config.TEXT_FONT_SIZE, Config.SELECT_LANGUAGE_LABEL_ID);
    String[] langs = {Config.ENGLISH_STRING, Config.FRENCH_STRING, Config.GERMAN_STRING, Config.SPANISH_STRING};
    selectLang = nodeFactory.makeDropDown(Config.LANGUAGE_COMBOBOX, langs, Config.LANGUAGE_COMBOBOX_ID);
    myColor = javafx.scene.paint.Color.BLACK;
    Label loginTitle = nodeFactory.makeLabel(resources.getString(Config.LOGIN_TITLE), Config.TEXT_FONT_SIZE, Config.LOGIN_ID);
    loginTitle.setFont(new Font(Config.FONT, Config.TITLE_FONT_SIZE));
    loginUserField = nodeFactory.makeTextField(Config.LOGIN_USERNAME_FIELD_ID);
    loginPasswordField = nodeFactory.makeTextField(Config.LOGIN_PASSWORD_FIELD_ID);
    Button createAccountButton = nodeFactory.makeButton(Config.CREATE_ACCOUNT_BUTTON, this);
    Button logInButton = nodeFactory.makeButton(Config.LOGIN_BUTTON, this);
    root.add(createAccTitle, 0, 0);
    root.add(usernameLabel, 0, 1);
    root.add(createAccUserField, 1, 1);
    root.add(createAccPasswordLabel, 0, 2);
    root.add(createAccPasswordField, 1, 2);
    root.add(preferredThemeLabel, 0, 3);
    root.add(preferredThemeField, 1, 3);
    root.add(languageLabel, 0, 4);
    root.add(selectLang, 1, 4);
    root.add(createAccountButton, 0, 5);
    root.add(loginTitle, 0, 6);
    root.add(loginUsernameLabel, 0, 7);
    root.add(loginUserField, 1, 7);
    root.add(usernamePasswordLabel, 0, 8);
    root.add(loginPasswordField, 1, 8);
    root.add(logInButton, 0, 9);
    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    return scene;
  }
  /**
   * creates account in database and stores it then opens game selector
   */
  public void createAccount() {
      if (createAccUserField.getText().isEmpty()
              || createAccPasswordField.getText().isEmpty()
              || isNull(selectLang.getValue())) {
          alertFactory.makeAlertWait(resources.getString(Config.ERROR_ALERT), resources.getString(Config.MISSING_INFO),
                  Alert.AlertType.ERROR);
      } else {
          alertFactory.makeAlertWait(resources.getString(Config.SUCCESS_ALERT),
                  resources.getString(Config.REGISTRATION_SUCCESSFUL), Alert.AlertType.CONFIRMATION);
          String username = createAccUserField.getText();
          String password = createAccPasswordField.getText();
          javafx.scene.paint.Color preferredTheme = preferredThemeField.getValue();
          String language = selectLang.getValue();
          Profile newProfile = new Profile(username, password, 0L, preferredTheme.toString(), language);
          dataController.savePlayerProfile(newProfile);
          dataController.setCurrentPlayerInGame(newProfile.getUsername());
          openGameSelector();
      }
  }

  /**
   * logs in to account in database if it exists then opens game selector
   */

    public void login() {
        String username = loginUserField.getText();
        String password = loginPasswordField.getText();
        Profile player = dataController.retrievePlayerProfile(username);
        if (loginUserField.getText().isEmpty()
                || loginPasswordField.getText().isEmpty()
        ) {
            alertFactory.makeAlertWait(resources.getString(Config.ERROR_ALERT), resources.getString(Config.MISSING_INFO), Alert.AlertType.ERROR);
            return;
        } else if (player != null && player.getPassword().equals(password)) {
            DataController.setCurrentPlayerInGame(player.getUsername());
            alertFactory.makeAlertWait(resources.getString(Config.SUCCESS_ALERT), resources.getString(Config.WELCOME_BACK), Alert.AlertType.CONFIRMATION);
            openGameSelector();
        } else {
            alertFactory.makeAlertWait(resources.getString(Config.ERROR_ALERT), resources.getString(Config.MISSING_PLAYER), Alert.AlertType.ERROR);
        }
    }

    private void openGameSelector() {
        stage.close();
        Stage newStage = new Stage();
        GameSelectorMenuController gameSelectorMenuController = new GameSelectorMenuController(
                getCurrentPlayer().getLanguage(), newStage);
        newStage.setScene(gameSelectorMenuController.getGameSelectorMenu().setupDisplay());
    }

  /**
   * Gets current Player info
   */

  public Profile getCurrentPlayer(){
    return  dataController.retrieveCurrentPlayer();
  }

}
