package tahub.contacts.logic.parser.attend;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_MISSING_FIELDS;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static tahub.contacts.logic.parser.ParserUtil.arePrefixesPresent;

import tahub.contacts.logic.commands.attend.AttendClearCommand;
import tahub.contacts.logic.parser.ArgumentMultimap;
import tahub.contacts.logic.parser.ArgumentTokenizer;
import tahub.contacts.logic.parser.Parser;
import tahub.contacts.logic.parser.ParserUtil;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * Parses input arguments and crates a new {@link AttendClearCommand} object.
 */
public class AttendClearCommandParser implements Parser<AttendClearCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@link AttendClearCommand}
     * and returns an {@link AttendClearCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AttendClearCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICULATION_NUMBER, PREFIX_CODE, PREFIX_TUTORIAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRICULATION_NUMBER, PREFIX_CODE, PREFIX_TUTORIAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_MISSING_FIELDS, AttendClearCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_MATRICULATION_NUMBER).isPresent();
        assert argMultimap.getValue(PREFIX_CODE).isPresent();
        assert argMultimap.getValue(PREFIX_TUTORIAL).isPresent();

        // parse each of the values: trim and check for correct format for each.
        MatriculationNumber matricNumber = ParserUtil
                .parseMatriculationNumber(argMultimap.getValue(PREFIX_MATRICULATION_NUMBER).get());
        CourseCode courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_CODE).get());
        String tutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(PREFIX_TUTORIAL).get());

        Course course = new Course(courseCode, new CourseName("COURSE QUERY"));
        Tutorial tutorial = new Tutorial(tutorialId, course);

        StudentCourseAssociation targetSca = new StudentCourseAssociation(
                Person.genericFromMatricNumber(matricNumber),
                course, tutorial);

        return new AttendClearCommand(targetSca);
    }


}
