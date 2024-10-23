package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PETS;

import seedu.address.model.Model;

/**
 * sorts all pets in the application to the user.
 */
public class SortPetCommand extends SortCommand {
    /** The message displayed when the list of pets is successfully sorted. */
    public static final String MESSAGE_SORT_PET_SUCCESS = "Sorted all pets";

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
        model.sortPets();
        model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(MESSAGE_SORT_PET_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortPetCommand)) {
            return false;
        }
        return true;
    }
}
