package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents a command to add a username to the specified game of the contact.
 */
public class UsernameCommand extends Command {

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("This command is supposed to add a username");
    }
}
