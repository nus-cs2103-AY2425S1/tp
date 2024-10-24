package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an abstract command for deleting an object of type {@code T} from the address book.
 */
public abstract class DeleteCommand<T> extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the entity identified by subcommand 'contact', 'company' or 'job' and "
            + "the index number used in the displayed person list.\n"
            + "Parameters: [contact/job/company] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " contact 1";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<T> lastShownList = getEntityList(model);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        T entityToDelete = lastShownList.get(targetIndex.getZeroBased());
        deleteEntity(model, entityToDelete);

        return new CommandResult(getSuccessMessage(entityToDelete));
    }

    /**
     * Returns the filtered list of entities (contacts, jobs, companies) based on the subclass's context.
     */
    protected abstract List<T> getEntityList(Model model);

    /**
     * Deletes the entity from the model.
     */
    protected abstract void deleteEntity(Model model, T entityToDelete);

    /**
     * Returns the success message for deleting the entity.
     */
    protected abstract String getSuccessMessage(T entityToDelete);
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand<?> otherDeleteCommand = (DeleteCommand<?>) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
