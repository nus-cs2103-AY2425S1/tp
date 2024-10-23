package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MONTH;

import java.time.YearMonth;

import seedu.address.logic.commands.SumCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SumCommand object.
 */
public class SumCommandParser implements Parser<SumCommand> {
    @Override
    public SumCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START_MONTH, PREFIX_END_MONTH);

        if (!argMultimap.arePrefixesPresent(PREFIX_START_MONTH, PREFIX_END_MONTH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SumCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_MONTH, PREFIX_END_MONTH);
        YearMonth start = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_START_MONTH).get());
        YearMonth end = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_END_MONTH).get());

        return new SumCommand(start, end);
    }
}
