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

        // Trim and split the input into parts
        String[] splitArgs = args.trim().split("\\s+");

        // Ensure there are at least two parts: the ingredient name and the cost
        if (splitArgs.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
        }

        // Use StringBuilder to join all parts except the last one for the ingredient name
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < splitArgs.length - 1; i++) {
            nameBuilder.append(splitArgs[i]);
            if (i < splitArgs.length - 2) {
                nameBuilder.append(" "); // Add space between words
            }
        }
        String name = nameBuilder.toString();

        // The last part is the cost; it must be a valid double
        double cost;
        try {
            cost = Double.parseDouble(splitArgs[splitArgs.length - 1]);
        } catch (NumberFormatException e) {
            throw new ParseException("The cost must be a valid number.");
        }

        if (cost <= 0) {
            throw new ParseException("The cost must be a positive number.");
        }

        // Return a new AddIngredientCommand with the parsed name and cost
        return new AddIngredientCommand(name, cost);
    }
}

