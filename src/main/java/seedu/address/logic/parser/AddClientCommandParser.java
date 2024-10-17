package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddClientCommand object
 */
public class AddClientCommandParser implements Parser<AddClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClientCommand
     * and returns an AddClientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !(arePrefixesPresent(argMultimap, PREFIX_PHONE) || arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Client client = null;

        if (isPhonePresent(argMultimap, PREFIX_PHONE) && isEmailPresent(argMultimap, PREFIX_EMAIL)) {
            Phone phone = ParserUtil.parsePhoneAdd(argMultimap.getValue(PREFIX_PHONE).get());
            Email email = ParserUtil.parseEmailAdd(argMultimap.getValue(PREFIX_EMAIL).get());
            client = new Client(name, phone, email, tagList);
        } else if (isPhonePresent(argMultimap, PREFIX_PHONE) && !isEmailPresent(argMultimap, PREFIX_EMAIL)) {
            Phone phone = ParserUtil.parsePhoneAdd(argMultimap.getValue(PREFIX_PHONE).get());
            Email email = new Email();
            client = new Client(name, phone, email, tagList);
        } else if (!isPhonePresent(argMultimap, PREFIX_PHONE) && isEmailPresent(argMultimap, PREFIX_EMAIL)) {
            Email email = ParserUtil.parseEmailAdd(argMultimap.getValue(PREFIX_EMAIL).get());
            Phone phone = new Phone();
            client = new Client(name, phone, email, tagList);
        }

        return new AddClientCommand(client);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean isPhonePresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

    private boolean isEmailPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
