package seedu.address.logic.commands.searchmode;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;


/**
 * Clears all excluded persons.
 */

public class ClearExcludedCommand extends Command {
    public static final String COMMAND_WORD = "clearexclude";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all excluded persons.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Cleared all excluded persons.";

    public ClearExcludedCommand() {
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        // Update the filtered person list without the exclusion predicate
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
