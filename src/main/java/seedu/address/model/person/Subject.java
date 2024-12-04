package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.capitalizeFirstLetter;

/**
 * Represents a Person's subject teaching or learning in the address book.
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS = "Subjects should be of either English, Math or Science.";
    public static final String[] AVAILABLE_SUBJECTS = {"English", "Math", "Science"};
    public final String subject;

    /**
     * Subject must be one of available subjects
     * @param subject subject being learned or taught
     */
    public Subject(String subject) {
        requireNonNull(subject);
        if (!isValidSubject(subject)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.subject = capitalizeFirstLetter(subject);
    }

    public String getSubject() {
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

    @Override
    public String toString() {
        return subject;
    }

    /**
     * checks if subject is valid
     * @param subject
     * @return {@code true} if subject is valid and {@code false} if it is not valid
     */
    public static boolean isValidSubject(String subject) {
        for (String availableSubject : AVAILABLE_SUBJECTS) {
            if (availableSubject.equalsIgnoreCase(subject)) {
                return true;
            }
        }
        return false;
    }

}
