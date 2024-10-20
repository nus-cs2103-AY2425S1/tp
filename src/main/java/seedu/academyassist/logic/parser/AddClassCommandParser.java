package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Set;

import seedu.academyassist.logic.commands.AddClassCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;


/**
 * Parses input arguments and creates a new AddClassCommand object
 */
public class AddClassCommandParser implements Parser<AddClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClassCommand
     * and returns an AddClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClassCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_SUBJECT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT); // Only can add one class at a time.
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getPreamble());
        Set<Subject> subjects = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

        // Verify that there is exactly one subject
        if (subjects.size() != 1) {
            throw new ParseException("Exactly one subject must be provided.");
        }

        // Assign the subject to subject variable
        Subject subject = subjects.iterator().next();
        return new AddClassCommand(studentId, subject);
    }
}
