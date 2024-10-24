package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows the full view for a person with all of their information.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all information about the person identified by the index number in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_SUCCESS = "Showing view for %1$s.";

    private final Index index;

    /**
     * Creates a ViewCommand to view all information on a specified {@code Person}.
     */
    public ViewCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    /**
     * Filters the displayed {@code ObservableList} in the {@code Model} to just the selected index.
     * Returns a {@code CommandResult} that indicates the user has requested to View a person.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(index.getZeroBased());
        model.updateFilteredPersonList(person -> person.isSamePerson(personToView));

        String commandResultMessage = String.format(MESSAGE_VIEW_SUCCESS, Messages.format(personToView));

        return new CommandResult(commandResultMessage, true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof to handle nulls
        if (!(other instanceof ViewCommand otherViewCommand)) {
            return false;
        }

        return index.equals(otherViewCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
