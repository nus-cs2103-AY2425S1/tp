package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.commands.DeletePotentialCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();
        try {
            String[] argsArray = args.split(" ");
            if (argsArray.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            String type = argsArray[0];
            Index index = ParserUtil.parseIndex(argsArray[1]);

            if (type.equals(DeleteEmployeeCommand.COMMAND_TYPE)) {
                return new DeleteEmployeeCommand(index);
            } else if (type.equals(DeletePotentialCommand.COMMAND_TYPE)) {
                return new DeletePotentialCommand(index);
            }

            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_TYPE);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
