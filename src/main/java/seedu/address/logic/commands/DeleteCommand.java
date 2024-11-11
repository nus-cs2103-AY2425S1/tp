package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {
    public static final String SHORT_COMMAND_WORD = ":rm";
    public static final String LONG_COMMAND_WORD = ":remove";
    public static final String MESSAGE_USAGE = "\"" + SHORT_COMMAND_WORD + "\"" + " OR \"" + LONG_COMMAND_WORD + "\""
            + " : Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: -i INDEX (must be a positive integer)\n"
            + "Example 1: " + SHORT_COMMAND_WORD + " -i 1" + "\n"
            + "Example 2: " + LONG_COMMAND_WORD + " -i 1";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String COMMAND_SUMMARY_ACTION = "Delete";
    public static final String COMMAND_SUMMARY_FORMAT =
            ":remove -i INDEX\n" + ":rm -i INDEX";
    public static final String COMMAND_SUMMARY_EXAMPLES =
            ":remove -i 3\n" + ":rm -i 3";
    public static final List<String> INVALID_VARIANTS = Arrays.asList("del", "delete", "rm", ":del",
            "remove");
    private static Logger logger = LogsCenter.getLogger(DeleteCommand.class);
    private final ArrayList<Index> targetIndexes;

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code Index}.
     */
    public DeleteCommand(Index targetindex) {
        this.targetIndexes = new ArrayList<>();
        targetIndexes.add(targetindex);
    }

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code Indexes}.
     */
    public DeleteCommand(ArrayList<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    /**
     * Executes the command and deletes the person from the address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the CommandResult of the command execution
     * @throws CommandException if an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing DeleteCommand");
        requireNonNull(model);
        Person personToDelete = null;
        List<Person> peopleToDelete = new ArrayList<>();
        List<Person> lastShownList = model.getFilteredPersonList();

        //checks if there is any invalid index
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
            peopleToDelete.add(personToDelete);
        }
        //reverse order to prevent index out of bounds
        targetIndexes.sort(Comparator.comparingInt(Index::getZeroBased).reversed());
        for (Index targetIndex : targetIndexes) {
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
        }
        assert personToDelete != null;
        assert !peopleToDelete.isEmpty();
        model.commitAddressBook(); // Commit after all deletions are successful
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                peopleToDelete.size() == 1
                        ? Messages.format(personToDelete)
                        : Messages.format(peopleToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndexes.equals(otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes",
                        Arrays.toString(targetIndexes.toArray())
                                .replace("[", "")
                                .replace("]", ""))
                .toString();
    }
}
