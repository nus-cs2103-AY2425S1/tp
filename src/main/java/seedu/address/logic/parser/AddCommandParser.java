package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
            PREFIX_EMAIL, PREFIX_COURSE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        // Parse courses; if none are provided, use an empty set
        Set<Course> courseList = parseCourses(argMultimap.getAllValues(PREFIX_COURSE)).orElse(new HashSet<>());

        Student student = new Student(name, phone, email, courseList);

        return new AddCommand(student);
    }

    /**
     * Parses {@code Collection<String> courses} into a {@code Set<Course>} if {@code courses} is non-empty.
     * This method supports both CSV input and multiple `c/` prefixes.
     */
    private Optional<Set<Course>> parseCourses(Collection<String> courses) throws ParseException {
        assert courses != null;

        if (courses.isEmpty()) {
            return Optional.empty(); // If no courses are provided, return empty Optional
        }

        Set<String> parsedCourses = new HashSet<>();

        // For each course string (this could be a CSV or a single course)
        for (String courseString : courses) {

            List<String> splitCourses = ArgumentTokenizer.tokenizeWithDefault(courseString);
            // Add each course after trimming whitespace
            for (String course : splitCourses) {
                parsedCourses.add(course.trim());
            }
        }

        return Optional.of(ParserUtil.parseCourses(parsedCourses));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
