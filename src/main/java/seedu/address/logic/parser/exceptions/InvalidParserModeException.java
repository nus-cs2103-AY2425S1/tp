package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser when trying to switch to a non-existent mode.
 */
public class InvalidParserModeException extends IllegalValueException {

    public InvalidParserModeException(String message) {
        super(message);
    }

    public InvalidParserModeException(String message, Throwable cause) {
        super(message, cause);
    }
}
