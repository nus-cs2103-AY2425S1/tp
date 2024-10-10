package seedu.address.model.person.exceptions;

/**
 * Signals that the user is trying to create a group with a name that is already used by another group.
 */
public class DuplicateGroupException extends RuntimeException {
    public DuplicateGroupException() {
        super("This group name already exists");
    }
}
