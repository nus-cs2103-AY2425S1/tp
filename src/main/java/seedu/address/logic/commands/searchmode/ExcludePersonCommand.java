package seedu.address.logic.commands.searchmode;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;



/**
 * Removes a person from the search results.
 */
public class ExcludePersonCommand extends Command {

    public static final String COMMAND_WORD = "exclude";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a contact from the search results.\n"
            + "Parameters: CONTACT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " pi/ 1, 5";

    public static final String MESSAGE_SUCCESS = "Contact removed from search results";

    private final Set<Index> excludedPersonIndices;

    public ExcludePersonCommand(Set<Index> excludedIndices) {
        this.excludedPersonIndices = excludedIndices;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        checkValidIndex(excludedPersonIndices, lastShownList.size());
        StringBuilder removedMessage = new StringBuilder("Excluded:");
        removePersonsFromSearchResults(model, excludedPersonIndices, removedMessage);
        model.updateFilteredListWithExclusions(null);
        return new CommandResult(String.format(MESSAGE_SUCCESS, removedMessage));
    }

    private void checkValidIndex(Set<Index> indices, int size) throws CommandException {
        for (Index index : indices) {
            if (index.getZeroBased() >= size) {
                throw new CommandException(Messages.MESSAGE_MULTIPLE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
        }
    }
    private void removePersonsFromSearchResults(Model model, Set<Index> indices, StringBuilder removedMessage) {
        for (Index index : indices) {
            Person excluded = model.getFilteredPersonList().get(index.getZeroBased());
            removedMessage.append(" ").append(excluded.getName());
            model.excludePerson(excluded);

        }
    }

    /**
     * Returns the indices of the persons to be removed from the search results.
     */
    public Set<Index> getExcludedPersonsIndices() {
        return excludedPersonIndices;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExcludePersonCommand // instanceof handles nulls
                && excludedPersonIndices.equals(((ExcludePersonCommand) other).excludedPersonIndices)); // state check
    }
}
