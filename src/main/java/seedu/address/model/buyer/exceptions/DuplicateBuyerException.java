package seedu.address.model.buyer.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Signals that the operation will result in duplicate Buyers (Buyers are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBuyerException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(DuplicateBuyerException.class);

    /**
     * Constructs a new {@code DuplicateBuyerException}.
     */
    public DuplicateBuyerException() {
        super("Operation would result in duplicate buyers");
        logger.info("Operation would result in duplicate buyers");
    }
}
