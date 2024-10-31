package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddSellerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;

/**
 * Parses input arguments and creates a new {@link AddSellerCommand} object.
 * The parser processes the input string to extract the necessary parameters
 * (name, phone, and email) for creating a {@link Seller}.
 */
public class AddSellerCommandParser implements Parser<AddSellerCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddSellerCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddSellerCommand}
     * and returns an {@code AddSellerCommand} object for execution.
     *
     * @param args The input arguments from the user.
     * @return An {@code AddSellerCommand} that adds a seller based on the parsed input.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddSellerCommand parse(String args) throws ParseException {
        // Tokenize the input arguments based on the expected prefixes (name, phone, email)
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        if (ParserUtil.hasExcessToken(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
            logger.warning("Excess prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE));
        }

        // Check if all required prefixes are present and if the preamble is empty
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE));
        }
        // Parse the name, phone, and email from the argument map
        Name name = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parseClientPhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseClientEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        // Create a new Seller using the parsed data
        Seller seller = new Seller(name, phone, email);
        return new AddSellerCommand(seller);
    }
}
