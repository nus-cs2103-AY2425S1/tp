package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes a person from the archive list
 */
public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": removes the details of the person identified "
            + "by the index number used in the archived person list and adds the person back to the "
            + "main client list\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_NOT_IN_ARCHIVED_LIST =
            "This command can only be used when viewing the archived list!";
    private final Index index;
    /**
     * Creates an UnarchiveCommand to remove the Person at the specified {@code Index} from the archived list and
     * add to the main client list
     */
    public UnarchiveCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean isArchivedList = model.getIsArchivedList();

        // Allow user to unarchive only if currently viewing the archived list
        if (!isArchivedList) {
            throw new CommandException(MESSAGE_NOT_IN_ARCHIVED_LIST);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnarchive = lastShownList.get(index.getZeroBased());
        model.deleteArchivedPerson(personToUnarchive); // Remove person from archived list
        model.addPerson(personToUnarchive); // Add person to main client list
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, Messages.format(personToUnarchive)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand unarchiveCommand = (UnarchiveCommand) other;
        return index.equals(unarchiveCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
