package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

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
            + ": Deletes the person identified by the index number used in the displayed person list, "
            + "or deletes all contacts shown if 'all' is provided as the parameter.\n"
            + "Parameters: INDEX (must be a positive integer) or 'all'\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " all";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Contacts: %1$s";
    public static final String MESSAGE_INVALID_INDEX_OR_STRING = "The person index provided is invalid. "
            + "Index must either be:\n"
            + "1. A positive integer within the size of the list\n"
            + "2. 'all' if you want to delete all contacts in the list.";
    public static final String MESSAGE_EMPTY_CONTACTS = "There is no contact to be deleted.";
    private List<Index> targetIndices = null;
    private boolean deleteAll = false;

    public DeleteCommand(Index targetIndex) {
        this.targetIndices = List.of(targetIndex);
    }

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    public DeleteCommand() {
        deleteAll = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> deletedPersons = new ArrayList<>();

        if (deleteAll) {
            List<Person> toDelete = new ArrayList<>(lastShownList);
            if (toDelete.isEmpty()) {
                throw new CommandException(MESSAGE_EMPTY_CONTACTS);
            }
            for (Person p : toDelete) {
                model.deletePerson(p);
                deletedPersons.add(p);
            }
        } else {
            for (Index targetIndex : targetIndices) {
                if (targetIndex.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(MESSAGE_INVALID_INDEX_OR_STRING);
                }
                Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
                model.deletePerson(personToDelete);
                deletedPersons.add(personToDelete);
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(deletedPersons)));
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

        if (targetIndices == null) {
            return otherDeleteCommand.targetIndices == null;
        }

        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .add("deleteAll", deleteAll)
                .toString();
    }
}
