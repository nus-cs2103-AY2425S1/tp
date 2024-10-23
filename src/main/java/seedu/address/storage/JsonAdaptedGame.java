package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;

/**
 * Jackson-friendly version of {@link Game}.
 */
class JsonAdaptedGame {
    private final String gameName;
    private final String username;
    private final String skillLevel;
    private final String role;
    private final Boolean isFavourite;

    /**
     * Constructs a {@code JsonAdaptedGame} with the given {@code gameName}.
     */
    @JsonCreator
    public JsonAdaptedGame(@JsonProperty("gameName") String gameName, @JsonProperty("username") String username,
                           @JsonProperty("skillLevel") String skillLevel, @JsonProperty("role") String role,
                           @JsonProperty("isFavourite") boolean isFavourite) {
        this.gameName = gameName;
        this.username = username;
        this.skillLevel = skillLevel;
        this.role = role;
        this.isFavourite = isFavourite;
    }

    /**
     * Converts a given {@code Game} into this class for Jackson use.
     */
    public JsonAdaptedGame(Game source) {
        gameName = source.gameName;
        username = source.getUsername().username;
        skillLevel = source.getSkillLevel().skillLevel;
        role = source.getRole().role;
        isFavourite = source.getFavouriteStatus();
    }

    public String getGameName() {
        return gameName;
    }

    /**
     * Converts this Jackson-friendly adapted game object into the model's {@code Game} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted game.
     */
    public Game toModelType() throws IllegalValueException {
        if (!Game.isValidGameName(gameName)) {
            throw new IllegalValueException(Game.MESSAGE_CONSTRAINTS);
        }
        return new Game(gameName, new Username(username), new SkillLevel(skillLevel), new Role(role), isFavourite);
    }

}
