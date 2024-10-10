package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Adds, deletes or views a tag of an existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from tag");
    }
}
