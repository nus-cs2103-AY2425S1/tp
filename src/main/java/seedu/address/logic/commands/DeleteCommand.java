package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index or NRIC from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or NRIC (must be government issued)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " S6253285H";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;
    private final Nric targetNric;

    /**
     * Creates a DeleteCommand to delete person at the specified {@code Index}.
     *
     * @param targetIndex The {@code Index} of the person to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetNric = null;
    }

    /**
     * Creates a DeleteCommand to delete person with the specified {@code Nric}.
     * @param targetNric The {@code Nric} of the person to be deleted.
     */
    public DeleteCommand(Nric targetNric) {
        this.targetNric = targetNric;
        this.targetIndex = null;
    }

    /**
     * Provides the action property for use in a {@code TableView}.
     * This method is needed for binding the action to the table column.
     *
     * @return the action as a {@code StringProperty}.
     */
    public StringProperty actionProperty() {
        return new SimpleStringProperty(this, "action", COMMAND_WORD);
    }

    /**
     * Provides the format example property for use in a {@code TableView}.
     * This method is needed for binding the format example to the table column.
     *
     * @return the format and example usage as a {@code StringProperty}.
     */
    public StringProperty formatExampleProperty() {
        return new SimpleStringProperty(this, "formatExample", MESSAGE_USAGE);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedFilteredPersonList();
        Person personToDelete;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else {
            personToDelete = model.getPersonByNric(targetNric);
            if (personToDelete == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
            }
        }

        if (model.isHistoryView()) {
            throw new CommandException(Messages.MESSAGE_USAGE_RESTRICTED_IN_HISTORY_VIEW);
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
        return (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex))
                || (targetNric != null && targetNric.equals(otherDeleteCommand.targetNric));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetNric", targetNric)
                .toString();
    }
}
