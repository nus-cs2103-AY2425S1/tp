package seedu.address.logic.parser.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.MarkLessonAttendanceCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new MarkLessonAttendanceCommand object.
 */
public class MarkLessonAttendanceCommandParser implements Parser<MarkLessonAttendanceCommand> {

    public static final String MESSAGE_TOO_MANY_ATTENDANCE_ARGUMENTS =
            "Number of attendance arguments must be exactly 1!";
    private final Logger logger = LogsCenter.getLogger(MarkLessonAttendanceCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the MarkLessonAttendanceCommand
     * and returns an MarkLessonAttendanceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MarkLessonAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ATTENDANCE);

        if (argMultimap.getPreamble().isEmpty() || !argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_ATTENDANCE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkLessonAttendanceCommand.MESSAGE_USAGE));
        }

        // too many attendance arguments
        if (argMultimap.getAllValues(PREFIX_ATTENDANCE).size() > 1) {
            throw new ParseException(MESSAGE_TOO_MANY_ATTENDANCE_ARGUMENTS);
        }

        // Parse index
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        // Parse names
        List<Name> studentNames = new ArrayList<>();
        for (String nameString : argMultimap.getAllValues(PREFIX_NAME)) {
            try {
                Name name = ParserUtil.parseName(nameString);
                studentNames.add(name);
            } catch (ParseException e) {
                logger.warning("Name " + nameString + " could not be parsed properly");
                throw new ParseException(Name.MESSAGE_CONSTRAINTS, e);
            }
        }
        // Parse attendance
        boolean attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE).get());

        return new MarkLessonAttendanceCommand(index, studentNames, attendance);
    }
}
