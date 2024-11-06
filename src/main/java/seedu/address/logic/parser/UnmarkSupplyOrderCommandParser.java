package seedu.address.logic.parser;

import seedu.address.logic.commands.UnmarkSupplyOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code UnmarkSupplyOrderCommand} object.
 * This parser is responsible for extracting and validating the index of a supply order
 * that needs to be unmarked (set to pending).
 */
public class UnmarkSupplyOrderCommandParser implements Parser<UnmarkSupplyOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UnmarkSupplyOrderCommand}
     * and returns an {@code UnmarkSupplyOrderCommand} object for execution.
     *
     * @param args the user input containing the index of the supply order to be unmarked.
     * @return an {@code UnmarkSupplyOrderCommand} object with the parsed index.
     * @throws ParseException if the user input is not a valid integer or is otherwise invalid.
     */
    @Override
    public UnmarkSupplyOrderCommand parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new UnmarkSupplyOrderCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid index: %s", args));
        }
    }
}
