package seedu.hireme.model.internshipapplication.exceptions;

import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;

/**
 * Signals that the operation will result in duplicate internship applications.
 */
public class DuplicateInternshipApplicationException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(DuplicateInternshipApplicationException.class);

    /**
     * Creates an exception that is thrown when the user tries to insert a duplicate internship application.
     */
    public DuplicateInternshipApplicationException() {
        super("Duplicate internship applications are not allowed");
        logger.warning("Tried to insert a duplicate internship application.");
    }
}

