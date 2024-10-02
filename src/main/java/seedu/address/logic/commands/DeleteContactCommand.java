package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.format;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Deletes a contact identified by the index from the address book.
 */
public class DeleteContactCommand extends Command {
    public static final String COMMAND_WORD = "contact";

    public static final String SUB_COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "contact delete: Deletes the contact identified by the index number "
            + "in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: contact delete 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Contact [%1$s] deleted successfully";
    public static final String MESSAGE_DELETE_PERSON_FAILURE = "Deleting of contact has failed due to: %s";

    private final Index targetIndex;

    public DeleteContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteContactCommand)) {
            return false;
        }

        DeleteContactCommand otherDeleteCommand = (DeleteContactCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return "DeleteContactCommand{"
                + "targetIndex=" + targetIndex
                + '}';
    }
}

