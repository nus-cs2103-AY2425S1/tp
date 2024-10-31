package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddBuyerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
/**
 * Parses input arguments and creates a new {@link AddBuyerCommand} object.
 * The parser processes the input string to extract the necessary parameters
 * (name, phone, and email) for creating a {@link Buyer}.
 */
public class AddBuyerCommandParser implements Parser<AddBuyerCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddBuyerCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddBuyerCommand}
     * and returns an {@code AddBuyerCommand} object for execution.
     *
     * @param args The input arguments from the user.
     * @return An {@code AddBuyerCommand} that adds a buyer based on the parsed input.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddBuyerCommand parse(String args) throws ParseException {
        // Tokenize the input arguments based on the expected prefixes (name, phone, email)
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        if (hasExcessToken(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
            logger.warning("Excess prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBuyerCommand.MESSAGE_USAGE));
        }

        // Check if all required prefixes are present and if the preamble is empty
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBuyerCommand.MESSAGE_USAGE));
        }

        // Verify there are no duplicate prefixes in the input


        // Parse the name, phone, and email from the argument map
        Name name = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parseClientPhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseClientEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        // Create a new Buyer using the parsed data
        Buyer buyer = new Buyer(name, phone, email);
        return new AddBuyerCommand(buyer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The argument multimap that holds the parsed arguments.
     * @param prefixes The prefixes to check for presence.
     * @return True if all prefixes contain non-empty values, false otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


    /**
     * Returns true if number of tokens in args string exceeds specified prefixes.
     */
    private boolean hasExcessToken(String args, Prefix... prefixes) {
        String[] splits = args.trim().split("\\s(?=\\S+/)");
        if (splits[0].equals("/")) {
            return false;
        }
        return splits.length > prefixes.length;
    }
}
