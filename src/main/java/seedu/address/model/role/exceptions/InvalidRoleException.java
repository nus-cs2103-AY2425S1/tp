package seedu.address.model.role.exceptions;

import seedu.address.model.role.RoleHandler;

/**
 * Signals that the operation is unable to find the specified role.
 */
public class InvalidRoleException extends Exception {
    public InvalidRoleException() {
        super(RoleHandler.MESSAGE_CONSTRAINTS);
    }
}
