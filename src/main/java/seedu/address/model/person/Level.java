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

    enum Year {
        NONE,
        S1,
        S2,
        S3,
        S4,
        S5,
    }

    enum Track {
        NONE,
        EXPRESS,
        NA,
        NT,
        IP,
    }

    public static final String MESSAGE_CONSTRAINTS = "Level must be in the format 'Year Track' "
            + "where Year is one of: " + Arrays.toString(Year.values())
            + " and Track is one of: " + Arrays.toString(Track.values())
            + " with the exception of S5 which is only allowed to have the Track NA";

    public final String levelName;

    /**
     * Constructs a {@code Level}.
     *
     * @param levelName A valid Level name.
     *
     */
    public Level(String levelName) {
        requireNonNull(levelName);
        checkArgument(isValidLevelName(levelName), MESSAGE_CONSTRAINTS);

        this.levelName = levelName;

    }

    /**
     * Returns true if a given string is a valid level name.
     */
    public static boolean isValidLevelName(String levelName) {
        String[] parts = levelName.split(" ");

        if (parts.length != 2) {
            return false;
        }

        String year = parts[0];
        String track = parts [1];

        requireNonNull(year);
        requireNonNull(track);

        if (year.equals("S5")) {
            return track.equals("NA");
        }

        return isValidYear(year) && isValidTrack(track);
    }

    private static boolean isValidYear(String year) {
        return inEnum(year, Year.class);
    }

    private static boolean isValidTrack(String track) {
        return inEnum(track, Track.class);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Level otherLevel)) {
            return false;
        }

        return levelName.equals(otherLevel.levelName);
    }

    @Override
    public int hashCode() {
        return levelName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return levelName;
    }

}

