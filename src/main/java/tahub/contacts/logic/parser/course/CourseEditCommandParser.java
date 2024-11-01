package tahub.contacts.logic.parser.course;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import tahub.contacts.logic.commands.course.CourseEditCommand;
import tahub.contacts.logic.commands.course.CourseEditCommand.EditCourseDescriptor;
import tahub.contacts.logic.parser.ArgumentMultimap;
import tahub.contacts.logic.parser.ArgumentTokenizer;
import tahub.contacts.logic.parser.Parser;
import tahub.contacts.logic.parser.ParserUtil;
import tahub.contacts.logic.parser.Prefix;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.CourseCode;

/**
 * Parses input arguments and creates a new EditCourseCommand object
 */
public class CourseEditCommandParser implements Parser<CourseEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCourseCommand
     * and returns an EditCourseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_NAME);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseEditCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_COURSE_CODE).isPresent();
        assert argMultimap.getValue(PREFIX_NAME).isPresent();

        CourseCode courseCodeToEdit = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());
        EditCourseDescriptor editCourseDescriptor = new EditCourseDescriptor();
        editCourseDescriptor.setCourseName(ParserUtil.parseCourseName(argMultimap.getValue(PREFIX_NAME).get()));

        if (!editCourseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(CourseEditCommand.MESSAGE_COURSE_NOT_EDITED);
        }

        return new CourseEditCommand(courseCodeToEdit, editCourseDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
