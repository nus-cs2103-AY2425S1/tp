package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import keycontacts.logic.commands.ViewCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.Date;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (argMultimap.isPreamblePresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE);
        Date date;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            date = new Date(LocalDate.now());
        }

        return new ViewCommand(date);
    }

}
