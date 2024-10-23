package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkPaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MonthPaid;

/**
 * Parses input arguments and creates a new MarkPaidCommand object
 */
public class MarkPaidCommandParser implements Parser<MarkPaidCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkPaidCommand
     * and returns a MarkPaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkPaidCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MONTHPAID);
        String preamble = argMultimap.getPreamble().trim();
        Set<MonthPaid> monthsPaid = parseMonthsPaidForMarkPaid(argMultimap.getAllValues(PREFIX_MONTHPAID));

        if (preamble.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE));
        }

        MarkPaidCommand.MarkPaidTarget target;
        switch (preamble.toLowerCase()) {
        case "all":
            target = MarkPaidCommand.MarkPaidTarget.all();
            break;
        default:
            if (!ParserUtil.isValidIndex(preamble)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE));
            }
            try {
                Index index = ParserUtil.parseIndex(preamble);
                target = MarkPaidCommand.MarkPaidTarget.fromIndex(index);
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MarkPaidCommand.MESSAGE_USAGE), pe);
            }
            break;
        }


        return new MarkPaidCommand(target, monthsPaid);
    }

    private Set<MonthPaid> parseMonthsPaidForMarkPaid(Collection<String> monthsPaid) throws ParseException {
        assert monthsPaid != null;

        if (monthsPaid.isEmpty()) {
            return Collections.emptySet();
        }
        Collection<String> monthsPaidSet =
                monthsPaid.size() == 1 && monthsPaid.contains("") ? Collections.emptySet() : monthsPaid;
        return ParserUtil.parseMonthsPaid(monthsPaidSet);
    }
}
