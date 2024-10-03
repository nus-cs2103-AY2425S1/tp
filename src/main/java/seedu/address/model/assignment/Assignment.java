package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Assignment in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Assignment {

    public static final String MESSAGE_CONSTRAINTS =
            "The maximum score should be greater than or equal to the minimum score of 0";
    private static final int MIN_SCORE = 0;

    // Identity fields
    private final Name name;

    // Data fields
    private final int maxScore;
    private int score = 0;
    private boolean hasSubmitted = false;

    /**
     * Every field must be present and not null.
     */
    public Assignment(Name name, int maxScore) {
        requireNonNull(name);
        this.name = name;
        checkArgument(isValidScore(maxScore), MESSAGE_CONSTRAINTS);
        this.maxScore = maxScore;
    }

    /**
     * Returns true if a given maxScore is a valid score.
     */
    public static boolean isValidScore(int test) {
        return test >= MIN_SCORE;
    }

    /**
     * Returns true if both assignments have the same name.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                && otherAssignment.name.equals(name);
    }

    /**
     * Returns true if both assignments have the same identity and data fields.
     * This defines a stronger notion of equality between two assignments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return name.equals(otherAssignment.name)
                && MIN_SCORE == otherAssignment.MIN_SCORE
                && maxScore == otherAssignment.maxScore
                && score == otherAssignment.score
                && hasSubmitted == otherAssignment.hasSubmitted;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, MIN_SCORE, maxScore, score, hasSubmitted);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("MIN_SCORE", MIN_SCORE)
                .add("maxScore", maxScore)
                .add("score", score)
                .add("hasSubmitted", hasSubmitted)
                .toString();
    }

}
