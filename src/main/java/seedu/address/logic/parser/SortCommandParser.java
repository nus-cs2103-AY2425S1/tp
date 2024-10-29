package seedu.address.logic.parser;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        String[] keywords = trimmedArgs.split("\\s+");

        if (keywords.length != 2) {
            throw new ParseException(SortCommand.MESSAGE_USAGE);
        }

        String sortBy = keywords[0].toLowerCase();
        String order = keywords[1].toLowerCase();

        logger.fine("Keyword for sort: " + sortBy + ", Keyword for order: " + order);

        if (!(sortBy.equals("name") || sortBy.equals("deadline"))) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_KEYWORD);
        }

        boolean isAscending;
        if (order.equalsIgnoreCase("ascending")) {
            isAscending = true;
        } else if (order.equals("descending")) {
            isAscending = false;
        } else {
            logger.fine("These args caused a parse command(for SortCommandParser): " + args);
            throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
        }

        return new SortCommand(sortBy, isAscending);
    }
}

