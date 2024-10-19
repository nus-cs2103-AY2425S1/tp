package seedu.address.model.tutorial;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Tutorial class for the students
 * Guarantees: details are present and not null, field values are validated
 */
public class Tutorial {

    private String subject;

    /**
     * Every field must be present and not null.
     */
    public Tutorial(String subject) {
        requireNonNull(subject);
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
        return otherTutorial != null && otherTutorial.getSubject().equals(getSubject());
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
        return subject.equals(otherTutorial.subject);
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
