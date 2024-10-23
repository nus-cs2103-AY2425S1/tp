package seedu.hireme.logic.parser;

import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.logic.commands.SortCommand;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.internshipapplication.DateComparator;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        boolean isInvalidCommand = trimmedArgs.isEmpty() || trimmedArgs.split("\\s+").length > 1;
        if (isInvalidCommand) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        boolean isAscendingOrder = ParserUtil.parseSortingOrder(trimmedArgs);
        String order = isAscendingOrder ? "ascending" : "descending";
        logger.info(String.format("Sorting list in %s order!", order));

        return new SortCommand(new DateComparator(isAscendingOrder));
    }

}
