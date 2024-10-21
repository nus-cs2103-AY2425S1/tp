package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.academyassist.logic.commands.AddCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;

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
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL,
                        CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_IC, CliSyntax.PREFIX_SUBJECT,
                        CliSyntax.PREFIX_YEARGROUP);

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ADDRESS,
                CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_IC, CliSyntax.PREFIX_SUBJECT, CliSyntax.PREFIX_YEARGROUP,
                CliSyntax.PREFIX_EMAIL) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL,
                CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_IC, CliSyntax.PREFIX_YEARGROUP);
        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        Ic ic = ParserUtil.parseIc(argMultimap.getValue(CliSyntax.PREFIX_IC).get());
        StudentId studentId = StudentId.TEMPORARY_STUDENT_ID;
        YearGroup yg = ParserUtil.parseYearGroup(argMultimap.getValue(CliSyntax.PREFIX_YEARGROUP).get());
        Set<Subject> subjects = ParserUtil.parseSubjects(argMultimap.getAllValues(CliSyntax.PREFIX_SUBJECT));

        Person person = new Person(name, phone, email, address, ic, yg, studentId, subjects);

        return new AddCommand(person);
    }

}
