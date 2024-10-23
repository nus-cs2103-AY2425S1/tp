package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.logic.Messages.format;

import java.util.List;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;
import tutorease.address.model.person.Person;


/**
 * Deletes a contact identified by the index from the address book.
 */
public class DeleteContactCommand extends ContactCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String DELETE_COMMAND_STRING_FORMAT = "DeleteContactCommand"
            + "{targetIndex=tutorease.address.commons.core."
            + "index.Index{zeroBasedIndex=%d}}";

    public static final String MESSAGE_USAGE = ContactCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the contact identified by the index number "
            + "in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: contact delete 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Contact [%1$s] deleted successfully";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";

    private final Index targetIndex;
    /**
     * Constructs a {@code DeleteContactCommand} to delete a contact at the specified {@code Index}.
     *
     * @param targetIndex The index of the contact to be deleted. Must not be null.
     * @throws NullPointerException if {@code targetIndex} is null.
     */
    public DeleteContactCommand(Index targetIndex) {
        if (targetIndex == null) {
            throw new NullPointerException("Index cannot be null");
        }
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
        return String.format(DELETE_COMMAND_STRING_FORMAT, targetIndex.getZeroBased());
    }
}

