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
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDICES (must be a positive integer or a closed range, separated by spaces)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 5-7";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Entries(s): \n";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    /**
     * Validates that each index in the list of target indices is within the bounds of the last displayed person list.
     *
     * @param targetIndices A list of indices to be validated.
     * @param lastShownListSize The size of the last displayed person list.
     * @throws CommandException if any index in targetIndices is out of bounds
     *      (i.e., equal to or greater than lastShownListSize).
     */
    private void validateIndices(List<Index> targetIndices, Integer lastShownListSize) throws CommandException {
        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownListSize) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Deletes the specified persons from the model.
     *
     * @param model The model from which persons will be deleted.
     * @param personToDeleteList A list of persons to be deleted from the model.
     */
    private void deletePersonsFromModel(Model model, List<Person> personToDeleteList) {
        for (Person personToDelete : personToDeleteList) {
            model.deletePerson(personToDelete);
        }
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getPersonList();
        List<Person> personToDeleteList = new ArrayList<>();
        StringBuilder commandResultString = new StringBuilder(MESSAGE_DELETE_PERSON_SUCCESS);
        validateIndices(targetIndices, lastShownList.size());
        for (Index targetIndex : targetIndices) {

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            personToDeleteList.add(personToDelete);
            commandResultString.append(Messages.format(personToDelete)).append("\n");
        }
        deletePersonsFromModel(model, personToDeleteList);
        return new CommandResult(commandResultString.toString());
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
                .add("targetIndices", targetIndices)
                .toString();
    }
}
