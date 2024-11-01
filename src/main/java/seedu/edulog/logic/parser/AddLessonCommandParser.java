package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;
import java.util.stream.Stream;

import seedu.edulog.logic.commands.AddLessonCommand;
import seedu.edulog.logic.parser.exceptions.ParseException;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_DESCRIPTION, PREFIX_START_DAY, PREFIX_START_TIME, PREFIX_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_START_DAY, PREFIX_START_TIME, PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_DESCRIPTION, PREFIX_START_DAY, PREFIX_START_TIME, PREFIX_END_TIME);
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Day day = ParserUtil.parseDayOfWeek(argMultimap.getValue(PREFIX_START_DAY).get());

        List<LessonTime> times = ParserUtil.parseLessonTimes(
            argMultimap.getValue(PREFIX_START_TIME).get(),
            argMultimap.getValue(PREFIX_END_TIME).get()
        );

        LessonTime startTime = times.get(0);
        LessonTime endTime = times.get(1);

        Lesson lesson = new Lesson(description, day, startTime, endTime);

        return new AddLessonCommand(lesson);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
