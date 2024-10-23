package seedu.address.model.exam;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Exam that all students in the address book have taken.
 * Guarantees: immutable; name is valid as declared in {@link #isValidExamName(String)}
 * Exam Score is null until initialised to a specific student by {@code AddExamScore}
 */
public class Exam {

    public static final String NAME_MESSAGE_CONSTRAINTS =
            "Exam names should only contain alphanumeric characters and spaces, and it should not be blank.";
    public static final String SCORE_MESSAGE_CONSTRAINTS =
            "Exam scores should be an integer representing a percentage from 0 to 100.";

    /**
     * The first character of the exam name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String SCORE_VALIDATION_REGEX = "^(?:[0-9]|[1-9][0-9]|100|NIL)$";

    public final String examName;
    public final String examScore;

    /**
     * Constructs a {@code Exam}
     *
     * @param examName A valid exam name.
     */
    public Exam(String examName) {
        requireNonNull(examName);
        checkArgument(isValidExamName(examName), NAME_MESSAGE_CONSTRAINTS);
        this.examName = examName;
        this.examScore = "NIL";
    }

    /**
     * Overloaded constructor to add exam score.
     */
    public Exam(String examName, String examScore) {
        requireNonNull(examName);
        requireNonNull(examScore);
        checkArgument(isValidExamName(examName), NAME_MESSAGE_CONSTRAINTS);
        checkArgument(isValidExamScore(examScore), SCORE_MESSAGE_CONSTRAINTS);
        this.examName = examName;
        this.examScore = examScore;
    }

    /**
     * Returns true if a given string is a valid exam name.
     */
    public static boolean isValidExamName(String examName) {
        return examName.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid exam score.
     */
    public static boolean isValidExamScore(String examScore) {
        return examScore.matches(SCORE_VALIDATION_REGEX);
    }

    public String getExamName() {
        return examName;
    }

    public String getExamScore() {
        return examScore;
    }

    @Override
    public String toString() {
        return examName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Exam)) {
            return false;
        }

        Exam otherExam = (Exam) other;
        return examName.equals(otherExam.examName);
    }

    @Override
    public int hashCode() {
        return examName.hashCode();
    }
}
