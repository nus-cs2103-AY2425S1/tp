package seedu.address.logic.parser.sortcommands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.sortcommands.SortTaskCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortTaskCommand object.
 */
public class SortTaskCommandParser implements Parser<SortTaskCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an SortTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortTaskCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE));
        }
        return new SortTaskCommand();
    }

}
