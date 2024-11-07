package keycontacts.logic.parser;


import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.CancelLessonCommand;
import keycontacts.logic.commands.UncancelLessonCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;


/**
 * Parses input arguments and creates a new {@code UncancelLessonCommand} object
 */
public class UncancelLessonCommandParser implements Parser<UncancelLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UncancelLessonCommand
     * and returns a UncancelLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UncancelLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncancelLessonCommand.MESSAGE_USAGE), e);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_DATE) || !argMultimap.isPreamblePresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CancelLessonCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE);

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        return new UncancelLessonCommand(index, new CancelledLesson(date));
    }

}
