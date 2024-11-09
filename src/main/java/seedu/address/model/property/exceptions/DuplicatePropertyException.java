package seedu.address.model.property.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Signals that the operation will result in duplicate Properties (Properties are considered duplicates if they have the
 * same identity).
 */
public class DuplicatePropertyException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(DuplicatePropertyException.class);

    /**
     * Constructs a new {@code DuplicatePropertyException}.
     */
    public DuplicatePropertyException() {
        super("Operation would result in duplicate properties");
        logger.info("Operation would result in duplicate properties");
    }
}
