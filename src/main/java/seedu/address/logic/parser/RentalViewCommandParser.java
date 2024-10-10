package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RentalViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RentalViewCommand object
 */
public class RentalViewCommandParser implements Parser<RentalViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RentalViewCommand
     * and returns a RentalViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RentalViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RentalViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RentalViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
