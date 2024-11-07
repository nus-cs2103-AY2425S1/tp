package seedu.address.logic.parser.contact.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.contact.commands.AddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.role.Role;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_ROLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            // There are fields that are missing
            String missingFields = getMissingFieldsMessage(argMultimap, PREFIX_NAME,
                                        PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL);
            String errorMessage = missingFields.isEmpty()
                                    ? AddCommand.MESSAGE_USAGE
                                    : AddCommand.MESSAGE_USAGE + "\n"
                                        + AddCommand.MESSAGE_MISSING_FIELDS + ": " + missingFields;
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, errorMessage));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TELEGRAM);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        TelegramUsername telegramUsername = ParserUtil
                .parseTeleOnAdd(argMultimap.getValue(PREFIX_TELEGRAM).orElse(null)); // null to represent input absent

        Set<Role> roleList = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));

        Person person = new Person(name, phone, email, address, telegramUsername, roleList);

        return new AddCommand(person);
    }

    private static String getMissingFieldsMessage(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .filter(prefix -> !argumentMultimap.getValue(prefix).isPresent())
                .map(AddCommandParser::getFieldName) // Call helper method to get field name
                .collect(Collectors.joining(", "));
    }

    private static String getFieldName(Prefix prefix) {
        if (prefix.equals(PREFIX_NAME)) {
            return "n/NAME";
        } else if (prefix.equals(PREFIX_PHONE)) {
            return "p/PHONE NUMBER";
        } else if (prefix.equals(PREFIX_EMAIL)) {
            return "e/EMAIL";
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            return "a/ADDRESS";
        }
        // should not reach here
        return "ERROR HAS OCCURRED";
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
