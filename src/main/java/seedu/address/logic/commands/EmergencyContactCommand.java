package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Person;

/**
 * Changes the emergency contact of an existing person in the address book.
 */
public class EmergencyContactCommand extends Command {

    public static final String COMMAND_WORD = "emergency";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the emergency contact details of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "[EMERGENCY CONTACT NAME]"
            + PREFIX_PHONE + "[EMERGENCY CONTACT NUMBER\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Richard Ng "
            + PREFIX_PHONE + "82943718";

    public static final String MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS = "Added emergency contact to %1$s: %2$s, %3$s";
    public static final String MESSAGE_EMERGENCY_CONTACT_EXISTS = "%1$s already has a saved emergency contact";

    public static final String MESSAGE_INVALID_EMERGENCY_CONTACT_PARAMETERS = "Please make sure both name and phone "
            + "number is filled! Command details:\n" + MESSAGE_USAGE;

    private final Index index;
    private final EmergencyContact emergencyContact;

    /**
     * @param index of the person in the filtered person list to add the emergency contact details
     * @param emergencyContact of the person to be updated to
     */
    public EmergencyContactCommand(Index index, EmergencyContact emergencyContact) {
        requireAllNonNull(index, emergencyContact);
        this.index = index;
        this.emergencyContact = emergencyContact;
    }

    /**
     * Edits a person's emergency contact information.
     *
     * @param model The model to operate on.
     * @return CommandResult with success or error message.
     * @throws CommandException if index is out of bounds or emergency contact parameters are invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        validateIndexWithinBounds(lastShownList);
        validateEmergencyContactParameters();

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (isExistingEmergencyContact(personToEdit)) {
            return new CommandResult(generateEmergencyContactExistsMessage(personToEdit));
        }

        Person editedPerson = createEditedPerson(personToEdit);
        model.updatePersonAndTasks(personToEdit, editedPerson);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Validates that the specified index is within the bounds of the displayed person list.
     *
     * @param lastShownList The list of persons currently displayed.
     * @throws CommandException if the index is out of bounds.
     */
    private void validateIndexWithinBounds(List<Person> lastShownList) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Validates that the emergency contact parameters are non-empty.
     *
     * @throws CommandException if the emergency contact parameters are invalid (empty contact name or number).
     */
    private void validateEmergencyContactParameters() throws CommandException {
        if (emergencyContact.contactNumber.isEmpty() || emergencyContact.contactName.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_EMERGENCY_CONTACT_PARAMETERS);
        }
    }

    /**
     * Checks if a person already has a non-empty emergency contact.
     *
     * @param personToEdit The person whose emergency contact is being checked.
     * @return true if an emergency contact already exists and is non-empty; false otherwise.
     */
    private boolean isExistingEmergencyContact(Person personToEdit) {
        EmergencyContact contact = personToEdit.getEmergencyContact();
        return contact != null && !contact.contactName.isEmpty() && !contact.contactNumber.isEmpty();
    }

    /**
     * Creates an edited person with the specified emergency contact while retaining other attributes.
     *
     * @param personToEdit The original person to edit.
     * @return A new Person object with updated emergency contact details.
     */
    private Person createEditedPerson(Person personToEdit) {
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                emergencyContact,
                personToEdit.getTags(),
                personToEdit.getPriorityLevel()
        );
    }

    /**
     * Generates a command execution success message based on whether the emergency contact is added
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS, personToEdit.getName(),
                emergencyContact.contactName, emergencyContact.contactNumber);
    }

    private String generateEmergencyContactExistsMessage(Person personToEdit) {
        return String.format(MESSAGE_EMERGENCY_CONTACT_EXISTS, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EmergencyContactCommand)) {
            return false;
        }
        // state check
        EmergencyContactCommand e = (EmergencyContactCommand) other;
        return index.equals(e.index)
                && emergencyContact.getName().equals(e.emergencyContact.getName())
                && emergencyContact.getNumber().equals(e.emergencyContact.getNumber());
    }
}
