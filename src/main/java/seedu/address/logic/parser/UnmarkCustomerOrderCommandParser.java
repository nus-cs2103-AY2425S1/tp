package seedu.address.logic.parser;

import seedu.address.logic.commands.UnmarkCustomerOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code UnmarkCustomerOrderCommand} object.
 * This parser is responsible for extracting and validating the index of a customer order
 * that needs to be unmarked (set to pending).
 */
public class UnmarkCustomerOrderCommandParser implements Parser<UnmarkCustomerOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UnmarkCustomerOrderCommand}
     * and returns an {@code UnmarkCustomerOrderCommand} object for execution.
     *
     * @param args the user input containing the index of the customer order to be unmarked.
     * @return an {@code UnmarkCustomerOrderCommand} object with the parsed index.
     * @throws ParseException if the user input is not a valid integer or is otherwise invalid.
     */
    @Override
    public UnmarkCustomerOrderCommand parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new UnmarkCustomerOrderCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid index: %s", args));
        }
    }
}
