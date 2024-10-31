package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkSupplyOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkSupplierOrderCommandParser implements Parser<MarkSupplyOrderCommand> {
    @Override
    public MarkSupplyOrderCommand parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new MarkSupplyOrderCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid index: %s", args));
        }
    }
}
