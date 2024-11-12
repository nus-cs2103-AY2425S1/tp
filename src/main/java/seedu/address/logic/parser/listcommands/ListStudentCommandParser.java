package seedu.address.logic.parser.listcommands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.listcommands.ListStudentCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListStudentCommand object
 */
public class ListStudentCommandParser implements Parser<ListStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an ListStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListStudentCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStudentCommand.MESSAGE_USAGE));
        }
        return new ListStudentCommand();
    }

}
