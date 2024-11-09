package seedu.hiredfiredpro.logic.parser;

import static seedu.hiredfiredpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hiredfiredpro.logic.commands.SortCommand;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String MESSAGE_INVALID_ORDER = "Sort order '%s' is not valid. "
            + "Valid orders are 'a' for ascending or 'd' for descending.";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Parse the order parameter
        String order = parseOrder(trimmedArgs);

        return new SortCommand(order);
    }

    /**
     * Parses and validates the sort order from the input string.
     * @throws ParseException if the order is invalid
     */
    private String parseOrder(String order) throws ParseException {
        if (!order.equals(SortCommand.ASCENDING_SHORT) && !order.equals(SortCommand.DESCENDING_SHORT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_ORDER, order));
        }
        return order;
    }
}
