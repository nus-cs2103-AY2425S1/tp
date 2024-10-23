package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OWNERS;

import seedu.address.model.Model;

/**
 * Sort all owners in the application.
 */
public class SortOwnerCommand extends SortCommand {
    /** The message displayed when the list of owners is successfully sorted. */
    public static final String MESSAGE_SORT_OWNER_SUCCESS = "Sorted all owners";

    /**
     * Executes the sort pet command, updating the filtered list in the model
     * to show the new list of sorted pets.
     *
     * @param model The {@code Model} which contains the application data.
     * @return The result of the command execution, which contains a success message.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortOwners();
        model.updateFilteredOwnerList(PREDICATE_SHOW_ALL_OWNERS);
        return new CommandResult(MESSAGE_SORT_OWNER_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortOwnerCommand)) {
            return false;
        }
        return true;
    }
}
