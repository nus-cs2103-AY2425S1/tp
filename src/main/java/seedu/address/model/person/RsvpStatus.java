package seedu.address.model.person;

/**
 * Represents the RSVP status of a person.
 * Pending - User has not responded to the invitation
 * Coming - User has confirmed attendance
 * Not Coming - User has declined the invitation
 */
public enum RsvpStatus {
    PENDING("Pending"),
    COMING("Coming"),
    NOT_COMING("Not Coming");;

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
}
