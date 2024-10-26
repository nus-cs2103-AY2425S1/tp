package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SortCommand} object.
 * The input should be either 'a' for ascending or 'd' for descending.
 * Usage:
 * - Parameters: 'a' for ascending or 'd' for descending
 * - Example: sort a
 * If the input is invalid, a {@code ParseException} is thrown.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException("Invalid sort order: Use 'a' for ascending or 'd' for descending."
                    + " Example: sort a");
        } else if (trimmedArgs.equals("a")) {
            return new SortCommand(true);
        } else if (trimmedArgs.equals("d")) {
            return new SortCommand(false);
        } else {
            throw new ParseException(String.format("Invalid sort order: %s. Use 'a' for ascending or "
                    + "'d' for descending. Example: sort a", trimmedArgs));
        }
    }
}
