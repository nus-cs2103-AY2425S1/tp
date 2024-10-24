package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Interview Score in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInterviewScore(String)}
 */
public class InterviewScore {

    public static final String MESSAGE_CONSTRAINTS =
            "Error: Invalid interview score. Please enter a valid interview score from 0.0 to 10.0, "
                    + "with a maximum of 1 decimal place.";
    /*
     * The first character of the interview score must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Interview scores can only range from 0.0 to 10.0 with a maximum of a single decimal place.
     */
    public static final String VALIDATION_REGEX = "^(?!\\s)(10\\.0|[0-9](\\.\\d)?|10(\\.0)?)$";

    public final String interviewScore;

    /**
     * Constructs a {@code InterviewScore}.
     *
     * @param interviewScore A valid interview score.
     */
    public InterviewScore(String interviewScore) {
        requireNonNull(interviewScore);
        checkArgument(isValidInterviewScore(interviewScore), MESSAGE_CONSTRAINTS);
        this.interviewScore = interviewScore;
    }

    /**
     * Returns true if a given string is a valid interview score.
     */
    public static boolean isValidInterviewScore(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both interview scores ignoring case are the same.
     * This defines a case-insensitive notion of equality between two interview scores.
     */
    public boolean isSameInterviewScore(InterviewScore otherInterviewScore) {
        if (otherInterviewScore == this) {
            return true;
        }

        return otherInterviewScore != null
                && this.interviewScore.equalsIgnoreCase(otherInterviewScore.interviewScore);
    }

    @Override
    public String toString() {
        return interviewScore;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewScore)) {
            return false;
        }

        InterviewScore otherInterviewScore = (InterviewScore) other;
        return interviewScore.equals(otherInterviewScore.interviewScore);
    }

    @Override
    public int hashCode() {
        return interviewScore.hashCode();
    }

}
