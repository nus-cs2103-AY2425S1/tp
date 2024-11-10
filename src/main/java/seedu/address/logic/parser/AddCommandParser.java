package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.areAllPrefixesPresent;
import static seedu.address.logic.parser.ParserUtil.areAnyPrefixesPresent;

import java.util.Optional;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String MESSAGE_MISSING_NAME = "Missing required field for name.";
    public static final String MESSAGE_MISSING_PHONE_OR_EMAIL =
            "At least one contact method (phone or email) is required.";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_MODULE, PREFIX_DESCRIPTION);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getErrorMessageWithUsage(Messages.MESSAGE_UNEXPECTED_PREAMBLE,
                    AddCommand.MESSAGE_USAGE));
        }

        if (!areAllPrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_MISSING_NAME,
                    AddCommand.MESSAGE_USAGE));
        }

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_MISSING_PHONE_OR_EMAIL,
                    AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_DESCRIPTION);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Phone phone = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }

        Email email = null;
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }

        Address address = null;
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        ModuleRoleMap moduleRoleMap = ParserUtil.parseModuleRoleMap(argMultimap.getAllValues(PREFIX_MODULE));

        Description description = null;
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }

        Person person = new Person(
                name,
                Optional.ofNullable(phone),
                Optional.ofNullable(email),
                Optional.ofNullable(address),
                tagList,
                moduleRoleMap,
                Optional.ofNullable(description)
        );
        return new AddCommand(person);
    }
}
