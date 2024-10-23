package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterPaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.predicate.StudentHasPaidPredicate;

/**
 * Parses input arguments and creates a new FilterPaidCommand object
 */
public class FilterPaidCommandParser implements Parser<FilterPaidCommand> {
    private static final String HAS_PAID = "true";
    private static final String HAS_NOT_PAID = "false";
    /**
     * Parses the given {@code String} of arguments in the context of the FilterPaidCommand
     * and returns a FilterPaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterPaidCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidCommand.MESSAGE_USAGE));
        }

        FilterPaidCommand filterPaidCommand = null;

        switch (trimmedArgs) {
        case HAS_PAID:
            filterPaidCommand = new FilterPaidCommand(new StudentHasPaidPredicate(true));
            break;
        case HAS_NOT_PAID:
            filterPaidCommand = new FilterPaidCommand(new StudentHasPaidPredicate(false));
            break;
        default:
            break;
        }

        // Arguments may not fit in either HAS_PAID or HAS_NOT_PAID
        if (filterPaidCommand == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidCommand.MESSAGE_USAGE));
        }

        return filterPaidCommand;
    }
}
