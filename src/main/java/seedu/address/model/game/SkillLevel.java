package seedu.address.model.game;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Describes the Person's skill level in a Game.
 */
public class SkillLevel {

    public static final String MESSAGE_CONSTRAINTS =
            "Skill Level should not be blank";

    /*
     * Regex expression matches Strings that contain at least one non-whitespace character.
     */
    private static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String skillLevel;

    /**
     * Constructs a {@code SkillLevel}.
     *
     * @param skillLevel a valid skill level or rank.
     */
    public SkillLevel(String skillLevel) {
        requireNonNull(skillLevel);
        checkArgument(isValidSkillLevel(skillLevel), MESSAGE_CONSTRAINTS);
        this.skillLevel = skillLevel;
    }

    /**
     * Returns true if a given string is a valid SkillLevel.
     */
    public static boolean isValidSkillLevel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Getter for skillLevel field.
     */
    public String getSkillLevel() {
        return skillLevel;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SkillLevel)) {
            return false;
        }

        SkillLevel otherName = (SkillLevel) other;
        return skillLevel.equals(otherName.skillLevel);
    }

    @Override
    public int hashCode() {
        return skillLevel.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return skillLevel;
    }

}

