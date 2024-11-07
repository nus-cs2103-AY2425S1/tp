package seedu.address.logic.commands.searchmode;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;


/**
 * Clears all excluded persons.
 */

public class ClearExcludedCommand extends Command {
    public static final String COMMAND_WORD = "clearexcluded";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all excluded contacts.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Cleared all excluded contacts.";
    public static final String MESSAGE_EMPTY = "No contacts are currently excluded.";

    public ClearExcludedCommand() {
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        // Update the filtered person list without the exclusion predicate
        if (model.getExcludedPersons().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        model.clearExcludedPersons();
        model.updateFilteredListWithExclusions(null);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ClearExcludedCommand; // instanceof handles nulls
    }
}
