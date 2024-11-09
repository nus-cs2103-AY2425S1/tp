package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by their NRIC or Index used in the patient list.\n"
            + "Parameters: NRIC (must be a valid NRIC)\n"
            + "Example: " + COMMAND_WORD + " S1234567A "
            + "OR \n"
            + "Parameters: Index (must be a valid Index in the list)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS =
            "Deleted Patient: %1$s\n\nBackup %2$d is created successfully.\nDescription: %3$s";

    private final Nric targetNric;
    private final Index targetIndex;

    /**
     * Creates a DeleteCommand to delete a person identified by their NRIC.
     *
     * @param targetNric The NRIC of the person to be deleted.
     */
    public DeleteCommand(Nric targetNric) {
        this.targetNric = targetNric;
        this.targetIndex = null;
    }

    /**
     * Creates a DeleteCommand to delete a person identified by their index in the displayed person list.
     *
     * @param targetIndex The index of the person to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetNric = null; // Null when not using NRIC
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Search for the person with the given NRIC
        Person personToDelete = null;
        if (targetNric != null) {
            for (Person person : lastShownList) {
                if (person.getNric().equals(targetNric)) {
                    personToDelete = person;
                    break;
                }
            }

            // If the person is not found, throw an exception
            if (personToDelete == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NRIC);
            }
        } else if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        }

        String description = "delete_" + personToDelete.getName().fullName;
        int backupIndex = model.backupData(description);
        model.deletePerson(personToDelete);

        return new CommandResult(
                String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete), backupIndex, description)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (targetNric != null && otherDeleteCommand.targetNric != null) {
            return targetNric.equals(otherDeleteCommand.targetNric);
        } else if (targetNric == null && otherDeleteCommand.targetNric == null) {
            // If both NRICs are null, they are considered equal (in case of index-based deletions)
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .toString();
    }
}
