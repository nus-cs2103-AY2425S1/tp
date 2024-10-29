package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.controller.ConfirmationController;
import seedu.address.logic.commands.controller.ConfirmationWindowController;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;


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
    private final ConfirmationController confirmationController;

    /**
     * @param targetName of the person to be deleted in the list
     */
    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
        this.confirmationController = new ConfirmationWindowController();
    }

    /**
     * @param targetIndex of the index of the person to be deleted in the list
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.confirmationController = new ConfirmationWindowController();
    }

    /**
     * This constructor should only be used for testing purposes to skip confirmation window.
     * @param targetName of the person to be deleted in the list
     * @param confirmationController provides the result for confirm deletion.
     */
    public DeleteCommand(Name targetName, ConfirmationController confirmationController) {
        this.targetName = targetName;
        this.targetIndex = null;
        this.confirmationController = confirmationController;
    }

    /**
     * This constructor should only be used for testing purposes to skip confirmation window.
     * @param targetIndex of the index of the person to be deleted in the list
     * @param confirmationController provides the result for confirm deletion.
     */
    public DeleteCommand(Index targetIndex, ConfirmationController confirmationController) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.confirmationController = confirmationController;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete = findPersonToDelete(lastShownList);
        boolean isConfirmed = isDeletionConfirmed(personToDelete);
        return processDeleteAction(model, personToDelete, isConfirmed);
    }

    /**
     * Checks if the deletion action is confirmed by the user.
     *
     * @param personToDelete The person to be deleted.
     * @return true if the user confirms deletion, false otherwise.
     */
    private boolean isDeletionConfirmed(Person personToDelete) {
        return confirmationController.isConfirmed(
                "Confirm Delete",
                String.format(MESSAGE_CONFIRMATION, personToDelete.getName())
        );
    }

    /**
     * Processes the deletion action based on confirmation.
     *
     * @param model The current model.
     * @param personToDelete The person to delete.
     * @param isConfirmed The confirmation status.
     * @return CommandResult based on the confirmation.
     */
    private CommandResult processDeleteAction(Model model, Person personToDelete, boolean isConfirmed) {
        requireNonNull(model);
        if (isConfirmed) {
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
