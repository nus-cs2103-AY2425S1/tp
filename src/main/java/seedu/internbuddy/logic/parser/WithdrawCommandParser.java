package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.WithdrawCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new WithdrawCommand object
 */
public class WithdrawCommandParser implements Parser<WithdrawCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WithdrawCommand
     * and returns a WithdrawCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public WithdrawCommand parse(String args) throws ParseException {
        try {
            Index[] indexes = ParserUtil.parseWithdrawIndex(args);
            Index companyIndex = indexes[0];
            Index applicationIndex = indexes[1];
            return new WithdrawCommand(companyIndex, applicationIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE), pe);
        }
    }
}
