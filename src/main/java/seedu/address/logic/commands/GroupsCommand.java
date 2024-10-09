package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class GroupsCommand extends Command {

    public static final String COMMAND_WORD = "groups";

    public static final String MESSAGE_SUCCESS = "Listed all groups";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(model.groupsString() + "\n" + MESSAGE_SUCCESS);
    }
}
