package seedu.address.logic.parser;

import seedu.address.logic.commands.ActualClearCommand;

/**
 * Handles parsing for user confirmation to clear all contacts and creates a new ActualClearCommand object.
 */
public class ActualClearCommandParser implements Parser<ActualClearCommand> {
    public static final String MESSAGE_FAILURE = "Address book has not been cleared!";

    /**
     * Parses the given {@code String} of arguments in the context of the ActualClearCommand
     * and returns a ActualClearCommand object for execution.
     * @return ActualClearCommand
     */
    public ActualClearCommand parse(String args) {
        String input = args.toLowerCase().trim();

        if (input.equals("yes") || input.equals("y")) {
            return new ActualClearCommand();
        }

        return new ActualClearCommand(MESSAGE_FAILURE);

    }

}
