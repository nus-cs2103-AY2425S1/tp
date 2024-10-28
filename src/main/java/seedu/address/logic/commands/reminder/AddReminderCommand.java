package seedu.address.logic.commands.reminder;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
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
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof AddReminderCommand; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return "AddReminderCommand";
    }
}
