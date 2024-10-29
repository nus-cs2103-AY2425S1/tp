package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Buyer's name in the meetup list.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeetUpName(String)}
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String meetUpFullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param meetUpName A valid meetup name.
     */
    public Subject(String meetUpName) {
        requireNonNull(meetUpName);
        checkArgument(isValidMeetUpName(meetUpName), MESSAGE_CONSTRAINTS);
        meetUpFullName = meetUpName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMeetUpName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return meetUpFullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherName = (Subject) other;
        return meetUpFullName.equals(otherName.meetUpFullName);
    }

    @Override
    public int hashCode() {
        return meetUpFullName.hashCode();
    }
}
