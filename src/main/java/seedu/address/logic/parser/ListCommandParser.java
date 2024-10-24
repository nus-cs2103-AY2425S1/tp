package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEY;

import seedu.address.logic.commands.ListBuyersCommand;
import seedu.address.logic.commands.ListClientsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListMeetingsCommand;
import seedu.address.logic.commands.ListPropertiesCommand;
import seedu.address.logic.commands.ListSellersCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ListCommand} object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListCommand}
     * and returns a {@code ListCommand} object for execution.
     *
     * @param args The input arguments to parse.
     * @return A {@code ListCommand} object based on the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format or the key is invalid.
     */
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_KEY);

        String keyArg = argMultimap.getValue(PREFIX_KEY).orElse("");
        if (keyArg.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // Switch case to handle different key values
        switch (keyArg.toLowerCase()) {
        case ListClientsCommand.KEY_WORD:
            return new ListClientsCommand();
        case ListBuyersCommand.KEY_WORD:
            return new ListBuyersCommand();
        case ListSellersCommand.KEY_WORD:
            return new ListSellersCommand();
        case ListPropertiesCommand.KEY_WORD:
            return new ListPropertiesCommand();
        case ListMeetingsCommand.KEY_WORD:
            return new ListMeetingsCommand();
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
