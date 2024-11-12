package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeletePersonCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PERSON_ENTITY_STRING
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " " + "1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public DeletePersonCommand(Index targetIndex) {
        super(targetIndex);
    }

    /**
     * Gets the filtered list of persons in the model.
     *
     * @param model Model to get the list from.
     * @return Filtered list of persons.
     */
    @Override
    protected List<Person> getFilteredList(Model model) {
        return model.getFilteredPersonList();
    }

    /**
     * Deletes the person from the model.
     *
     * @param model Model to delete the person from.
     * @param entity Person to be deleted.
     */
    @Override
    protected void deleteEntity(Model model, Object entity) {
        requireNonNull(entity);

        assert entity instanceof Person;

        model.deletePerson((Person) entity);
    }

    /**
     * Gets the success message for deleting a person.
     *
     * @return Success message for deleting a person.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_DELETE_PERSON_SUCCESS;
    }

    /**
     * Gets the invalid index message for deleting a person.
     *
     * @return Invalid index message for deleting a person.
     */
    @Override
    protected String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    }

    /**
     * Formats the person entity to be displayed.
     *
     * @param entity Entity to be formatted.
     * @return Formatted entity.
     */
    @Override
    protected String formatEntity(Object entity) {
        assert entity instanceof Person;

        return Messages.formatPerson((Person) entity);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePersonCommand otherDeletePersonCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeletePersonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
