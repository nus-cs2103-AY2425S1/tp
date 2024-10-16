package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DAY;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.ScheduleCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME);
        Day lessonDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());

        if (!Lesson.isValidTimePair(startTime, endTime)) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS);
        }
        RegularLesson regularLesson = new RegularLesson(lessonDay, startTime, endTime);

        return new ScheduleCommand(index, regularLesson);
    }

}
