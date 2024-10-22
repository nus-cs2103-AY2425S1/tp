package seedu.address.logic.parser.listcommands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.listcommands.ListGroupCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListStudentCommand object
 */
public class ListGroupCommandParser implements Parser<ListGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an ListStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListGroupCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGroupCommand.MESSAGE_USAGE));
        }
        return new ListGroupCommand();
    }

}
