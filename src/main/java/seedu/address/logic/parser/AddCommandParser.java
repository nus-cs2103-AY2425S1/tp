package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACTTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContactType;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TelegramHandle;
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
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACTTYPE, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_TELEHANDLE, PREFIX_MOD, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONTACTTYPE, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_TELEHANDLE, PREFIX_CONTACTTYPE, PREFIX_MOD, PREFIX_REMARK);

        // Check for at least one of phone, email, or telegram handle before parsing individual values
        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()
                && !argMultimap.getValue(PREFIX_EMAIL).isPresent()
                && !argMultimap.getValue(PREFIX_TELEHANDLE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // parse required fields
        ContactType contactType = ParserUtil.parseContactType(argMultimap.getValue(PREFIX_CONTACTTYPE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        // parse optional fields
        Optional<Phone> phone = argMultimap.getValue(PREFIX_PHONE).isPresent()
                ? Optional.of(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()))
                : Optional.empty();

        Optional<Email> email = argMultimap.getValue(PREFIX_EMAIL).isPresent()
                ? Optional.of(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()))
                : Optional.empty();

        Optional<TelegramHandle> telegramHandle = argMultimap.getValue(PREFIX_TELEHANDLE).isPresent()
                ? Optional.of(ParserUtil.parseTelegramHandle(argMultimap.getValue(PREFIX_TELEHANDLE).get()))
                : Optional.empty();

        Optional<ModuleName> moduleName = argMultimap.getValue(PREFIX_MOD).isPresent()
                ? Optional.of(ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_MOD).get()))
                : Optional.empty();

        Optional<Remark> remark = argMultimap.getValue(PREFIX_REMARK).isPresent()
                ? Optional.of(ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()))
                : Optional.empty();

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(contactType, name, phone, email, telegramHandle, moduleName, remark, tagList);
        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
