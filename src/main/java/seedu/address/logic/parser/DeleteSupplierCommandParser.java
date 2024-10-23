package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSupplierCommand object
 */
public class DeleteSupplierCommandParser implements Parser<DeleteSupplierCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSupplierCommand
     * and returns a DeleteSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSupplierCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (!trimmedArgs.startsWith("-s")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplierCommand.MESSAGE_USAGE));
            }
            // Extract the index after the "-s"
            String indexStr = trimmedArgs.substring(2).trim();
            Index index = ParserUtil.parseIndex(indexStr);
            return new DeleteSupplierCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplierCommand.MESSAGE_USAGE), pe);
        }
    }
}
