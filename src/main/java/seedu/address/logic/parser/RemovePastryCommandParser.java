package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemovePastryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemovePastryCommand} object.
 */
public class RemovePastryCommandParser implements Parser<RemovePastryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemovePastryCommand}
     * and returns a {@code RemovePastryCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RemovePastryCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim the input arguments and ensure they are not empty
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePastryCommand.MESSAGE_USAGE));
        }

        // Return the RemovePastryCommand with the provided pastry name
        return new RemovePastryCommand(trimmedArgs);
    }
}