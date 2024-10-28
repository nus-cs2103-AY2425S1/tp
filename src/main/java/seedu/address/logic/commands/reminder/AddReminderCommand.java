package seedu.address.logic.commands.reminder;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a reminder to the address book.
 */
public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
            + "Parameters: "
            + "Person "
            + "DATE "
            + "TIME "
            + "Description\n"
            + "Example: " + COMMAND_WORD + " "
            + "John "
            + "2021-12-31 "
            + "2359 "
            + "New Year's Eve";

    /**
     * Executes the command to add a reminder to the model.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A {@code CommandResult} indicating the outcome of the command execution.
     * @throws CommandException If there is an issue during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    /**
     * Checks if this {@code AddReminderCommand} is equal to another object.
     *
     * @param other The other object to compare to.
     * @return {@code true} if the other object is an {@code AddReminderCommand}, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof AddReminderCommand; // instanceof handles nulls
    }

    /**
     * Returns a string representation of the {@code AddReminderCommand}.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return "AddReminderCommand";
    }
}
