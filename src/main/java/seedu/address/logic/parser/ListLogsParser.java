package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

import seedu.address.logic.commands.ListLogsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IdentityNumber;

/**
 * Parses input arguments and creates a new ListLogsCommand object
 */
public class ListLogsParser implements Parser<ListLogsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListLogsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUMBER);

        // Ensure the prefix and value are present
        if (argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
        }

        try {
            // Parse the identity number
            IdentityNumber id = ParserUtil.parseIdentityNumber(
                    argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());

            // Return the ListLogsCommand with the parsed identity number
            return new ListLogsCommand(id);

        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
        }
    }
}
