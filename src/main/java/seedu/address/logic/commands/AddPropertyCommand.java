package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.Remark;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class AddPropertyCommand extends Command {
    public static final String COMMAND_WORD = "prop";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the property of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing property will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PROPERTY + "[property]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROPERTY + "37B Clementi Rd.";
    public static final String MESSAGE_ADD_PROPERTY_SUCCESS = "Added property to Person: %1$s";
    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Removed property from Person: %1$s";

    private final Index index;
    private final Property property;

    /**
     * @param index of the person in the filtered person list to edit the chosen property
     * @param property of the person to be updated to
     */
    public AddPropertyCommand(Index index, Property property) {
        requireAllNonNull(index, property);

        this.index = index;
        this.property = property;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), property, personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the property is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !property.getProperty().isEmpty() ? MESSAGE_ADD_PROPERTY_SUCCESS : MESSAGE_DELETE_PROPERTY_SUCCESS;
        return String.format(message, personToEdit);
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
}
