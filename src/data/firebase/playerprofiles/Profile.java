package ooga.data.firebase.playerprofiles;

import java.util.Map;
import java.util.Objects;

/**
 * Profile is a helper class meant to represent and contain player data that is used in making new
 * profiles, such as usernames and passwords.
 */
public class Profile {

  private final String username;
  private final String password;
  private Long highestScoreEver;
  private final String color;
  private final String language;

  /**
   * Constructor class that initializes a profile based on the inputs passed in.
   *
   * @param name             name of player
   * @param password         password for player
   * @param highestScoreEver highest score for player
   * @param color            preferred color of player
   * @param language         preferred language for player
   */
  public Profile(String name, String password, Long highestScoreEver,
      String color, String language) {
    this.username = name;
    this.password = password;
    this.highestScoreEver = highestScoreEver;
    this.color = color;
    this.language = language;
  }

  /**
   * Constructor that creates profile based on map of values
   *
   * @param playerData map of inputs
   */
  public Profile(Map<String, Object> playerData) {
    if (playerData != null) {
      this.username = (String) playerData.get("username");
      this.password = (String) playerData.get("password");
      this.highestScoreEver = (Long) playerData.get("highestScoreEver");
      this.color = (String) playerData.get("color");
      this.language = (String) playerData.get("language");
    } else {
      this.username = "";
      this.password = "";
      this.highestScoreEver = 0L;
      this.color = "0x0";
      this.language = "";
    }
  }

  /**
   * Constructor that creates empty profile
   */
  public Profile() {
    this.username = "";
    password = "";
    highestScoreEver = 0L;
    this.color = "0x0";
    this.language = "";
  }

  /**
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return highest score
   */
  public Long getHighestScoreEver() {
    return highestScoreEver;
  }

  /**
   * @return color
   */
  public String getColor() {
    return color;
  }

  /**
   * @return language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param highestScoreEver new highest score
   */
  public void setHighestScoreEver(Long highestScoreEver) {
    this.highestScoreEver = highestScoreEver;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Profile profile = (Profile) o;
    return username.equals(profile.username) && password.equals(profile.password) && color.equals(
        profile.color) && language.equals(profile.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, highestScoreEver, color, language);
  }
}
