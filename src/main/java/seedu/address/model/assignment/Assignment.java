package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.assignment.exceptions.ScoreExceedsMaxScoreException;

/**
 * Represents an Assignment in TAchy.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Assignment {

    public static final String MAX_SCORE_MESSAGE_CONSTRAINTS =
            "The maximum score should be an integer greater than 0 and should not be blank.";
    public static final String SCORE_MESSAGE_CONSTRAINTS =
            "The score should be an integer between (inclusive) of the minimum and maximum score.";

    private static final int MIN_SCORE = 0;

    // Identity fields
    private final AssignmentName assignmentName;

    // Data fields
    private final int maxScore;
    private int score = 0;
    private boolean hasSubmitted = false;
    private boolean isGraded = false;

    /**
     * Every field must be present and not null.
     */
    public Assignment(AssignmentName name, int maxScore) {
        requireNonNull(name);
        this.assignmentName = name;
        checkArgument(isValidMaxScore(maxScore), MAX_SCORE_MESSAGE_CONSTRAINTS);
        this.maxScore = maxScore;
    }
    /**
     * Constructs a new {@code Assignment} by copying the fields from another {@code Assignment}.
     * This creates a new instance with the same values for assignment name, maximum score,
     * score, submission status, and graded status as the provided {@code other} assignment.
     *
     * @param other The {@code Assignment} to copy. Must not be null.
     * @throws NullPointerException if {@code other} is null.
     */
    public Assignment(Assignment other) {
        this.assignmentName = other.assignmentName;
        this.maxScore = other.maxScore;
        this.score = other.score;
        this.hasSubmitted = other.hasSubmitted;
        this.isGraded = other.isGraded;
    }
    /**
     * Returns true if a given maxScore is a valid score.
     */
    public static boolean isValidMaxScore(int test) {
        return test >= MIN_SCORE;
    }

    /**
     * Returns true if both assignments have the same assignmentName.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                && otherAssignment.assignmentName.equals(assignmentName)
                && otherAssignment.maxScore == maxScore;
    }

    public AssignmentName getAssignmentName() {
        return this.assignmentName;
    }

    public String getName() {
        return this.assignmentName.assignmentName;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public int getScore() {
        return this.score;
    }

    public boolean getIsGraded() {
        return this.isGraded;
    }

    public boolean getHasSubmitted() {
        return this.hasSubmitted;
    }

    public void setScore(int score) {
        if (score > this.getMaxScore()) {
            throw new ScoreExceedsMaxScoreException();
        }
        this.score = score;
    }
    public void setHasSubmitted(boolean hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }

    public void setIsGraded(boolean isGraded) {
        this.isGraded = isGraded;
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
        return assignmentName.equals(otherAssignment.assignmentName)
               && MIN_SCORE == otherAssignment.MIN_SCORE
               && maxScore == otherAssignment.maxScore
               && score == otherAssignment.score
               && hasSubmitted == otherAssignment.hasSubmitted
               && isGraded == otherAssignment.isGraded;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(assignmentName, MIN_SCORE, maxScore, score, hasSubmitted);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", assignmentName)
                .add("MIN_SCORE", MIN_SCORE)
                .add("maxScore", maxScore)
                .add("score", score)
                .add("hasSubmitted", hasSubmitted)
                .add("isGraded", isGraded)
                .toString();
    }

}
