package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) [EMERGENCY CONTACT INDEX (must be a positive integer)]\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS = "Deleted Emergency Contact: %1$s";

    private final Index targetIndex;
    private final Index emergencyContactIndex;

    /**
     * @param targetIndex of the person in the filtered person list to delete
     * @param emergencyContactIndex of the emergency contact in the person's emergency contact list to delete
     */
    public DeleteCommand(Index targetIndex, Index emergencyContactIndex) {
        this.targetIndex = targetIndex;
        this.emergencyContactIndex = emergencyContactIndex;
    }

    private CommandResult executeDeleteEmergencyContact(Index emergencyContactIndex,
                                                        Person personToDelete,
                                                        Model model) throws CommandException {
        if (personToDelete.hasOnlyOneEmergencyContact()) {
            throw new CommandException(Messages.MESSAGE_LAST_EMERGENCY_CONTACT_INDEX);
        }
        EmergencyContact deletedEmergencyContact =
                personToDelete.getAndRemoveEmergencyContact(emergencyContactIndex);
        model.setPerson(personToDelete, personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS,
                Messages.formatEmergencyContact(deletedEmergencyContact)));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (!(emergencyContactIndex instanceof Index.EmptyEmergencyContactIndex)) {
            return executeDeleteEmergencyContact(emergencyContactIndex, personToDelete, model);
        }
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
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
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
