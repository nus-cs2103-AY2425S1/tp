package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DELETE_EMPTY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given input arguments and creates a {@code DeleteCommand} object.
     * The input is expected to contain a single index indicating which item to delete.
     *
     * <p>If the input is empty or the index format is invalid, a {@code ParseException} is thrown.</p>
     *
     * @param args The user input string containing the index to delete.
     * @return A {@code DeleteCommand} object with the specified index.
     * @throws ParseException If the input is empty or the index format is incorrect.
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            if (args.isEmpty()) {
                throw new ParseException(MESSAGE_DELETE_EMPTY_INDEX);
            }
            Index index = ParserUtil.parseIndex(args);

            return new DeleteCommand(index);
        } catch (ParseException pe) {
            String errorMessage = String.format("%s \n%s",
                    pe.getMessage(),
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

            throw new ParseException(String.format(errorMessage), pe);
        }
    }
}
