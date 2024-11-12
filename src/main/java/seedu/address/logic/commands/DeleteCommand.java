package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

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
            + "ENTITY_TYPE (person/appt) "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " 1\n"
            + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " 1";

    protected final Index targetIndex;

    /**
     * Constructs a DeleteCommand with the specified index.
     *
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
     *
     * @param model Model to get the list from.
     * @return Filtered list of entities.
     */
    protected abstract List<?> getFilteredList(Model model);

    /**
     * Deletes the entity from the model.
     *
     * @param model Model to delete entity from.
     * @param entity Entity to delete.
     */
    protected abstract void deleteEntity(Model model, Object entity);

    /**
     * Returns the success message to display upon deleting entity.
     *
     * @return Success message.
     */
    protected abstract String getSuccessMessage();

    /**
     * Returns the invalid index message when the index is out of bounds.
     *
     * @return Invalid index message.
     */
    protected abstract String getInvalidIndexMessage();

    /**
     * Formats the entity for displaying in the success message.
     *
     * @param entity Entity to format.
     * @return Formatted entity.
     */
    protected abstract String formatEntity(Object entity);
}
