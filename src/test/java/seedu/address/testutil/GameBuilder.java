package seedu.address.testutil;


import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
/**
 * A utility class to help with building Game objects.
 */
public class GameBuilder {

    public static final String DEFAULT_GAME_NAME = "LoL";
    public static final String DEFAULT_USERNAME = "lmao";
    public static final String DEFAULT_SKILL_LEVEL = "nub";
    public static final String DEFAULT_ROLE = "npc";
    public static final Boolean DEFAULT_FAVOURITE_STATUS = false;

    private String gameName;
    private Role role;
    private SkillLevel skillLevel;
    private Username username;
    private boolean isFavourite;
    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public GameBuilder() {
        gameName = DEFAULT_GAME_NAME;
        role = new Role(DEFAULT_ROLE);
        skillLevel = new SkillLevel(DEFAULT_SKILL_LEVEL);
        username = new Username(DEFAULT_USERNAME);
        isFavourite = DEFAULT_FAVOURITE_STATUS;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public GameBuilder(Game gameToCopy) {
        gameName = gameToCopy.getGameName();
        role = gameToCopy.getRole();
        skillLevel = gameToCopy.getSkillLevel();
        username = gameToCopy.getUsername();
        isFavourite = gameToCopy.getFavouriteStatus();
    }

    /**
     * Sets the {@code gameName} of the {@code Game} that we are building.
     */
    public GameBuilder withGameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Game} that we are building.
     */
    public GameBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Game} that we are building.
     */
    public GameBuilder withSkillLevel(String skillLevel) {
        this.skillLevel = new SkillLevel(skillLevel);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Game} that we are building.
     */
    public GameBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    public Game build() {
        return new Game(gameName, username, skillLevel, role, isFavourite);
    }

}
