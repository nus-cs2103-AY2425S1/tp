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
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeletePotentialCommand extends DeleteCommand {

    public static final String COMMAND_TYPE = "ph";

    /**
     * @param targetIndex of the person in the displayed list to delete.
     */
    public DeletePotentialCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (personToDelete.isEmployee()) {
            throw new CommandException(String.format(MESSAGE_DELETE_PERSON_WRONG_TYPE, "potential hire"));
        }
        model.deletePerson(personToDelete);
        return new CommandResult(
                String.format(MESSAGE_DELETE_PERSON_SUCCESS, "Potential Hire", Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePotentialCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetType", "ph")
                .add("targetIndex", targetIndex)
                .toString();
    }
}
