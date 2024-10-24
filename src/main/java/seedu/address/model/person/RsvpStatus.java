package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the RSVP status of a person.
 * Pending - User has not responded to the invitation
 * Coming - User has confirmed attendance
 * Not Coming - User has declined the invitation
 */
public enum RsvpStatus {
    PENDING("Pending"),
    COMING("Coming"),
    NOT_COMING("Not Coming");

    public static final String MESSAGE_CONSTRAINTS =
            "rsvpStatus should only be 1 (Coming), 2 (Not Coming) or 3 (Pending).";

    private final String status;

    RsvpStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

    /**
     * Returns an RsvpStatus with the given input status from the user (1, 2, 3)
     */
    public static RsvpStatus of(String status) {
        requireNonNull(status);
        checkArgument(checkValidStatus(status), MESSAGE_CONSTRAINTS);
        switch (status) {
        case "1":
            return COMING;
        case "2":
            return NOT_COMING;
        case "3":
            return PENDING;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if the given status is a valid status (1, 2 or 3)
     */
    public static boolean checkValidStatus(String status) {
        requireNonNull(status);
        switch (status) {
        case "1":
        case "2":
        case "3":
            return true;
        default:
            return false;
        }
    }
}
