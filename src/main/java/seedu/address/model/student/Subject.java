package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Student's subject in the address book.
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS = "Subjects should only be: "
            + "economics / literature / music / "
            + "biology / chemistry /science / "
            + "english / chinese / malay / tamil / "
            + "mathematics / history / geography / physics / "
            + "GP "
            + "and it should not be blank";


    public final Subjects value;

    /**
     * Constructs a {@code Subject}.
     *
     * @param subject A valid subject.
     */

    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_CONSTRAINTS);
        value = Subjects.valueOf(subject.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid subject.
     */
    public static boolean isValidSubject(String test) {
        try {
            Subjects.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
        return value.equals(otherSubject.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns string value of enumerated subject.
     * @return String value of subject.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
