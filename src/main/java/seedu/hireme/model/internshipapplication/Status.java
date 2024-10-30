package seedu.hireme.model.internshipapplication;

/**
 * Represents the status of an internship application.
 * The status can be one of the following:
 * <ul>
 *     <li>PENDING - The application is under review and has not yet received a decision.</li>
 *     <li>ACCEPTED - The application has been reviewed and accepted.</li>
 *     <li>REJECTED - The application has been reviewed and rejected.</li>
 * </ul>
 */
public enum Status {
    /**
     * Represents an internship application that is pending review.
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
     * Returns the string representation of the status.
     *
     * @return The string value of the status.
     */
    @Override
    public String toString() {
        return this.value;
    }
}
