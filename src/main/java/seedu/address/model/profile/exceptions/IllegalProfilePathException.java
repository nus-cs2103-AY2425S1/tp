package seedu.address.model.profile.exceptions;

import seedu.address.model.profile.Profile;

/**
 * Exception thrown when a profile path violates the constraints defined in {@link Profile}.
 */
public class IllegalProfilePathException extends RuntimeException {
    public static final String ERR_MSG =
            "Invalid file path. The file must be a .json file located directly under a 'data' directory within the "
                    + "project root, following the pattern: data/{profileName}.json.";

    public IllegalProfilePathException() {
        super(ERR_MSG);
    }
}
