package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.edulog.logic.commands.PaidRevenueCommand;
import seedu.edulog.logic.commands.RevenueCommand;
import seedu.edulog.logic.commands.UnpaidRevenueCommand;
import seedu.edulog.logic.parser.exceptions.ParseException;

/**
 * Parses input arguements so RevenueCommand knows whether to show total paid or unpaid
 */
public class RevenueCommandParser implements Parser<RevenueCommand> {
    /**
     * Parses the option and returns the configured RevenueCommand
     */
    public RevenueCommand parse(String args) throws ParseException {

        args = args.trim();
        if (args.equals(RevenueCommand.PAID)) {
            return new PaidRevenueCommand();
        } else if (args.equals(RevenueCommand.UNPAID)) {
            return new UnpaidRevenueCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.COMMAND_USAGE));
        }

    }
}
