package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ID + "ID] "
            + "[" + PREFIX_WARD + "WARD] "
            + "[" + PREFIX_DIAGNOSIS + "DIAGNOSIS] "
            + "[" + PREFIX_MEDICATION + "MEDICATION] "
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ID + "P12345 "
            + PREFIX_WARD + "A2";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: \n\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Id updatedId = editPersonDescriptor.getId().orElse(personToEdit.getId());
        Ward updatedWard = editPersonDescriptor.getWard().orElse(personToEdit.getWard());
        Diagnosis updatedDiagnosis = editPersonDescriptor.getDiagnosis().orElse(personToEdit.getDiagnosis());
        Medication updatedMedication = editPersonDescriptor.getMedication().orElse(personToEdit.getMedication());
        Notes updatedNotes = personToEdit.getNotes();
        Appointment updatedAppointment = personToEdit.getAppointment();
        /*
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

         */

        return new Person(updatedName, updatedId, updatedWard, updatedDiagnosis, updatedMedication,
                updatedNotes, updatedAppointment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {

        private Id id;
        private Name name;
        private Ward ward;
        private Diagnosis diagnosis;
        private Medication medication;

        /*
        private Set<Tag> tags;

         */

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setId(toCopy.id);
            setName(toCopy.name);
            setWard(toCopy.ward);
            setDiagnosis(toCopy.diagnosis);
            setMedication(toCopy.medication);
            /*
            setTags(toCopy.tags);

             */
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, name, ward, diagnosis, medication);
        }

        public void setId(Id id) {
            this.id = id;
        }

        public Optional<Id> getId() {
            return Optional.ofNullable(id);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setWard(Ward ward) {
            this.ward = ward;
        }

        public Optional<Ward> getWard() {
            return Optional.ofNullable(ward);
        }

        public void setDiagnosis(Diagnosis diagnosis) {
            this.diagnosis = diagnosis;
        }

        public Optional<Diagnosis> getDiagnosis() {
            return Optional.ofNullable(diagnosis);
        }

        public void setMedication(Medication medication) {
            this.medication = medication;
        }

        public Optional<Medication> getMedication() {
            return Optional.ofNullable(medication);
        }

        /*
        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        /*
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

         */

        /*
        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        /*
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

         */


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(id, otherEditPersonDescriptor.id)
                    && Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(ward, otherEditPersonDescriptor.ward)
                    && Objects.equals(diagnosis, otherEditPersonDescriptor.diagnosis)
                    && Objects.equals(medication, otherEditPersonDescriptor.medication);
            /*
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);

            */
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("id", id)
                    .add("name", name)
                    .add("ward", ward)
                    .add("diagnosis", diagnosis)
                    .add("medication", medication)
                    /*
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)

                     */
                    .toString();
        }
    }
}
