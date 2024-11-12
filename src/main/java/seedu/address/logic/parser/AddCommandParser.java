package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STUDENTID, PREFIX_NETID,
                        PREFIX_MAJOR, PREFIX_GROUP, PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_STUDENTID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_STUDENTID,
                PREFIX_NETID, PREFIX_MAJOR, PREFIX_YEAR);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get());
        Year year = parseOptionalYear(argMultimap);
        Major major = parseOptionalMajor(argMultimap);
        Email email = parseOptionalNetId(argMultimap);
        GroupList groupList = ParserUtil.parseGroups(argMultimap.getAllValues(PREFIX_GROUP));

        long inputGroups = argMultimap.countPrefixesOf(PREFIX_GROUP);

        if (inputGroups > 1 && inputGroups > groupList.size()) {
            throw new ParseException(Messages.MESSAGE_DUPLICATE_GROUPS);
        }

        Comment comment = new Comment("");

        Person person = new Person(name, studentId, email, major, groupList, year, comment);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the prefix contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

    private static Year parseOptionalYear(ArgumentMultimap argumentMultimap) throws ParseException {
        assert argumentMultimap != null;

        Year year = Year.makeYear("");

        if (isPrefixPresent(argumentMultimap, PREFIX_YEAR)) {
            year = ParserUtil.parseYear(argumentMultimap.getValue(PREFIX_YEAR).get());
        }

        return year;
    }

    private static Major parseOptionalMajor(ArgumentMultimap argumentMultimap) throws ParseException {
        assert argumentMultimap != null;

        Major major = Major.makeMajor("");

        if (isPrefixPresent(argumentMultimap, PREFIX_MAJOR)) {
            major = ParserUtil.parseMajor(argumentMultimap.getValue(PREFIX_MAJOR).get());
        }

        return major;
    }

    private static Email parseOptionalNetId(ArgumentMultimap argumentMultimap) throws ParseException {
        assert argumentMultimap != null;

        Email email = Email.makeEmail("");

        if (isPrefixPresent(argumentMultimap, PREFIX_NETID)) {
            email = ParserUtil.parseNetId(argumentMultimap.getValue(PREFIX_NETID).get());
        }

        return email;
    }
}
