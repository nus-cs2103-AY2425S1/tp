package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CheckPastryStockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code CheckPastryStockCommand} object.
 */
public class CheckPastryStockCommandParser implements Parser<CheckPastryStockCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code CheckPastryStockCommand}
     * and returns a {@code CheckPastryStockCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CheckPastryStockCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim the input to remove extra spaces
        String trimmedArgs = args.trim();

        // Ensure that the input is not empty
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckPastryStockCommand.MESSAGE_USAGE));
        }

        // Create and return the CheckPastryStockCommand with the parsed pastry name
        return new CheckPastryStockCommand(trimmedArgs);
    }
}
