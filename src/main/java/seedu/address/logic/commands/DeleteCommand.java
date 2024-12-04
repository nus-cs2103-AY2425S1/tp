package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person: %1$s";
    public static final String MESSAGE_DELETE_PERSON_FAILURE = "Deletion canceled";
    public static final String MESSAGE_UNDO_SUCCESS = "Reverted deletion of person: %1$s";
    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);
    private final Index targetIndex;
    private boolean isConfirmed;
    private Person deletedPerson;

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code Index}.
     */
    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.isConfirmed = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (!isConfirmed) {
            logger.info("delete is canceled");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Are you sure you want to delete this person?");
            alert.setContentText(String.format("Name: %s", personToDelete.getName()));
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result != ButtonType.OK) {
                throw new CommandException(String.format(MESSAGE_DELETE_PERSON_FAILURE));
            }
        }

        model.deletePerson(personToDelete);
        deletedPerson = personToDelete;
        isExecuted = true;
        logger.info("delete successful");
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * set the is Confirmed value for the test cases to jump over the delete popout
     */
    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @Override
    public CommandResult undo(Model model) {
        requireExecuted();
        requireAllNonNull(model, deletedPerson);
        model.insertPerson(deletedPerson, targetIndex);
        isExecuted = false;
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, Messages.format(deletedPerson)));
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
