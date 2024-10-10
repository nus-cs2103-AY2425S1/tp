package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_CONTACT_TYPE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.model.person.ContactType.VENDOR;
import static seedu.ddd.model.person.ContactType.CLIENT;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import seedu.ddd.logic.commands.AddContactCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.person.Address;
import seedu.ddd.model.person.Client;
import seedu.ddd.model.person.Contact;
import seedu.ddd.model.person.ContactType;
import seedu.ddd.model.person.Date;
import seedu.ddd.model.person.Email;
import seedu.ddd.model.person.Name;
import seedu.ddd.model.person.Phone;
import seedu.ddd.model.person.Service;
import seedu.ddd.model.person.Vendor;
import seedu.ddd.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_SERVICE, PREFIX_DATE);

        ContactType contactType;
        String contactTypeString = argMultimap.getPreamble();

        try {
            if (Objects.equals(contactTypeString, "client")) {
                contactType = CLIENT;
            } else if (Objects.equals(contactTypeString, "vendor")) {
                contactType = VENDOR;
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_CONTACT_TYPE, contactTypeString));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SERVICE)
                && contactType == VENDOR) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.VENDOR_MESSAGE_USAGE));
        } else if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE)
                && contactType == CLIENT) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.CLIENT_MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_SERVICE, PREFIX_DATE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Contact contact;

        if (contactType == VENDOR) {
            Service service = ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get());
            contact = new Vendor(name, phone, email, address, service, tagList);
        } else if (contactType == CLIENT) {
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            contact = new Client(name, phone, email, address, date, tagList);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_CONTACT_TYPE, contactTypeString));
        }

        return new AddContactCommand(contact);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
