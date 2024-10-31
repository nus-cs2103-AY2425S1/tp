package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkCustomerOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MarkCustomerOrderCommand} object.
 * This parser is responsible for extracting and validating the index of a customer order
 * that needs to be marked.
 */
public class MarkCustomerOrderCommandParser implements Parser<MarkCustomerOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkCustomerOrderCommand}
     * and returns a {@code MarkCustomerOrderCommand} object for execution.
     *
     * @param args the user input containing the index of the customer order to be marked.
     * @return a {@code MarkCustomerOrderCommand} object with the parsed index.
     * @throws ParseException if the user input is not a valid integer or is otherwise invalid.
     */
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