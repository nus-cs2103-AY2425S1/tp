package spleetwaise.transaction.model.transaction;

import static java.util.Objects.requireNonNull;

import spleetwaise.commons.util.AppUtil;

/**
 * Represents the status of a transaction, indicating whether it is done or not.
 */
public class Status {

    public static final String DONE_STATUS = "Done";
    public static final String NOT_DONE_STATUS = "Not Done";

    public static final String MESSAGE_CONSTRAINTS = "Status should be '" + DONE_STATUS + "' or '" + NOT_DONE_STATUS
            + "'";

    private final boolean isDone;

    /**
     * Constructs a {@code Status} with the specified completion state.
     *
     * @param isDone {@code true} if the status is marked as done, {@code false} otherwise.
     */
    public Status(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Constructs a {@code Status} with the specified isDoneString.
     *
     * @param isDoneString The string representation of the completion state.
     */
    public Status(String isDoneString) {
        requireNonNull(isDoneString);
        String isDoneStringTrimmed = isDoneString.trim();
        AppUtil.checkArgument(isValidStatus(isDoneStringTrimmed), MESSAGE_CONSTRAINTS);
        this.isDone = DONE_STATUS.equals(isDoneStringTrimmed);
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String status) {
        String trimmedStatus = status.trim();
        return DONE_STATUS.equals(trimmedStatus) || NOT_DONE_STATUS.equals(trimmedStatus);
    }

    /**
     * Returns the completion state of the status.
     *
     * @return {@code true} if the status is marked as done, {@code false} otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return isDone ? DONE_STATUS : NOT_DONE_STATUS;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status otherStatus)) {
            return false;
        }

        return otherStatus.isDone == isDone;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isDone);
    }
}
