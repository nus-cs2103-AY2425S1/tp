package seedu.address.model.appointment;

/**
 * Represents the status of an appointment in the address book.
 */
public enum Status {

    COMPLETED,
    PENDING;

    public static final String MESSAGE_CONSTRAINTS = "Status should only be 'COMPLETED' or 'PENDING'";

    /**
     * Checks if the given string matches any of the defined statuses (case-insensitive).
     *
     * @param status The status string to check.
     * @return true if the status is valid, false otherwise.
     */
    public static boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }

        try {
            Status.valueOf(status.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
