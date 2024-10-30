package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (emergencyContact.contactNumber.equals(new Phone("000"))
                || emergencyContact.contactName.equals(new Name("No Name Entered"))) {
            throw new CommandException(MESSAGE_INVALID_EMERGENCY_CONTACT_PARAMETERS);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getEmergencyContact() != null
                && !personToEdit.getEmergencyContact().contactName.equals(new Name("No Name Entered"))
                && !personToEdit.getEmergencyContact().contactNumber.equals(new Phone("000"))) {
            return new CommandResult(generateEmergencyContactExistsMessage(personToEdit));
        }
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), emergencyContact, personToEdit.getTags(), personToEdit.getPriorityLevel());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the emergency contact is added
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS, personToEdit.getName(),
                emergencyContact.getName(), emergencyContact.getNumber());
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
