package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;
import seedu.address.ui.MainWindow;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_POLICY_SUCCESS = "Deleted Policy %1$d from %2$s";

    private final Index targetIndex;
    private final Name targetName;
    private final Index policyIndex;

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code Index}.
     *
     * @param targetIndex Index of the person to delete.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.policyIndex = null;
    }

    /**
     * Creates a DeleteCommand to delete the person with the specified {@code Name}.
     *
     * @param targetName Name of the person to delete.
     */
    public DeleteCommand(Name targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
        this.policyIndex = null;
    }

    /**
     * Creates a DeleteCommand to delete the policy at the specified {@code policyIndex}
     * from the person at the specified {@code targetIndex}.
     *
     * @param targetIndex Index of the person.
     * @param policyIndex Index of the policy to delete.
     */
    public DeleteCommand(Index targetIndex, Index policyIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.policyIndex = policyIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());

            if (policyIndex != null) {
                if (policyIndex.getZeroBased() >= personToDelete.getPolicies().size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
                }
                Policy policyToDelete = personToDelete.getPolicies().get(policyIndex.getZeroBased());
                personToDelete.removePolicy(policyToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_POLICY_SUCCESS, policyIndex
                        .getOneBased(), personToDelete.getName()));
            } else {
                boolean isConfirmed = MainWindow.showConfirmationDialog("Are you sure you want to delete "
                        + personToDelete.getName() + "?");
                if (!isConfirmed) {
                    return new CommandResult("Deletion cancelled.");
                }
                model.deletePerson(personToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
            }
        } else {
            Optional<Person> personOptional = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst();
            if (personOptional.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
            }
            personToDelete = personOptional.get();

            boolean isConfirmed = MainWindow.showConfirmationDialog("Are you sure you want to delete "
                    + personToDelete.getName() + "?");
            if (!isConfirmed) {
                return new CommandResult("Deletion cancelled.");
            }

            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex))
                || (targetName != null && targetName.equals(otherDeleteCommand.targetName))
                || (policyIndex != null && policyIndex.equals(otherDeleteCommand.policyIndex));
    }

    @Override
    public String toString() {
        if (targetIndex != null) {
            if (policyIndex != null) {
                return getClass().getCanonicalName() + "{targetIndex="
                        + targetIndex + ", policyIndex=" + policyIndex + "}";
            } else {
                return getClass().getCanonicalName() + "{targetIndex=" + targetIndex + "}";
            }
        } else {
            return getClass().getCanonicalName() + "{targetName=" + targetName + "}";
        }
    }
}
