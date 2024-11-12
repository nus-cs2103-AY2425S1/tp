package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnpaidCommand;
import seedu.address.logic.commands.UnpaidCommand.UnpaidPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new UnpaidCommand object
 */
public class UnpaidCommandParser implements Parser<UnpaidCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnpaidCommand
     * and returns a UnpaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnpaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            UnpaidPersonDescriptor unpaidPersonDescriptor = new UnpaidPersonDescriptor();
            unpaidPersonDescriptor.setHasNotPaid(); // Change this to false for Unpaid
            unpaidPersonDescriptor.setFrequencyToZero();
            unpaidPersonDescriptor.setLastPaidDate();
            return new UnpaidCommand(index, unpaidPersonDescriptor);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpaidCommand.MESSAGE_USAGE), pe);
        }
    }
}
