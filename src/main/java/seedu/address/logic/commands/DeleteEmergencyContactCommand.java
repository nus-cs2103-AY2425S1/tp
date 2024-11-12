package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.EmergencyContact.NO_NAME;
import static seedu.address.model.person.EmergencyContact.NO_NUMBER;

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

    public static final String COMMAND_WORD = "deleteemergency";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the emergency contact details of the patient identified "
            + "by the index number used in the last patient listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS = "Removed emergency contact (%1$s, %2$s) from "
            + "%3$s";
    public static final String MESSAGE_NO_EMERGENCY_CONTACT = "%1$s does not have a saved emergency contact";
    private final Index index;

    /**
     * @param index of the patient in the filtered patient list to delete the emergency contact details
     */
    public DeleteEmergencyContactCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    /**
     * Executes the command to delete the emergency contact of a specified patient.
     *
     * @param model The model to operate on.
     * @return A CommandResult with the appropriate success or error message.
     * @throws CommandException if the specified index is out of bounds or if no emergency contact exists.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        validateIndexWithinBounds(lastShownList);
        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (isNoEmergencyContact(personToEdit)) {
            return new CommandResult(generateNoEmergencyContactMessage(personToEdit));
        }

        EmergencyContact oldEmergencyContact = personToEdit.getEmergencyContact();
        Person editedPerson = createPersonWithoutEmergencyContact(personToEdit);

        model.updatePersonAndTasks(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson, oldEmergencyContact));
    }

    /**
     * Validates that the specified index is within the bounds of the displayed patient list.
     *
     * @param lastShownList The list of patients currently displayed.
     * @throws CommandException if the index is out of bounds.
     */
    private void validateIndexWithinBounds(List<Person> lastShownList) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if a patient does not have an emergency contact.
     *
     * @param person The patient whose emergency contact status is being checked.
     * @return true if no emergency contact exists or the contact details are empty; false otherwise.
     */
    private boolean isNoEmergencyContact(Person person) {
        EmergencyContact contact = person.getEmergencyContact();
        return contact == null || (contact.getName().equals(new Name(NO_NAME))
                && contact.getNumber().equals(new Phone(NO_NUMBER)));
    }

    /**
     * Creates a copy of the given patient with an empty emergency contact.
     *
     * @param personToEdit The original patient to edit.
     * @return A new Person object with an empty emergency contact.
     */
    private Person createPersonWithoutEmergencyContact(Person personToEdit) {
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                new EmergencyContact(new Name(NO_NAME), new Phone(NO_NUMBER)),
                personToEdit.getTags(),
                personToEdit.getPriorityLevel()
        );
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
