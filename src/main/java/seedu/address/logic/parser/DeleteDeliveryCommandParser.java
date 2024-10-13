package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteDeliveryCommand object
 */
public class DeleteDeliveryCommandParser implements Parser<DeleteDeliveryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDeliveryCommand
     * and returns a DeleteDeliveryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDeliveryCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (!trimmedArgs.startsWith("-d")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeliveryCommand.MESSAGE_USAGE));
            }
            // Extract the index after the "-d"
            String indexStr = trimmedArgs.substring(2).trim();
            Index index = ParserUtil.parseIndex(indexStr);
            return new DeleteDeliveryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeliveryCommand.MESSAGE_USAGE), pe);
        }
    }
}

