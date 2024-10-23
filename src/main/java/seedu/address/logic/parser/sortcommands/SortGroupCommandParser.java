package seedu.address.logic.parser.sortcommands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.sortcommands.SortGroupCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortGroupCommand object.
 */
public class SortGroupCommandParser implements Parser<SortGroupCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a SortGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortGroupCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGroupCommand.MESSAGE_USAGE));
        }
        return new SortGroupCommand();
    }

}


