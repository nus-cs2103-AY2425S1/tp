package seedu.address.model.exam;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Exam that students in the address book have taken.
 * Guarantees: immutable; name is valid as declared in {@link #isValidExamName(String)}
 * Exam Score is initialised as NIL and an actual score can be added by {@code AddExamScore}
 */
public class Exam {

    public static final String NAME_MESSAGE_CONSTRAINTS =
            "Exam names should only contain alphanumeric characters and spaces, and it should not be blank.";
    public static final String SCORE_MESSAGE_CONSTRAINTS =
            "Exam scores should be a percentage accurate to one decimal point (i.e. 0.0 - 100.0).\n"
            + "To delete an exam score, input 'NIL' as the score.";

    /**
     * The first character of the exam name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String SCORE_VALIDATION_REGEX = "^(100\\.0|[1-9]?[0-9]\\.[0-9]|0\\.[0-9]|NIL)$";

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

        // an exam is equal to another exam if they have the same name, the score is not considered
        Exam otherExam = (Exam) other;
        return examName.equals(otherExam.examName);
    }

    @Override
    public int hashCode() {
        return examName.hashCode();
    }
}
