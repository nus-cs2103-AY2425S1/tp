package seedu.address.model.profile.exceptions;

import seedu.address.model.profile.Profile;

/**
 * Exception thrown when a profile name violates the constraints defined in {@link Profile}.
 */
public class IllegalProfileNameException extends RuntimeException {
    public static final String ERR_MSG = "Invalid profile name. Please ensure the profile name fits our constraints";
    public IllegalProfileNameException() {
        super(ERR_MSG);
    }
}
