package seedu.address.logic.commands;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to view the schedule for a specific week starting from a given date.
 * The date is provided by the user and must follow the format "DD-MM-YYYY".
 */
public class SeeScheduleCommand extends Command {
    public static final String COMMAND_WORD = "see";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": See your schedule for the week. "
            + "Parameters: d/\n"
            + "Example: " + COMMAND_WORD + " d/10-10-2024";

    public static final String MESSAGE_INVALID_DATE = "Date must be in the format DD-MM-YYYY.";

    private final LocalDate date;

    public SeeScheduleCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
