package seedu.address.model.profile.exceptions;

import seedu.address.model.profile.Profile;

/**
 * Exception thrown when a profile path violates the constraints defined in {@link Profile}.
 */
public class IllegalProfilePathException extends RuntimeException {
    public IllegalProfilePathException() {
        super("Invalid file path. Only files following the pattern data/{profileName}.json are supported.");
    }
}
