package seedu.address.model.person.exceptions;

public class DuplicateMeetingException extends RuntimeException {
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
