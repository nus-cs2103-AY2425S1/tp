package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Set;

import seedu.academyassist.logic.commands.AddSubjectCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;


/**
 * Parses input arguments and creates a new AddSubjectCommand object
 */
public class AddSubjectCommandParser implements Parser<AddSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSubjectCommand
     * and returns an AddSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubjectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_SUBJECT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubjectCommand.MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getPreamble());

        try {
            Set<Subject> subjects = ParserUtil.parseSubjects(argMultimap.getAllValues(CliSyntax.PREFIX_SUBJECT));
            return new AddSubjectCommand(studentId, subjects);
        } catch (ParseException pe) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
    }
}
