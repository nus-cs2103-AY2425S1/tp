package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MONTH;

import java.time.YearMonth;

import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TransactionDatePredicate;

/**
 * Parses input arguments and creates a new SummaryCommand object.
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {
    @Override
    public SummaryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START_MONTH, PREFIX_END_MONTH);

        if (!argMultimap.arePrefixesPresent(PREFIX_START_MONTH, PREFIX_END_MONTH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_MONTH, PREFIX_END_MONTH);
        YearMonth start = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_START_MONTH).get());
        YearMonth end = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_END_MONTH).get());

        if (start.isAfter(end)) {
            throw new ParseException(MESSAGE_INVALID_DATE_RANGE);
        }
        return new SummaryCommand(new TransactionDatePredicate(start, end));
    }
}
