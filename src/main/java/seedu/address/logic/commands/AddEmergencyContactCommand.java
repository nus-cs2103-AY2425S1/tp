package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_RELATIONSHIP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds an emergency contact to an existing person in the address book.
 */
public class AddEmergencyContactCommand extends Command {

    public static final String COMMAND_WORD = "addec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new emergency contact to the person "
            + "identified by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EMERGENCY_CONTACT_NAME + "EMERGENCY CONTACT NAME "
            + PREFIX_EMERGENCY_CONTACT_PHONE + "EMERGENCY CONTACT PHONE "
            + PREFIX_EMERGENCY_CONTACT_RELATIONSHIP + "EMERGENCY CONTACT RELATIONSHIP "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMERGENCY_CONTACT_NAME + "91234567 "
            + PREFIX_EMERGENCY_CONTACT_PHONE + "johndoe@example.com"
            + PREFIX_EMERGENCY_CONTACT_RELATIONSHIP + "Granddaughter";

    public static final String MESSAGE_SUCCESS = "Added emergency contact: %1$s";
    public static final String MESSAGE_DUPLICATE_EMERGENCY_CONTACT = "This person is already an emergency contact.";

    private final Index index;
    private final EmergencyContact emergencyContactToAdd;

    /**
     * @param index of the person in the filtered person list to add
     * @param emergencyContactToAdd to
     */
    public AddEmergencyContactCommand(Index index, EmergencyContact emergencyContactToAdd) {
        requireNonNull(index);
        this.index = index;
        this.emergencyContactToAdd = emergencyContactToAdd;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddEmergencyContactTo}
     * with an added {@code emergencyContactToAdd}.
     */
    public static Person createEditedPerson(Person personToEdit, EmergencyContact emergencyContactToAdd) {
        assert personToEdit != null;

        Set<EmergencyContact> personToEditEmergencyContacts = personToEdit.getEmergencyContacts();
        Set<EmergencyContact> updatedEmergencyContacts = new LinkedHashSet<>(personToEditEmergencyContacts);
        updatedEmergencyContacts.add(emergencyContactToAdd);

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Doctor doctor = personToEdit.getDoctor();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, updatedEmergencyContacts, doctor, tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddEmergencyContactTo = lastShownList.get(index.getZeroBased());

        if (personToAddEmergencyContactTo.hasEmergencyContact(emergencyContactToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMERGENCY_CONTACT);
        }

        Person editedPerson = createEditedPerson(personToAddEmergencyContactTo, emergencyContactToAdd);

        model.setPerson(personToAddEmergencyContactTo, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEmergencyContactCommand)) {
            return false;
        }

        AddEmergencyContactCommand otherAddEmergencyContactCommand = (AddEmergencyContactCommand) other;
        return emergencyContactToAdd.equals(otherAddEmergencyContactCommand.emergencyContactToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", emergencyContactToAdd)
                .toString();
    }
}
