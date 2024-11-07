package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_EMPTY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_MONTH_PAID;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkPaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MonthPaid;

/**
 * Parses input arguments and creates a new UnmarkPaidCommand object
 */
public class UnmarkPaidCommandParser implements Parser<UnmarkPaidCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkPaidCommand
     * and returns a UnmarkPaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkPaidCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MONTHPAID);
        String preamble = argMultimap.getPreamble().trim();
        Set<MonthPaid> monthsPaid = parseMonthsPaidForUnmarkPaid(argMultimap.getAllValues(PREFIX_MONTHPAID));
        if (preamble.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_EMPTY_INDEX, UnmarkPaidCommand.MESSAGE_USAGE));
        }

        if (monthsPaid.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_EMPTY_MONTH_PAID, UnmarkPaidCommand.MESSAGE_USAGE));
        }

        UnmarkPaidCommand.UnmarkPaidTarget target;
        switch (preamble.toLowerCase()) {
        case "all":
            target = UnmarkPaidCommand.UnmarkPaidTarget.all();
            break;
        default:
            if (!ParserUtil.isValidIndex(preamble)) {
                throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                                            UnmarkPaidCommand.MESSAGE_USAGE));
            }
            try {
                Index index = ParserUtil.parseIndex(preamble);
                target = UnmarkPaidCommand.UnmarkPaidTarget.fromIndex(index);
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        UnmarkPaidCommand.MESSAGE_USAGE), pe);
            }
            break;
        }
        return new UnmarkPaidCommand(target, monthsPaid);
    }

    private Set<MonthPaid> parseMonthsPaidForUnmarkPaid(Collection<String> monthsPaid) throws ParseException {
        assert monthsPaid != null;

        if (monthsPaid.isEmpty()) {
            return Collections.emptySet();
        }
        Collection<String> monthsPaidSet =
                monthsPaid.size() == 1 && monthsPaid.contains("") ? Collections.emptySet() : monthsPaid;
        return ParserUtil.parseMonthsPaid(monthsPaidSet);
    }
}
