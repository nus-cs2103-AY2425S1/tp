package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.trim().split(" ");
        if (splitArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String parameter = splitArgs[0];
        String order = splitArgs[1];

        if (!SortCommand.isValidParameter(parameter)) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_PARAMETER);
        }

        if (!SortCommand.isValidOrder(order)) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
        }

        return new SortCommand(parameter, order);
    }
}
