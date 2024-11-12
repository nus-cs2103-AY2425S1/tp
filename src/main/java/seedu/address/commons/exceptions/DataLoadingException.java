package seedu.address.commons.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents an error during loading of data from a file.
 */
public class DataLoadingException extends Exception {
    private static final Logger logger = LogsCenter.getLogger(DataLoadingException.class);

    /**
     * Constructs a new {@code DataLoadingException} with the specified detail {@code cause}.
     */
    public DataLoadingException(Exception cause) {
        super(cause);
        logger.info(String.format("Data Loading Exception: [%s]", cause));
    }

}
