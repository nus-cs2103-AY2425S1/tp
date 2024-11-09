package seedu.address.model.meetup.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Signals that the operation will result in duplicate Buyers (Buyers are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMeetUpException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(DuplicateMeetUpException.class);

    /**
     * Constructs a new {@code DuplicateMeetUpException}.
     */
    public DuplicateMeetUpException() {
        super("Operation would result in duplicate meetups");
        logger.info("Operation would result in duplicate meetups");
    }
}
