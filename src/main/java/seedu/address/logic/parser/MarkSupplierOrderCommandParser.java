package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkSupplyOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MarkSupplyOrderCommand} object.
 * This parser is responsible for extracting and validating the index of a supply order
 * that needs to be marked.
 */
public class MarkSupplierOrderCommandParser implements Parser<MarkSupplyOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkSupplyOrderCommand}
     * and returns a {@code MarkSupplyOrderCommand} object for execution.
     *
     * @param args the user input containing the index of the supply order to be marked.
     * @return a {@code MarkSupplyOrderCommand} object with the parsed index.
     * @throws ParseException if the user input is not a valid integer or is otherwise invalid.
     */
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
