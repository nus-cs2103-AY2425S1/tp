package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.ui.ConfirmationWindow;


/**
 * Deletes a person identified using their name or index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name or index in the address book.\n"
            + "Parameters: NAME or INDEX (must match exactly one person or be a valid index)\n"
            + "Example: " + COMMAND_WORD + " John Doe\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_PERSON_CANCELLED = "Delete action cancelled.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person's name provided is invalid";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to delete %1$s from your address book?\n"
            + "This action is IRREVERSIBLE.";
    private final Name targetName;
    private final Index targetIndex;
    private Boolean isConfirmed;

    /**
     * @param targetName of the person to be deleted in the list
     */
    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    /**
     * @param targetIndex of the index of the person to be deleted in the list
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * This constructor should only be used for testing purposes to skip confirmation window.
     * @param targetName of the person to be deleted in the list
     * @param isConfirmed skips confirmation window and provides the result for confirm deletion.
     */
    public DeleteCommand(Name targetName, boolean isConfirmed) {
        this.targetName = targetName;
        this.isConfirmed = isConfirmed;
        this.targetIndex = null;
    }

    /**
     * This constructor should only be used for testing purposes to skip confirmation window.
     * @param targetIndex of the index of the person to be deleted in the list
     * @param isConfirmed skips confirmation window and provides the result for confirm deletion.
     */
    public DeleteCommand(Index targetIndex, boolean isConfirmed) {
        this.targetIndex = targetIndex;
        this.isConfirmed = isConfirmed;
        this.targetName = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete = findPersonToDelete(lastShownList);

        if (isConfirmed != null) {
            return processDeleteAction(model, personToDelete, isConfirmed);
        }

        return processDeleteAction(model, personToDelete, confirmDeletion(personToDelete));
    }

    /**
     * Processes the deletion action based on confirmation.
     *
     * @param model The current model.
     * @param personToDelete The person to delete.
     * @param confirmed The confirmation status.
     * @return CommandResult based on the confirmation.
     */
    private CommandResult processDeleteAction(Model model, Person personToDelete, boolean confirmed) {
        requireNonNull(model);
        if (confirmed) {
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
        }
        return new CommandResult(MESSAGE_DELETE_PERSON_CANCELLED);
    }


    /**
     * Finds the person to delete based on the provided name or index.
     *
     * @param lastShownList The list of persons currently shown.
     * @return The person to delete.
     * @throws CommandException if the person cannot be found.
     */
    private Person findPersonToDelete(List<Person> lastShownList) throws CommandException {
        if (targetName != null) {
            return findPersonByName(lastShownList);
        } else {
            return findPersonByIndex(lastShownList);
        }
    }

    /**
     * Finds a person by name.
     *
     * @param lastShownList The list of persons currently shown.
     * @return The person found.
     * @throws CommandException if the person is not found.
     */
    private Person findPersonByName(List<Person> lastShownList) throws CommandException {
        Optional<Person> personOptional = lastShownList.stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst();

        if (personOptional.isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        return personOptional.get();
    }

    /**
     * Finds a person by index.
     *
     * @param lastShownList The list of persons currently shown.
     * @return The person found.
     * @throws CommandException if the index is invalid.
     */
    private Person findPersonByIndex(List<Person> lastShownList) throws CommandException {
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }
        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Shows a confirmation dialog to the user and returns whether the deletion was confirmed.
     *
     * @param personToDelete The person to be deleted.
     * @return true if the deletion is confirmed; false otherwise.
     */
    private boolean confirmDeletion(Person personToDelete) {
        ConfirmationWindow confirmationWindow = ConfirmationWindow.getInstance();
        return confirmationWindow.showAlertDialogAndWait(
                "Confirm Delete",
                String.format(MESSAGE_CONFIRMATION, personToDelete.getName())
        );
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


        return (targetName != null && targetName.equals(otherDeleteCommand.targetName))
                || (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex));
    }

    @Override
    public String toString() {
        if (targetName != null) {
            return String.format("DeleteCommand[targetName=%s]", targetName);
        } else {
            return String.format("DeleteCommand[targetIndex=%d]", targetIndex.getOneBased());
        }
    }
}
