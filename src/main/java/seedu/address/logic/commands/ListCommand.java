package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String SHORT_COMMAND_WORD = "li";

    public static final String MESSAGE_SUCCESS = "Listed all clients.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateUnfilteredList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
