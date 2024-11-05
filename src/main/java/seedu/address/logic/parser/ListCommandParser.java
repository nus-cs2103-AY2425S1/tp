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

        // Tokenize the arguments and perform initial validation checks
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_KEY);

        validateArgumentsFormat(argMultimap, args);

        // Parse the key argument
        String keyArg = argMultimap.getValue(PREFIX_KEY).orElse("");
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
            logger.warning("Invalid key argument: " + keyArg);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates the format of the arguments in the ArgumentMultimap.
     *
     * @param argMultimap The tokenized arguments map.
     * @param args        The original input arguments string.
     * @throws ParseException if there are duplicate prefixes, missing prefixes, or excess tokens.
     */
    private void validateArgumentsFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        requireNonNull(argMultimap, "ArgumentMultimap cannot be null.");

        // Check for duplicate PREFIX_KEY
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_KEY);

        // Check for excess tokens beyond the specified prefix
        if (ParserUtil.hasExcessToken(args, PREFIX_KEY)) {
            logger.warning("Excess tokens detected in input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // Check if PREFIX_KEY is present
        if (argMultimap.getValue(PREFIX_KEY).isEmpty()) {
            logger.warning("Missing required PREFIX_KEY in input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
