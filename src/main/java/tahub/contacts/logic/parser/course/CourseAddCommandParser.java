package tahub.contacts.logic.parser.course;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import tahub.contacts.logic.commands.course.CourseAddCommand;
import tahub.contacts.logic.parser.ArgumentMultimap;
import tahub.contacts.logic.parser.ArgumentTokenizer;
import tahub.contacts.logic.parser.Parser;
import tahub.contacts.logic.parser.ParserUtil;
import tahub.contacts.logic.parser.Prefix;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;

/**
 * Parses input arguments and creates a new CourseCommand object
 */
public class CourseAddCommandParser implements Parser<CourseAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CourseCommand
     * and returns an CourseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseAddCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_COURSE_CODE).isPresent();
        assert argMultimap.getValue(PREFIX_NAME).isPresent();

        CourseCode courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());
        CourseName courseName = ParserUtil.parseCourseName(argMultimap.getValue(PREFIX_NAME).get());

        Course course = new Course(courseCode, courseName);
        return new CourseAddCommand(course);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
