package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.Seller;

/**
 * Adds a property to an existing person in the address book.
 * The person is identified by the index in the filtered person list.
 * The new property will overwrite the existing property.
 */
public class AddPropertyCommand extends Command {
    public static final String COMMAND_WORD = "prop";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the property of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing property will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PROPERTY + "[property]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROPERTY + "37B Clementi Rd.";
    public static final String MESSAGE_ADD_PROPERTY_SUCCESS = "Assigned %1$s to %2$s";
    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Removed assigned property for %1$s";

    private final Index index;
    private final Property property;

    /**
     * Constructs an {@code AddPropertyCommand} to update the property of a specific person.
     *
     * @param index The index of the person in the filtered person list.
     * @param property The new property to be added to the person.
     */
    public AddPropertyCommand(Index index, Property property) {
        requireAllNonNull(index, property);

        this.index = index;
        this.property = property;
    }

    /**
     * Executes the command to add or update the property for the specified person.
     *
     * @param model The model which contains the list of persons and handles command execution.
     * @return The result of executing the command.
     * @throws CommandException If the index is invalid or the person cannot be found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;
        if (personToEdit instanceof Buyer) {
            editedPerson = new Buyer(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getTags(), personToEdit.getAppointment(), personToEdit.getProperty()
            );
        } else {
            editedPerson = new Seller(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getTags(), personToEdit.getAppointment(), personToEdit.getProperty()
            );
        }
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a success message based on whether the property is added or removed.
     *
     * @param personToEdit The person whose property is being added or removed.
     * @return A success message string.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !property.toString().isEmpty()
                ? MESSAGE_ADD_PROPERTY_SUCCESS
                : MESSAGE_DELETE_PROPERTY_SUCCESS;
        return String.format(message, property, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPropertyCommand)) {
            return false;
        }

        AddPropertyCommand e = (AddPropertyCommand) other;
        return index.equals(e.index)
                && property.equals(e.property);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .add("property", property)
                .toString();
    }
}
