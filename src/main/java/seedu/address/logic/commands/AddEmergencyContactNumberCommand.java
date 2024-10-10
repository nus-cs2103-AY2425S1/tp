package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Changes the emergency contact number of an existing student in the address book
 */
public class AddEmergencyContactNumberCommand extends Command {

    public static final String COMMAND_WORD = "addEmergencyContactNumber";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from add emergency contact number command");
    }
}
