package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeletePostalCodeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PostalCode;

/**
 * Parses input arguments and creates a new DeletePostalCommand object.
 */
public class DeletePostalCodeCommandParser implements Parser<DeletePostalCodeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePostalCommand
     * and returns a DeletePostalCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeletePostalCodeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePostalCodeCommand.MESSAGE_USAGE));
        }

        if (!isValidPostalCode(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePostalCodeCommand.MESSAGE_USAGE));
        }

        return new DeletePostalCodeCommand(new PostalCode(trimmedArgs));
    }

    /**
     * Validates the postal code format.
     * @param postalCode The postal code to validate.
     * @return true if the postal code is valid; false otherwise.
     */
    private boolean isValidPostalCode(String postalCode) {
        return postalCode.matches("\\d{6}");
    }
}
