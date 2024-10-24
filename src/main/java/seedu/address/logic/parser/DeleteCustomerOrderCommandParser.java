package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteCustomerOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteCustomerOrderCommandParser implements Parser<DeleteCustomerOrderCommand> {

    @Override
    public DeleteCustomerOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim and split the input into arguments
        String[] splitArgs = args.trim().split("\\s+");

        // Ensure there is only one argument (the index)
        if (splitArgs.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCustomerOrderCommand.MESSAGE_USAGE));
        }

        // Check if the argument is a valid integer index
        try {
            int index = Integer.parseInt(splitArgs[0]);

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
