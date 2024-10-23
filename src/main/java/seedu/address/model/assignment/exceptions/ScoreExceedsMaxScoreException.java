package seedu.address.model.assignment.exceptions;

/**
 * Signals that the operation will result in an Assignment score that exceeds the max score for that assignment
 */
public class ScoreExceedsMaxScoreException extends RuntimeException {
    public ScoreExceedsMaxScoreException() {
        super("Operation would result in score exceeding assignment maxScore");
    }
}
