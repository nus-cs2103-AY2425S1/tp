package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_TO_EDIT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person by the index number used in the displayed person list or emergency contact "
            + "identified by the index number used in the displayed emergency contact list.\n"
            + "Parameters: INDEX (must be a positive integer) [" + PREFIX_EMERGENCY_CONTACT_TO_EDIT
            + "EMERGENCY CONTACT INDEX (must be a positive integer)]\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS = "Deleted Emergency Contact: %1$s";

    private final Index targetIndex;
    private final DeleteCommandDescriptor deleteCommandDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to delete
     * @param deleteCommandDescriptor carries emergency contact index, if present
     */
    public DeleteCommand(Index targetIndex, DeleteCommandDescriptor deleteCommandDescriptor) {
        this.targetIndex = targetIndex;
        this.deleteCommandDescriptor = deleteCommandDescriptor;
    }

    /**
     * Helper function which returns a new person with the updatedEmergencyContacts to abide by
     * immutability of the Person class
     * @param personToEdit  person in the filtered person list to delete
     * @param updatedEmergencyContacts updated emergency contacts list
     */
    public static Person createEditedPerson(Person personToEdit, Set<EmergencyContact> updatedEmergencyContacts) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Doctor doctor = personToEdit.getDoctor();
        Set<Tag> tagSet = personToEdit.getTags();

        return new Person(name, phone, email, address, updatedEmergencyContacts, doctor, tagSet);
    }

    private CommandResult executeDeleteEmergencyContact(Index emergencyContactIndex,
                                                        Person personToDelete,
                                                        Model model) throws CommandException {
        if (personToDelete.hasOnlyOneEmergencyContact()) {
            throw new CommandException(Messages.MESSAGE_LAST_EMERGENCY_CONTACT_INDEX);
        }

        EmergencyContact deletedEmergencyContact =
                personToDelete.getEmergencyContact(emergencyContactIndex);

        Set<EmergencyContact> updatedEmergencyContacts =
                personToDelete.removeEmergencyContact(deletedEmergencyContact);

        Person updatedPerson = createEditedPerson(personToDelete, updatedEmergencyContacts);

        // Refreshes model
        model.setPerson(personToDelete, updatedPerson);
        return new CommandResult(String.format(MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS,
                Messages.formatEmergencyContact(deletedEmergencyContact)));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        Optional<Index> emergencyContactIndex = deleteCommandDescriptor.getEmergencyContactIndex();
        if (emergencyContactIndex.isPresent()) {
            return executeDeleteEmergencyContact(emergencyContactIndex.get(), personToDelete, model);
        }
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will
     * replace the
     * corresponding field value of the person.
     */
    public static class DeleteCommandDescriptor {
        private Index emergencyContactIndex;

        public DeleteCommandDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public DeleteCommandDescriptor(DeleteCommandDescriptor toCopy) {
            setEmergencyContactIndex(toCopy.emergencyContactIndex);
        }

        public Optional<Index> getEmergencyContactIndex() {
            return Optional.ofNullable(emergencyContactIndex);
        }

        public void setEmergencyContactIndex(Index emergencyContactIndex) {
            this.emergencyContactIndex = emergencyContactIndex;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteCommandDescriptor)) {
                return false;
            }

            DeleteCommandDescriptor otherDeletCommandDescriptor = (DeleteCommandDescriptor) other;
            return Objects.equals(emergencyContactIndex, otherDeletCommandDescriptor.emergencyContactIndex);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("emergency contact index", emergencyContactIndex)
                    .toString();
        }
    }
}
