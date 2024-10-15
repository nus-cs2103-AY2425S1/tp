package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's class in the address book.
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS =
            "Subjects should only contain alphanumeric characters";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String subject;

    /**
     * Constructs a {@code Class}.
     *
     * @param subject A valid class.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_CONSTRAINTS);
        this.subject = subject;
    }

    /**
     * Returns true if a given string is a valid class name.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return subject;
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
        return subject.equals(otherSubject.subject);
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
