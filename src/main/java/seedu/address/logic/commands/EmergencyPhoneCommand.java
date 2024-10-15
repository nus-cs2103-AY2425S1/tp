package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmergencyPhone;
import seedu.address.model.person.Person;

/**
 * Add or remove the emergency phone number of an existing student in the address book
 */
public class EmergencyPhoneCommand extends Command {

    public static final String COMMAND_WORD = "emergencyPhone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an emergency phone number to the student "
            + "identified by the index.\n"
            + "Parameters: [INDEX] en/[EMERGENCY_NUMBER]\n"
            + "Example: " + COMMAND_WORD + " 1 ep/91234567";

    public static final String MESSAGE_ADD_EMERGENCY_PHONE_SUCCESS = "Added emergency phone for %1$s\n"
            + "Emergency Contact Number: %2$s";

    public static final String MESSAGE_DELETE_EMERGENCY_PHONE_SUCCESS = "Removed emergency phone for %1$s\n"
            + "Emergency Contact Number: %2$s";

    private final Index index;
    private final EmergencyPhone emergencyPhone;

    /**
     * @param index index of the person in the filtered person list to edit the emergency contact phone
     * @param emergencyPhone emergency contact phone of the person to be updated to
     */
    public EmergencyPhoneCommand(Index index, EmergencyPhone emergencyPhone) {
        requireAllNonNull(index, emergencyPhone);

        this.index = index;
        this.emergencyPhone = emergencyPhone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), emergencyPhone, personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the emergency phone is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !emergencyPhone.value.isEmpty()
                ? MESSAGE_ADD_EMERGENCY_PHONE_SUCCESS : MESSAGE_DELETE_EMERGENCY_PHONE_SUCCESS;
        return String.format(message, personToEdit.getName(), personToEdit.getEmergencyPhone());
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
        return index.equals(e.index)
                && emergencyPhone.equals(e.emergencyPhone);
    }
}
