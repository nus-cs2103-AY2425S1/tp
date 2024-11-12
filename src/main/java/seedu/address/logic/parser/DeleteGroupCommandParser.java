package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String args) throws ParseException {
        try {
            GroupName groupName = ParserUtil.parseGroupName(args);
            return new DeleteGroupCommand(groupName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE), pe);
        }
    }

}
