package seedu.address.model.person;

/**
 * Represents a Person's subject teaching or learning in the address book.
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS =
            "Subjects should be string format and of those valid ones(english, math, science).";

    public static final String[] AVAILABLE_SUBJECTS = {"english", "math", "science"};
    public final String subject;


    /**
     * subject must be one of available subjects
     * @param subject
     */
    public Subject(String subject) {
        if (!isValidSubject(subject)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.subject = subject;
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
