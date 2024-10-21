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

    private boolean deleteParserCheck(String args, String commandType) throws ParseException {
        if (args.startsWith(commandType)) {
            if (args.startsWith(commandType + " ")) {
                return true;
            }
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return false;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();
        Index index;
        try {
            if (deleteParserCheck(args, DeleteEmployeeCommand.COMMAND_TYPE)) {
                index = ParserUtil.parseIndex(args.substring(DeleteEmployeeCommand.COMMAND_TYPE.length()).trim());
                return new DeleteEmployeeCommand(index);
            } else if (deleteParserCheck(args, DeletePotentialCommand.COMMAND_TYPE)) {
                index = ParserUtil.parseIndex(args.substring(DeletePotentialCommand.COMMAND_TYPE.length()).trim());
                return new DeletePotentialCommand(index);
            }
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_TYPE);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
