package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.EnumUtil.inEnum;

import java.util.Arrays;

/**
 * Represents a Level in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidLevelName(String)}
 */
public class Level {
    enum Levels {
        K1, K2,
        P1, P2, P3, P4, P5, P6,
        S1, S2, S3, S4, S5,
        JC1, JC2,

    }

    public static final String MESSAGE_CONSTRAINTS = "Level names should be in list: "
            + Arrays.toString(Levels.values());

    public final String levelName;

    /**
     * Constructs a {@code Level}.
     *
     * @param levelName A valid level name.
     */
    public Level(String levelName) {
        requireNonNull(levelName);
        checkArgument(isValidLevelName(levelName), MESSAGE_CONSTRAINTS);
        this.levelName = levelName;
    }

    /**
     * Returns true if a given string is a valid level name.
     */
    public static boolean isValidLevelName(String test) {
        return inEnum(test, Levels.class);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Level)) {
            return false;
        }

        Level otherTag = (Level) other;
        return levelName.equals(otherTag.levelName);
    }

    @Override
    public int hashCode() {
        return levelName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + levelName + ']';
    }

}

