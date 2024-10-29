package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes an entity identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the entity identified by the index number used in the displayed list.\n"
            + "Parameters: "
            + "ENTITY_TYPE (person or appt) "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + "person" + " " + "1";

    protected final Index targetIndex;

    /**
     * @param targetIndex Index of entity to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<?> lastShownList = getFilteredList(model);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        Object entityToDelete = lastShownList.get(targetIndex.getZeroBased());
        deleteEntity(model, entityToDelete);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity(entityToDelete)));
    }

    /**
     * Gets the filtered list of entities in the model.
     */
    protected abstract List<?> getFilteredList(Model model);

    /**
     * Deletes the entity from the model.
     */
    protected abstract void deleteEntity(Model model, Object entity) throws CommandException;

    /**
     * Returns the success message to display upon deleting entity.
     */
    protected abstract String getSuccessMessage();

    /**
     * Returns the invalid index message when the index is out of bounds.
     */
    protected abstract String getInvalidIndexMessage();

    /**
     * Formats the entity for displaying in the success message.
     */
    protected abstract String formatEntity(Object entity);
}
