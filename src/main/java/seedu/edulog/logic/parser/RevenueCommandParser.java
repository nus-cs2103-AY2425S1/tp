package seedu.edulog.logic.parser;

import seedu.edulog.logic.commands.RevenueCommand;
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
            return new RevenueCommand("paid");
        } else if (args.equals(RevenueCommand.UNPAID)) {
            return new RevenueCommand(("unpaid"));
        } else {
            throw new ParseException(RevenueCommand.COMMAND_USAGE);
        }
    }
}
