package seedu.address.model.person.exceptions;

/**
 * Signals that the user the group is trying to find does not exist.
 */
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException() {
        super("Group with that name does not exist");
    }
}
