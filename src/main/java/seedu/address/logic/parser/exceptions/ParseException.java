package seedu.address.logic.parser.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {
    private static final Logger logger = LogsCenter.getLogger(ParseException.class);

    /**
     * Constructs a new {@code ParseException} with the specified detail {@code message}.
     */
    public ParseException(String message) {
        super(message);
        logger.info("Parse Exception");

    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
