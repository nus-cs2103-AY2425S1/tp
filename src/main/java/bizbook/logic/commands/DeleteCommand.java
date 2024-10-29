package bizbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import bizbook.commons.core.index.Index;
import bizbook.commons.util.ToStringBuilder;
import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.person.Person;

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

    private final Index targetIndex;

    /**
     * Creates a DeleteCommand to delete the specified person.
     *
     * @param targetIndex of the person in the filtered person list to be deleted
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        Person focusedPerson = model.getFocusedPerson().get();
        if (focusedPerson.equals(personToDelete)) {
            model.getFocusedPerson().set(null);
        }
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.formatShort(personToDelete)), false, false);
        return commandResult;
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
