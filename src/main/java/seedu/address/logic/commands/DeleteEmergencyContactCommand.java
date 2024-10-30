package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Deletes the emergency contact of an existing patient in the address book.
 */
public class DeleteEmergencyContactCommand extends Command {

    public static final String COMMAND_WORD = "delemergency";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the emergency contact details of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS = "Removed emergency contact (%1$s, %2$s) from "
            + "%3$s";
    public static final String MESSAGE_NO_EMERGENCY_CONTACT = "%1$s does not have a saved emergency contact";
    private final Index index;

    /**
     * @param index of the person in the filtered person list to delete the emergency contact details
     */
    public DeleteEmergencyContactCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getEmergencyContact() == null
                || (personToEdit.getEmergencyContact().contactName.equals(new Name("No Name Entered"))
                && personToEdit.getEmergencyContact().contactNumber.equals(new Phone("000")))) {
            return new CommandResult(generateNoEmergencyContactMessage(personToEdit));
        }

        EmergencyContact toBeDeleted = personToEdit.getEmergencyContact();
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), new EmergencyContact(new Name("No Name Entered"), new Phone("000")),
                personToEdit.getTags(), personToEdit.getPriorityLevel());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson, toBeDeleted));
    }

    /**
     * Generates a command execution success message based on whether the emergency contact is deleted
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit, EmergencyContact toBeDeleted) {
        return String.format(MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS, toBeDeleted.getName(), toBeDeleted.getNumber(),
                personToEdit.getName());
    }

    private String generateNoEmergencyContactMessage(Person personToEdit) {
        return String.format(MESSAGE_NO_EMERGENCY_CONTACT, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof DeleteEmergencyContactCommand)) {
            return false;
        }
        // state check
        DeleteEmergencyContactCommand e = (DeleteEmergencyContactCommand) other;
        return index.equals(e.index);
    }
}
