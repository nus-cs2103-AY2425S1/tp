package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteCustomerOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteCustomerOrderCommandParser implements Parser<DeleteCustomerOrderCommand> {

    @Override
    public DeleteCustomerOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();

        // Check if the argument is a valid integer index
        try {
            int index = Integer.parseInt(trimmedArgs);

            if (index <= 0) { // Index must be a positive integer
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCustomerOrderCommand.MESSAGE_USAGE));
            }

            return new DeleteCustomerOrderCommand(index);

        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCustomerOrderCommand.MESSAGE_USAGE));
        }
    }
}
