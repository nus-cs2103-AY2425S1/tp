package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Status;

/**
 * Parses input arguments and creates a new MarkDeliveryCommand object
 */
public class MarkDeliveryCommandParser implements Parser<MarkDeliveryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkDeliveryCommand
     * and returns a MarkDeliveryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkDeliveryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.startsWith("-d")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDeliveryCommand.MESSAGE_USAGE));
        }

        // Split the arguments into index and status
        String[] parts = trimmedArgs.substring(2).trim().split("\\s+", 2);
        if (parts.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDeliveryCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(parts[0]);
            Status status = ParserUtil.parseStatus(parts[1].trim());
            return new MarkDeliveryCommand(index, status);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDeliveryCommand.MESSAGE_USAGE), pe);
        }
    }
}
