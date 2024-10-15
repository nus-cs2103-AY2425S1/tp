package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmergencyPhone;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Changes the emergency contact number of an existing student in the address book
 */
public class EmergencyPhoneCommand extends Command {

    public static final String COMMAND_WORD = "addEmergencyContactNumber";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an emergency contact number to the student "
            + "identified by the name. "
            + "Parameters: n/NAME en/[EMERGENCY_NUMBER]\n"
            + "Example: " + COMMAND_WORD + " n/Henry ep/91234567";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, ECNumber: %2$s";
    public static final String MESSAGE_INVALID_NAME = "The name you entered is not in the addressbook!";
    public static final String MESSAGE_EMERGENCY_PHONE_SUCCESS = "New Emergency Phone Number %1$s, for %2$s";

    private final Name name;
    private final EmergencyPhone emergencyPhone;

    /**
     * @param name name of the person in the filtered person list to edit the emergency contact phone
     * @param emergencyPhone emergency contact phone of the person to be updated to
     */
    public EmergencyPhoneCommand(Name name, EmergencyPhone emergencyPhone) {
        requireAllNonNull(name, emergencyPhone);

        this.name = name;
        this.emergencyPhone = emergencyPhone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> fullList = model.getFilteredPersonList();
        Optional<Person> personToUpdate = fullList.stream().filter(person -> person.getName().equals(name)).findFirst();
        if (personToUpdate.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        }
        Person personToEdit = personToUpdate.get();
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), emergencyPhone, personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
//        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EMERGENCY_PHONE_SUCCESS, emergencyPhone, name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EmergencyPhoneCommand)) {
            return false;
        }

        EmergencyPhoneCommand e = (EmergencyPhoneCommand) other;
        return name.equals(e.name)
                && emergencyPhone.equals(e.emergencyPhone);
    }
}
