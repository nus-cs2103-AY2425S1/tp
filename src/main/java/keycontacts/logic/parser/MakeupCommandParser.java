package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.MakeupLessonCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Time;


/**
 * Parses input arguments and creates a new MakeupCommand object
 */
public class MakeupCommandParser implements Parser<MakeupLessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MakeupCommand
     * and returns a MakeupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MakeupLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MakeupLessonCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeupLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME);
        Date lessonDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());

        if (!Lesson.isValidTimePair(startTime, endTime)) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS);
        }
        MakeupLesson makeupLesson = new MakeupLesson(lessonDate, startTime, endTime);

        return new MakeupLessonCommand(index, makeupLesson);
    }

}
