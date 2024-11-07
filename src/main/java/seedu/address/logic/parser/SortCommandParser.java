package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.ParserUtil.SortAttribute;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        SortAttribute sortAttribute;

        try {
            sortAttribute = ParserUtil.parseSortAttribute(args);
        } catch (IllegalValueException ive) {
            logger.log(Level.WARNING, "Exception caught when parsing for SortCommand.");
            throw new ParseException(ive.getMessage() + "\n" + SortCommand.MESSAGE_USAGE, ive);
        }

        assert sortAttribute != null;
        logger.log(Level.INFO, "Parsed sort command without exception.");
        return new SortCommand(sortAttribute);
    }
}
