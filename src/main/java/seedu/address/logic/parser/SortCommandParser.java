package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for the sort command.
 * The input should specify the order in which to sort the contacts (ascending or descending).
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the user input and returns a SortCommand object based on the specified order.
     *
     * @param userInput The input provided by the user. This should be either "asc" or "desc".
     * @return A SortCommand object with the specified sorting order.
     * @throws ParseException If the input is invalid or does not specify a valid sorting order.
     */
    @Override
    public SortCommand parse(String userInput) throws ParseException {
        String order = userInput.trim().toLowerCase(); // Trim whitespace and convert to lowercase

        // Check for empty input
        if (order.isEmpty()) {
            throw new ParseException(SortCommand.MESSAGE_NO_ORDER); // Replace with your custom message
        }

        // Check for valid order input: it must be either "asc" or "desc"
        if (!order.equals("asc") && !order.equals("desc")) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
        }

        // Create and return a SortCommand object with the  valid order
        return new SortCommand(order);
    }
}
