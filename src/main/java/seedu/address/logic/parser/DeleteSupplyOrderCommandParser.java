package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteSupplyOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteSupplyOrderCommand} object.
 * This parser is responsible for extracting and validating the index of the supply order
 * that needs to be deleted.
 */
public class DeleteSupplyOrderCommandParser implements Parser<DeleteSupplyOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteSupplyOrderCommand}
     * and returns a {@code DeleteSupplyOrderCommand} object for execution.
     *
     * @param args the user input containing the index of the supply order to be deleted.
     * @return a {@code DeleteSupplyOrderCommand} object with the parsed index.
     * @throws ParseException if the user input is invalid.
     */
    @Override
    public DeleteSupplyOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim and split the input into arguments
        String[] splitArgs = args.trim().split("\\s+");

        // Ensure there is only one argument (the index)
        if (splitArgs.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplyOrderCommand.MESSAGE_USAGE));
        }

        // Check if the argument is a valid integer index
        try {
            int index = Integer.parseInt(splitArgs[0]);

            if (index <= 0) { // Index must be a positive integer
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplyOrderCommand.MESSAGE_USAGE));
            }

            return new DeleteSupplyOrderCommand(index);

        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplyOrderCommand.MESSAGE_USAGE));
        }
    }
}