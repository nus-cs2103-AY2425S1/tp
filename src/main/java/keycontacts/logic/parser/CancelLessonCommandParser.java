package keycontacts.logic.parser;


import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.CancelLessonCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Time;


/**
 * Parses input arguments and creates a new {@code CancelLessonCommand} object
 */
public class CancelLessonCommandParser implements Parser<CancelLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CancelLessonCommand
     * and returns a CancelLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CancelLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_START_TIME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelLessonCommand.MESSAGE_USAGE), e);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_DATE, PREFIX_START_TIME)
                || !argMultimap.isPreamblePresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CancelLessonCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE, PREFIX_START_TIME);

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        return new CancelLessonCommand(index, date, startTime);
    }

}
