package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkCustomerOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkCustomerOrderCommandParser implements Parser<MarkCustomerOrderCommand> {
    @Override
    public MarkCustomerOrderCommand parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new MarkCustomerOrderCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid index: %s", args));
        }
    }
}
