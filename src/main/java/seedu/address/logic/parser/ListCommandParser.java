package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEY;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(ListCommandParser.class);

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
        logger.info("Parsing list command with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_KEY);
        String keyArg = argMultimap.getValue(PREFIX_KEY).orElse("");

        if (keyArg.isEmpty()) {
            logger.warning("No key argument provided for list command");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        String keyArgLower = keyArg.toLowerCase();
        logger.info("Key argument received: " + keyArgLower);

        // Switch case to handle different key values with assertions
        switch (keyArgLower) {
        case ListClientsCommand.KEY_WORD:
            assert keyArgLower.equals("clients")
                    : "Key argument must be 'clients' for ListClientsCommand to be created";
            logger.info("Creating ListClientsCommand");
            return new ListClientsCommand();

        case ListBuyersCommand.KEY_WORD:
            assert keyArgLower.equals("buyers")
                    : "Key argument must be 'buyers' for ListBuyersCommand to be created";
            logger.info("Creating ListBuyersCommand");
            return new ListBuyersCommand();

        case ListSellersCommand.KEY_WORD:
            assert keyArgLower.equals("sellers")
                    : "Key argument must be 'sellers' for ListSellersCommand to be created";
            logger.info("Creating ListSellersCommand");
            return new ListSellersCommand();

        case ListPropertiesCommand.KEY_WORD:
            assert keyArgLower.equals("properties")
                    : "Key argument must be 'properties' for ListPropertiesCommand to be created";
            logger.info("Creating ListPropertiesCommand");
            return new ListPropertiesCommand();

        case ListMeetingsCommand.KEY_WORD:
            assert keyArgLower.equals("meetings")
                    : "Key argument must be 'meetings' for ListMeetingsCommand to be created";
            logger.info("Creating ListMeetingsCommand");
            return new ListMeetingsCommand();

        default:
            // Assert that the keyArg is not any of the valid keys
            assert !keyArgLower.equals(ListClientsCommand.KEY_WORD.toLowerCase())
                    && !keyArgLower.equals(ListBuyersCommand.KEY_WORD.toLowerCase())
                    && !keyArgLower.equals(ListSellersCommand.KEY_WORD.toLowerCase())
                    && !keyArgLower.equals(ListPropertiesCommand.KEY_WORD.toLowerCase())
                    && !keyArgLower.equals(ListMeetingsCommand.KEY_WORD.toLowerCase())
                    : "Key argument should not match any valid keys: "
                    + ListClientsCommand.KEY_WORD + ", "
                    + ListBuyersCommand.KEY_WORD + ", "
                    + ListSellersCommand.KEY_WORD + ", "
                    + ListPropertiesCommand.KEY_WORD + ", "
                    + ListMeetingsCommand.KEY_WORD;

            logger.warning("Invalid key argument: " + keyArg);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
