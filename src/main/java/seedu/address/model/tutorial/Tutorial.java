package seedu.address.model.tutorial;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Tutorial class for the students
 * Guarantees: details are present and not null, field values are validated
 */
public class Tutorial {

    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial name should only contain alphanumeric characters and spaces, and it should not be blank.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    private final String subject;

    /**
     * Every field must be present and not null.
     */
    public Tutorial(String subject) {
        requireAllNonNull(subject);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * Returns true if both tutorials are of the same subject.
     * This defines a weaker notion of equality between two tutorials.
     */
    public boolean isSameTutorial(Tutorial otherTutorial) {
        if (otherTutorial == this) {
            return true;
        }
        return otherTutorial != null && otherTutorial.getSubject().equalsIgnoreCase(getSubject());
    }
    public static boolean isValidTutorial(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both tutorials have the same data fields.
     * This defines a stronger notion of equality between two tutorials.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return subject.equalsIgnoreCase(otherTutorial.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subject", subject)
                .toString();
    }
}
