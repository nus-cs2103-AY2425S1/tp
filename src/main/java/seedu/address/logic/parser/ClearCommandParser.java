package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    @Override
    public ClearCommand parse(String userInput) throws ParseException {
        // Check if the user provided the "confirm" keyword.
        boolean isConfirmed = userInput.trim().equalsIgnoreCase("confirm");

        if (!isConfirmed) {
            throw new ParseException(ClearCommand.MESSAGE_CONFIRMATION_REQUIRED);
        }

        // Create the ClearCommand with the confirmation status.
        return new ClearCommand();
    }
}
