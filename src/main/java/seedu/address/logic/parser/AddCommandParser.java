package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FAVOURITE_LABEL;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NOT_FAVOURITE_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NONFAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TELEGRAM,
                        PREFIX_ROLE, PREFIX_FAVOURITE, PREFIX_NONFAVOURITE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_FAVOURITE).isPresent()
                && argMultimap.getValue(PREFIX_NONFAVOURITE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_FAVOURITE).isPresent()
                && !argMultimap.getAllValues(PREFIX_FAVOURITE).get(0).isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FAVOURITE_LABEL);
        }

        if (argMultimap.getValue(PREFIX_NONFAVOURITE).isPresent()
                && !argMultimap.getAllValues(PREFIX_NONFAVOURITE).get(0).isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_NOT_FAVOURITE_LABEL);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_TELEGRAM, PREFIX_FAVOURITE, PREFIX_NONFAVOURITE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Telegram telegram = ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get());
        Set<Role> roleList = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));
        Set<Attendance> emptyAttendanceList = new HashSet<>();
        FavouriteStatus isFavourite = argMultimap.getValue(PREFIX_FAVOURITE).isPresent()
                ? FavouriteStatus.FAVOURITE
                : FavouriteStatus.NOT_FAVOURITE;

        Person person = new Person(name, phone, email, telegram, roleList, emptyAttendanceList, isFavourite);

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
