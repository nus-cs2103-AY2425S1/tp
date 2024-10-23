package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemoveIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemoveIngredientCommand} object.
 */
public class RemoveIngredientCommandParser implements Parser<RemoveIngredientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemoveIngredientCommand}
     * and returns a {@code RemoveIngredientCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public RemoveIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim the input and ensure it is not empty
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveIngredientCommand.MESSAGE_USAGE));
        }

        // Return a new RemoveIngredientCommand with the parsed ingredient name
        return new RemoveIngredientCommand(trimmedArgs);
    }
}
