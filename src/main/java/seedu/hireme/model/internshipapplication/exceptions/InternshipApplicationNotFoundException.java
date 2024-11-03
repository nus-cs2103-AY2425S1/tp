package seedu.hireme.model.internshipapplication.exceptions;

import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;

/**
 * Signals that the operation is unable to find the specified internship application.
 */
public class InternshipApplicationNotFoundException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(InternshipApplicationNotFoundException.class);

    /**
     * Creates an exception that is thrown when the user tries to interact with an internship application that does not
     *     exist.
     */
    public InternshipApplicationNotFoundException() {
        super("Unable to find this internship application");
        logger.warning("Failed to find the specified internship application.");
    }
}


