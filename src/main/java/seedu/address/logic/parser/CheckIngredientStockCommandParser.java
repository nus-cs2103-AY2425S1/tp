package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CheckIngredientStockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code CheckIngredientStockCommand} object.
 */
public class CheckIngredientStockCommandParser implements Parser<CheckIngredientStockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code CheckIngredientStockCommand}
     * and returns a {@code CheckIngredientStockCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public CheckIngredientStockCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim the input to remove any extra spaces
        String trimmedArgs = args.trim();

        // Check if the input is empty
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckIngredientStockCommand.MESSAGE_USAGE));
        }

        // Create and return the CheckIngredientStockCommand with the parsed ingredient name
        return new CheckIngredientStockCommand(trimmedArgs);
    }
}