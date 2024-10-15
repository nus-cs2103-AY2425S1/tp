package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.time.LocalDate;
import java.time.LocalTime;

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
