package tahub.contacts.logic.parser;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_NAME;
import static tahub.contacts.model.course.Course.COURSE_CODE_MESSAGE_CONSTRAINTS;

import java.util.stream.Stream;

import tahub.contacts.logic.commands.CourseCommand;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.Course;

/**
 * Parses input arguments and creates a new CourseCommand object
 */
public class CourseCommandParser implements Parser<CourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CourseCommand
     * and returns an CourseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseCommand.MESSAGE_USAGE));
        }

        String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE).get();
        String courseName = argMultimap.getValue(PREFIX_NAME).get();

        if (!Course.isValidCourseCode(courseCode)) {
            throw new ParseException(COURSE_CODE_MESSAGE_CONSTRAINTS);
        }

        if (!Course.isValidCourseName(courseName)) {
            throw new ParseException(Course.COURSE_NAME_MESSAGE_CONSTRAINTS);
        }

        Course course = new Course(courseCode, courseName);

        return new CourseCommand(course);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
