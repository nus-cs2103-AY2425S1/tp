package seedu.hireme.model.internshipapplication;

/**
 * Represents the status of an internship application.
 * The status can be one of the following:
 * <ul>
 *     <li>PENDING - The application is awaiting review or further action by the interviewee.</li>
 *     <li>ACCEPTED - The application has been accepted, indicating a successful outcome for the interviewee.</li>
 *     <li>REJECTED - The application has been rejected, indicating an unsuccessful outcome for the interviewee.</li>
 * </ul>
 */
public enum Status {
    /**
     * Represents an internship application that is awaiting review or further action.
     */
    PENDING("PENDING"),

    /**
     * Represents an internship application that has been accepted.
     */
    ACCEPTED("ACCEPTED"),

    /**
     * Represents an internship application that has been rejected.
     */
    REJECTED("REJECTED");

    public static final String MESSAGE_CONSTRAINTS =
            "Status can only take these values: PENDING, ACCEPTED, REJECTED";

    private final String value;

    /**
     * Constructs a {@code Status} with the specified value.
     *
     * @param s The string representation of the status.
     */
    Status(String s) {
        this.value = s;
    }

    /**
     * Returns a {@code Status} with the specified value.
     * This method allows the status to be case-insensitive.
     *
     * @param s The string representation of the status.
     * @return the status enum matching the provided string value.
     */
    public static Status getValueOf(String s) {
        return Status.valueOf(s.toUpperCase());
    }

    /**
     * Returns the string representation of the status.
     *
     * @return The string value of the status.
     */
    @Override
    public String toString() {
        return this.value;
    }
}
