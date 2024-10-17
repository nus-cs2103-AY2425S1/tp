package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCustomerOrderCommand;
import seedu.address.logic.commands.DeleteCustomerOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class DeleteCustomerOrderCommandParser implements Parser<DeleteCustomerOrderCommand> {

    public DeleteCustomerOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCustomerOrderCommand.MESSAGE_USAGE));
        }

        String phoneNumber = splitArgs[0];

        return new DeleteCustomerOrderCommand(phoneNumber);
    }
}
