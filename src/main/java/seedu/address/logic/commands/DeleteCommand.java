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
            + "Parameters: INDICES (must be a positive integer or a range, separated by commas)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 5-7";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Entries(s): \n";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    private void validateIndices(List<Index> targetIndices, Integer lastShownListSize) throws CommandException {
        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownListSize) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

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
        String commandResultString = MESSAGE_DELETE_PERSON_SUCCESS;
        validateIndices(targetIndices, lastShownList.size());
        for (Index targetIndex : targetIndices) {

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            personToDeleteList.add(personToDelete);
            commandResultString += Messages.format(personToDelete) + "\n";
        }
        deletePersonsFromModel(model, personToDeleteList);
        return new CommandResult(commandResultString);
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
