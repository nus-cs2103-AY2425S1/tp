package seedu.address.model.participation.exceptions;


public class DuplicateParticipationException extends RuntimeException {
    public DuplicateParticipationException() {
        super("Operation would result in duplicate participations");
    }
}
