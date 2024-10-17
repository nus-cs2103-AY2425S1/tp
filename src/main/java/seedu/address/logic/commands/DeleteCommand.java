package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.DescendingIndexComparator;
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

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person(s) identified by the index number(s) used in the displayed person list.\n"
            + "Parameters: any number of INDEX (must be a positive integer) separated by spaces\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_PEOPLE_SUCCESS = "Deleted: ";

    public static final String MESSAGE_INVALID_INDICES = "\nThe remaining inputs were invalid.";

    private final ArrayList<Index> targetIndices;

    private final boolean hasInvalidArgs;

    /**
     * Instantiates a DeleteCommand for deleting a single index assuming
     * no invalid arguments inputted by user.
     *
     * @param targetIndex
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndices = new ArrayList<Index>(Arrays.asList(targetIndex));
        this.hasInvalidArgs = false;
    }

    /**
     * Instantiates a DeleteCommand for deleting multiple indices assuming
     * no invalid arguments inputted by user.
     *
     * @param targetIndices
     */
    public DeleteCommand(ArrayList<Index> targetIndices) {
        this.targetIndices = targetIndices;
        this.hasInvalidArgs = false;
    }

    /**
     * Instantiates a DeleteCommand with the option of specifying if invalid
     * arguments were inputted by user.
     *
     * @param targetIndices
     * @param hasInvalidArgs
     */
    public DeleteCommand(ArrayList<Index> targetIndices, boolean hasInvalidArgs) {
        this.targetIndices = targetIndices;
        this.hasInvalidArgs = hasInvalidArgs;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndices.size() == 1) {
            return deleteSingleIndex(model, targetIndices.get(0));
        }

        ArrayList<Index> invalidIndices = new ArrayList<>();
        ArrayList<String> deletedPersons = new ArrayList<>();
        targetIndices.sort(new DescendingIndexComparator()); //sort in descending order for smooth deletion
        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                invalidIndices.add(targetIndex);
                continue;
            }
            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            deletedPersons.add(personToDelete.getName().toString());
        }
        if (invalidIndices.isEmpty() && !hasInvalidArgs) {
            return new CommandResult(MESSAGE_DELETE_PEOPLE_SUCCESS + String.join(", ", deletedPersons));
        }
        if (deletedPersons.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return new CommandResult(MESSAGE_DELETE_PEOPLE_SUCCESS + String.join(", ", deletedPersons)
                + MESSAGE_INVALID_INDICES);
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
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndices.get(0))
                .toString();
    }

    private CommandResult deleteSingleIndex(Model model, Index targetIndex) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        String commandResult = String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete));
        if (hasInvalidArgs) {
            commandResult += MESSAGE_INVALID_INDICES;
        }
        return new CommandResult(commandResult);
    }
}
