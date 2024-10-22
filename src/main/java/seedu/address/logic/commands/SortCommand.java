package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    @Override
    public abstract CommandResult execute(Model model);
}
