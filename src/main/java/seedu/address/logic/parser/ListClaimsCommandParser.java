package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListClaimsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the ListClaimsCommand
 * and returns a ListClaimsCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class ListClaimsCommandParser implements Parser<ListClaimsCommand> {

    /**
     * Parses the given arguments and creates a {@code ListClaimsCommand} instance.
     *
     * This method takes a string of arguments, attempts to parse it into an {@code Index} object,
     * and constructs a new {@code ListClaimsCommand} using the parsed index. If the parsing fails,
     * it throws a {@code ParseException} with an appropriate error message.
     *
     * @param args The string arguments to parse. This should represent the index of the claims to list.
     * @return A {@code ListClaimsCommand} instance created with the parsed index.
     * @throws ParseException if the arguments are in an invalid format or cannot be parsed into an index.
     */
    public ListClaimsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListClaimsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE), pe);
        }
    }
}
