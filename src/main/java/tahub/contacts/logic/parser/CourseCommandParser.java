package tahub.contacts.logic.parser;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_NAME;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CODE);

        String courseName = String.valueOf(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        String courseCode = String.valueOf(ParserUtil.parseName(argMultimap.getValue(PREFIX_CODE).get()));

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
