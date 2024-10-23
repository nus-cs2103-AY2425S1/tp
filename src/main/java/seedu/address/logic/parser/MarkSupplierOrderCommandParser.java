package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkSupplierOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkSupplierOrderCommandParser implements Parser<MarkSupplierOrderCommand> {
    @Override
    public MarkSupplierOrderCommand parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new MarkSupplierOrderCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid index: %s", args));
        }
    }
}
