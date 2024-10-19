package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS = "Subjects should only contain alphanumeric characters and spaces, "
            + "and it should not be blank.";
    /*
     * The first character of the subject must be an alphanumeric character, and it can be followed by
     * alphanumeric characters or spaces.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String subjectName;

    /**
     * Constructs a {@code Subject}.
     *
     * @param subject A valid subject name.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_CONSTRAINTS);
        subjectName = subject;
    }

    /**
     * Returns if a given string is a valid subject name.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return subjectName;
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
        return subjectName.equals(otherSubject.subjectName);
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }
}

