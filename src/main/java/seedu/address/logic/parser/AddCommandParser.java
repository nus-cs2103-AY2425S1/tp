package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.clienttype.ClientType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.Reminder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final String MESSAGE_MISSING_FIELD = "The following field for add command is missing: %s";
    private static final String MESSAGE_MULTIPLE_MISSING_FIELDS =
            "The following fields for add command are missing: %s";

    // Map to store prefix to field description mapping
    private static final HashMap<Prefix, String> FIELD_DESCRIPTIONS = new HashMap<>();
    static {
        FIELD_DESCRIPTIONS.put(PREFIX_NAME, "NAME");
        FIELD_DESCRIPTIONS.put(PREFIX_PHONE, "PHONE");
        FIELD_DESCRIPTIONS.put(PREFIX_EMAIL, "EMAIL");
        FIELD_DESCRIPTIONS.put(PREFIX_ADDRESS, "ADDRESS");
        FIELD_DESCRIPTIONS.put(PREFIX_CLIENT_TYPE, "CLIENT_TYPE");
        FIELD_DESCRIPTIONS.put(PREFIX_DESCRIPTION, "DESCRIPTION");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_CLIENT_TYPE, PREFIX_DESCRIPTION);

        // Check for missing fields
        arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_CLIENT_TYPE, PREFIX_DESCRIPTION);

        // Only check for preamble if there are no missing fields
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DESCRIPTION);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<ClientType> clientTypeList = ParserUtil.parseClientTypes(argMultimap.getAllValues(PREFIX_CLIENT_TYPE));
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Reminder> reminders = Collections.emptySet();
        Person person = new Person(name, phone, email, address, clientTypeList, description, reminders);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static void arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes)
            throws ParseException {
        //return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());

        StringBuilder missingFields = new StringBuilder();

        for (Prefix prefix : prefixes) {
            if (prefix == PREFIX_CLIENT_TYPE) {
                if (argumentMultimap.getAllValues(prefix).isEmpty()) {
                    missingFields.append(String.format("%s%s ",
                            prefix.getPrefix(), FIELD_DESCRIPTIONS.get(prefix)));
                }
            } else if (!argumentMultimap.getValue(prefix).isPresent()) {
                missingFields.append(String.format("%s%s ",
                        prefix.getPrefix(), FIELD_DESCRIPTIONS.get(prefix)));
            }
        }

        // If any fields are missing, throw exception with specific message
        if (missingFields.length() > 0) {
            String missingFieldsStr = missingFields.toString().trim();
            String message = missingFieldsStr.contains(" ")
                    ? String.format(MESSAGE_MULTIPLE_MISSING_FIELDS, missingFieldsStr)
                    : String.format(MESSAGE_MISSING_FIELD, missingFieldsStr);
            throw new ParseException(message);
        }
    }

}
