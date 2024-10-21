package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OWNERS;

import seedu.address.model.Model;

/**
 * Lists all owners in the application to the user.
 */
public class ListOwnerCommand extends ListCommand {

    /** The command word used to trigger the list owner action. */
    public static final String COMMAND_WORD = "list";

    /** The message displayed when the list of owners is successfully shown. */
    public static final String MESSAGE_SUCCESS = "Listed all owners";

    /**
     * Executes the list owner command, updating the filtered list in the model
     * to show all owners.
     *
     * @param model The {@code Model} which contains the application data.
     * @return The result of the command execution, which contains a success message.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOwnerList(PREDICATE_SHOW_ALL_OWNERS);
        CommandResult c = new CommandResult(MESSAGE_SUCCESS);
        c.setListType(ListOwnerCommand.MESSAGE_SUCCESS);
        return c;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListOwnerCommand)) {
            return false;
        }
        return true;
    }
}
