package seedu.address.model.task;

/**
 * Enum class representing the status of a Task.
 * {@code MISSED}: Task was not completed on time.
 */
public enum Status {
    PENDING,
    COMPLETED,
    OVERDUE,
    COMPLETE_LATE;

    public static final String MESSAGE_CONSTRAINTS = "Status has to be either PENDING or COMPLETED";

    /**
     * Returns true if a given string is a valid Status.
     */
    public static boolean isValidStatus(String status) {
        try {
            Status.valueOf(status.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
