package seedu.address.logic.parser;

import seedu.address.logic.commands.AddSupplierOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class AddSupplyOrderCommandParser implements Parser<AddSupplierOrderCommand> {

    public AddSupplierOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSupplierOrderCommand.MESSAGE_USAGE));
        }

        String phoneNumber = splitArgs[0];

        int id;
        ArrayList<Integer> idList = new ArrayList<>();

        for (int i = 1; i < splitArgs.length; i++) {
            try {

                id = Integer.parseInt(splitArgs[i]);
                idList.add(id);
            } catch (NumberFormatException e) {
                throw new ParseException("ID must be a valid integer.");
            }
        }


        return new AddSupplierOrderCommand(phoneNumber, idList);
    }
}
