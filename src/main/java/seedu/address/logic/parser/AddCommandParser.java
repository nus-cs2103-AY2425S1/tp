package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClassId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fees;
import seedu.address.model.person.MonthPaid;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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
                        PREFIX_FEES, PREFIX_CLASSID, PREFIX_TAG);

        // check if preamble is empty
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(getPreambleErrorString());
        }

        // check if prefixes are not specified
        // Note: if prefixes are specified, null values are accepted
        Set<Prefix> missingPrefixes = getMissingPrefixes(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_FEES, PREFIX_CLASSID);
        if (!missingPrefixes.isEmpty()) {
            String missingPrefixesString = missingPrefixes
                    .stream()
                    .map(Prefix::getPrefix)
                    .reduce((curr, next) -> curr + " " + next)
                    .orElseThrow();
            throw new ParseException(getMissingPrefixesErrorString(missingPrefixesString));
        }

        // check for duplicate prefixes for prefixes that should have no duplicates
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_FEES, PREFIX_CLASSID);

        // single-value prefixes
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Fees fees = ParserUtil.parseFees(argMultimap.getValue(PREFIX_FEES).get());
        ClassId classId = ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get());
        // multi-value prefixes
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // prefix that cannot be set on Add
        Set<MonthPaid> monthsPaid = Collections.emptySet();

        Person person = new Person(name, phone, email, address, fees, classId, monthsPaid, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns a set of the prefixes which are not contained in {@code argumentMultimap}.
     */
    private static Set<Prefix> getMissingPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        Set<Prefix> missingPrefixes = new LinkedHashSet<>(); // to preserve insertion order
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getValue(prefix).isEmpty()) {
                missingPrefixes.add(prefix);
            }
        }
        return missingPrefixes;
    }

    /**
     * Returns the error string associated with a non-empty preamble.
     */
    public static String getPreambleErrorString() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddCommand.MESSAGE_NONEMPTY_PREAMBLE, AddCommand.MESSAGE_USAGE));
    }

    /**
     * Returns the error string associated with missing prefixes.
     */
    public static String getMissingPrefixesErrorString(String missingPrefixesString) {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, missingPrefixesString, AddCommand.MESSAGE_USAGE));
    }
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof AddCommandParser)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        return true;
    }

}
