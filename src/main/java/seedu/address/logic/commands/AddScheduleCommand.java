package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Adds an event to schedule.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "add-schedule";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from add-schedule");
    }
}
