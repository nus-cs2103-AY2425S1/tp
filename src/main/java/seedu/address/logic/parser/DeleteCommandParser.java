package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
     * Checks if the command type is correct and if there is at least one space after command type.
     * @param args the user input
     * @param commandType the command type such as "e" or "ph"
     * @return true if the command type is correct and there is at least a space after the command type
     * @throws ParseException if the user input does not conform the expected format
     */
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
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } catch (IndexOutOfBoundsException | ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
