package seedu.address.logic.parser;

import seedu.address.logic.commands.ActualClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Handles parsing for user confirmation to clear all contacts
 */
public class ActualClearCommandParser implements Parser<ActualClearCommand> {
    public static final String MESSAGE_FAILURE = "Address book has not been cleared!";

    /**
     * Parses the given {@code String} of arguments in the context of the ActualClearCommand
     * and returns a ActualClearCommand object for execution.
     * @return ActualClearCommand
     * @throws ParseException ParseException if the user input is not 'yes' or 'y'
     */
    public ActualClearCommand parse(String args) throws ParseException {
        String input = args.toLowerCase().trim();

        if (input.equals("yes") || input.equals("y")) {
            return new ActualClearCommand();
        }

        throw new ParseException(MESSAGE_FAILURE);

    }

}
