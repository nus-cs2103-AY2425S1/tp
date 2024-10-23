package seedu.address.logic.commands;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays all appointments for the specified date from the address book.
 */
public class ScheduleDateCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all medical appointments for a particular date.\n"
            + "Parameters: DATE (in dd-MM-yyyy format)\n"
            + "Example: " + COMMAND_WORD + " 12-10-2024";

    public static final String MESSAGE_SUCCESS = "Displayed schedule for: %1$s";

    private final LocalDate date;

    public ScheduleDateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO: Implement logic to filter and display appointments for the given date
        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScheduleDateCommand)) {
            return false;
        }

        ScheduleDateCommand otherCommand = (ScheduleDateCommand) other;
        return date.equals(otherCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .toString();
    }
}
