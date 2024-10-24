package seedu.address.model.submission;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Submission that all students in the address book have taken.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSubmissionName(String)}
 * Submission Status is null until initialised to a specific student by {@code AddSubmissionStatus}
 */
public class Submission {

    public static final String NAME_MESSAGE_CONSTRAINTS =
            "Submission names should only contain alphanumeric characters and spaces, and it should not be blank.";
    public static final String STATUS_MESSAGE_CONSTRAINTS =
            "Submission status should only be \"Y\" or \"N\".";

    /**
     * The first character of the submission name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String STATUS_VALIDATION_REGEX = "^[YN]|NIL$";

    public final String submissionName;
    public final String submissionStatus;

    /**
     * Constructs a {@code Submission}
     *
     * @param submissionName A valid submission name.
     */
    public Submission(String submissionName) {
        requireNonNull(submissionName);
        checkArgument(isValidSubmissionName(submissionName), NAME_MESSAGE_CONSTRAINTS);
        this.submissionName = submissionName;
        this.submissionStatus = "NIL";
    }

    /**
     * Overloaded constructor to add submission status.
     */
    public Submission(String submissionName, String submissionStatus) {
        requireNonNull(submissionName);
        requireNonNull(submissionStatus);
        checkArgument(isValidSubmissionName(submissionName), NAME_MESSAGE_CONSTRAINTS);
        checkArgument(isValidSubmissionStatus(submissionStatus), STATUS_MESSAGE_CONSTRAINTS);
        this.submissionName = submissionName;
        this.submissionStatus = submissionStatus;
    }

    /**
     * Returns true if a given string is a valid submission name.
     */
    public static boolean isValidSubmissionName(String submissionName) {
        return submissionName.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid submission status.
     */
    public static boolean isValidSubmissionStatus(String submissionStatus) {
        return submissionStatus.matches(STATUS_VALIDATION_REGEX);
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    @Override
    public String toString() {
        return submissionName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Submission)) {
            return false;
        }

        Submission otherSubmission = (Submission) other;
        return submissionName.equals(otherSubmission.submissionName);
    }

    @Override
    public int hashCode() {
        return submissionName.hashCode();
    }
}
