package seedu.address.model.person.exceptions;

public class ConflictMeetingException extends RuntimeException {
    public ConflictMeetingException() {
        super("Operation would result in conflicting meetings");
    }
}
