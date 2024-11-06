package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Edits the details of an existing entity (person or appointment) in the address book.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of an entity identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: ENTITY_TYPE (person/appt) INDEX (must be a positive integer) [DATA_FIELDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " 1 "
            + PREFIX_APPOINTMENT_TYPE + "Health Checkup "
            + PREFIX_MEDICINE + "Panadol";

    protected final Index targetIndex;
    protected final EditEntityDescriptor editEntityDescriptor;

    /**
     * Constructs {@code EditCommand} with the specified index and descriptor.
     *
     * @param targetIndex Index of entity to be edited.
     */
    public EditCommand(Index targetIndex, EditEntityDescriptor editEntityDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editEntityDescriptor);
        this.targetIndex = targetIndex;
        this.editEntityDescriptor = editEntityDescriptor;
    }

    /**
     * Executes the edit command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the result of executing the command.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<?> lastShownList = getFilteredList(model);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        Object entityToEdit = lastShownList.get(targetIndex.getZeroBased());
        Object editedEntity = createEditedEntity(model, entityToEdit, editEntityDescriptor);

        if (isSameEntity(model, editedEntity, entityToEdit) && hasEntity(model, editedEntity)) {
            throw new CommandException(getDuplicateMessage());
        }

        try {
            editEntity(model, editedEntity, entityToEdit);
        } catch (DuplicatePersonException e) {
            throw new CommandException(getDuplicateMessage());
        }

        return new CommandResult(String.format(getSuccessMessage(), formatEntity(editedEntity)));
    }

    /**
     * Checks if entity already exists in list
     *
     * @param model {@code Model} which the command should operate on.
     * @param entity Entity to check
     * @return True if entity exists in list, false otherwise
     */
    protected abstract boolean hasEntity(Model model, Object entity);

    /**
     * Checks if entity to edit is the same as edited entity
     *
     * @param model {@code Model} which the command should operate on.
     * @param editedEntity Entity after editing
     * @param entityToEdit Entity to edit
     * @return True if entity is the same, false otherwise
     */
    protected abstract boolean isSameEntity(Model model, Object editedEntity, Object entityToEdit);

    /**
     * Gets the filtered list of entities in the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return List of entities in the model.
     */
    protected abstract List<?> getFilteredList(Model model);

    /**
     * Edits Entity
     *
     * @param model {@code Model} which the command should operate on.
     * @param editedEntity Entity after editing
     * @param entityToEdit Entity to edit
     */
    protected abstract void editEntity(Model model, Object editedEntity, Object entityToEdit);

    /**
     * Adds the entity to the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @param entityToEdit Entity to edit
     * @param editEntityDescriptor Descriptor for editing entity
     * @return Entity after editing
     */
    protected abstract Object createEditedEntity(Model model, Object entityToEdit,
                                                 EditEntityDescriptor editEntityDescriptor) throws CommandException;

    /**
     * Returns success message to display upon adding entity.
     */
    protected abstract String getSuccessMessage();

    /**
     * Returns duplicate message to display upon adding entity.
     */
    protected abstract String getDuplicateMessage();

    /**
     * Returns the invalid index message when the index is out of bounds.
     */
    protected abstract String getInvalidIndexMessage();

    /**
     * Formats the entity for displaying in the success message.
     */
    protected abstract String formatEntity(Object entity);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand otherEditCommand)) {
            return false;
        }

        boolean isSameIndex = targetIndex.equals(otherEditCommand.targetIndex);
        boolean isSameEntityDescriptor = editEntityDescriptor.equals(otherEditCommand.editEntityDescriptor);

        return isSameIndex && isSameEntityDescriptor;
    }

    /**
     * Abstract descriptor for editing entities.
     */
    public abstract static class EditEntityDescriptor {
        // This class can have common fields or methods for all entities.

        public EditEntityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEntityDescriptor(EditEntityDescriptor toCopy) {
            // Copy logic for common fields, if any, can go here.
        }

        @Override
        public abstract boolean equals(Object other);
    }

}
