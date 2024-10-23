package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewRentalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewRentalCommand object
 */
public class ViewRentalCommandParser implements Parser<ViewRentalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewRentalCommand
     * and returns a ViewRentalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewRentalCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewRentalCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewRentalCommand.MESSAGE_USAGE), pe);
        }
    }
}
