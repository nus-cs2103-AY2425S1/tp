package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddIngredientCommand} object.
 */
public class AddIngredientCommandParser implements Parser<AddIngredientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddIngredientCommand}
     * and returns an {@code AddIngredientCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Extract name and cost from the input
        String[] splitArgs = args.trim().split("\\s+");

        // Ensure the input has exactly 2 parts: name and cost
        if (splitArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
        }

        // First argument is the ingredient name
        String name = splitArgs[0];

        // Second argument is the cost; must be a valid double
        double cost;
        try {
            cost = Double.parseDouble(splitArgs[1]);
        } catch (NumberFormatException e) {
            throw new ParseException("The cost must be a valid number.");
        }

        // Return a new AddIngredientCommand with the parsed name and cost
        return new AddIngredientCommand(name, cost);
    }
}
