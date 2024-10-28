package seedu.address.logic.commands.reminder;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits a reminder in the address book.
 */
public class EditReminderCommand extends Command {
    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a reminder in the address book. "
            + "Parameters: "
            + "INDEX "
            + "DATE "
            + "TIME "
            + "MESSAGE\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + "2021-12-31 "
            + "23:59 "
            + "New Year's Eve";

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EditReminderCommand; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return "EditReminderCommand";
    }

    /**
     * Executes the command to edit a reminder to the model.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A {@code CommandResult} indicating the outcome of the command execution.
     * @throws CommandException If there is an issue during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
