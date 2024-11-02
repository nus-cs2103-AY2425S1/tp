package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Buyer's subject in the meetup list.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS =
            "Subjects should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String meetUpFullSubject;

    /**
     * Constructs a {@code Subject}.
     *
     * @param meetUpSubject A valid meetup subject.
     */
    public Subject(String meetUpSubject) {
        requireNonNull(meetUpSubject);
        checkArgument(isValidSubject(meetUpSubject), MESSAGE_CONSTRAINTS);
        meetUpFullSubject = meetUpSubject;
    }

    /**
     * Returns true if a given string is a valid subject.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return meetUpFullSubject;
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

        Subject otherSubject = (Subject) other;
        return meetUpFullSubject.equals(otherSubject.meetUpFullSubject);
    }

    @Override
    public int hashCode() {
        return meetUpFullSubject.hashCode();
    }
}
