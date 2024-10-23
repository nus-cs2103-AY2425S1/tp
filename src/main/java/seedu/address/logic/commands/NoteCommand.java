package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;


/**
 * Edits the details of an existing person in the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the person identified "
                                               + "Parameters: NAME (must be an exact match) "
                                               + "[" + PREFIX_APPOINTMENT + "PREVIOUS APPOINTMENT] "
                                               + "[" + PREFIX_REMARK + "REMARK] "
                                               + "[" + PREFIX_MEDICATION + "MEDICATION]...\n"
                                               + "Example: " + COMMAND_WORD + " John Doe "
                                               + PREFIX_APPOINTMENT + "01/01/2025 1200 "
                                               + PREFIX_REMARK + "Allergic to Ibuprofen ";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited Person's Note: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Name name;
    private final NoteDescriptor noteDescriptor;

    /**
     * @param name of the person in the filtered person list to edit
     * @param noteDescriptor details to edit the person with
     */
    public NoteCommand(Name name, NoteDescriptor noteDescriptor) {
        requireNonNull(name);
        requireNonNull(noteDescriptor);

        this.name = name;
        this.noteDescriptor = new NoteDescriptor(noteDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (!model.hasName(name)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
        }

        Person personToEdit = lastShownList.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME));

        Person editedPerson = createEditedPerson(personToEdit, noteDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS, editedPerson.getNote().toString()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, NoteDescriptor noteDescriptor) {
        assert personToEdit != null;
        Note personToEditNote = personToEdit.getNote();

        Set<Appointment> updatedAppointment = new HashSet<>(noteDescriptor.getAppointments()
                .orElse(personToEditNote.previousAppointments));

        if (!updatedAppointment.isEmpty()) {
            updatedAppointment.addAll(personToEditNote.previousAppointments);
        }

        Set<String> updatedMedication = new HashSet<>(noteDescriptor.getMedications()
                .orElse(personToEditNote.medications));

        if (!updatedMedication.isEmpty()) {
            updatedMedication.addAll(personToEditNote.medications);
        }

        Set<String> updatedRemark = new HashSet<>(noteDescriptor.getRemarks()
                .orElse(personToEditNote.remarks));

        if (!updatedRemark.isEmpty()) {
            updatedRemark.addAll(personToEditNote.remarks);
        }

        Note updatedNote = new Note(updatedAppointment, updatedRemark, updatedMedication);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getAge(), personToEdit.getSex(), personToEdit.getAppointment(),
                personToEdit.getTags(), updatedNote, personToEdit.getStarredStatus());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherNoteCommand = (NoteCommand) other;
        return name.equals(otherNoteCommand.name)
               && noteDescriptor.equals(otherNoteCommand.noteDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("NoteDescriptor", noteDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class NoteDescriptor {
        private Set<Appointment> appointments;
        private Set<String> medications;
        private Set<String> remarks;

        public NoteDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public NoteDescriptor(NoteDescriptor toCopy) {
            setAppointments(toCopy.appointments);
            setMedications(toCopy.medications);
            setRemarks(toCopy.remarks);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(appointments, medications, remarks);
        }

        /**
         * Sets {@code appointments} to this object's {@code appointments}.
         * A defensive copy of {@code appointments} is used internally.
         */
        public void setAppointments(Set<Appointment> appointments) {
            this.appointments = (appointments != null) ? new HashSet<>(appointments) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code appointments} is null.
         */
        public Optional<Set<Appointment>> getAppointments() {
            return (appointments != null) ? Optional.of(Collections.unmodifiableSet(appointments)) : Optional.empty();
        }

        /**
         * Sets {@code medications} to this object's {@code medications}.
         * A defensive copy of {@code medications} is used internally.
         */
        public void setMedications(Set<String> medications) {
            this.medications = (medications != null) ? new HashSet<>(medications) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medications} is null.
         */
        public Optional<Set<String>> getMedications() {
            return (medications != null) ? Optional.of(Collections.unmodifiableSet(medications)) : Optional.empty();
        }

        /**
         * Sets {@code medications} to this object's {@code medications}.
         * A defensive copy of {@code medications} is used internally.
         */
        public void setRemarks(Set<String> remarks) {
            this.remarks = (remarks != null) ? new HashSet<>(remarks) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medications} is null.
         */
        public Optional<Set<String>> getRemarks() {
            return (remarks != null) ? Optional.of(Collections.unmodifiableSet(remarks)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof NoteDescriptor)) {
                return false;
            }

            NoteDescriptor otherNoteDescriptor = (NoteDescriptor) other;
            return Objects.equals(remarks, otherNoteDescriptor.remarks)
                   && Objects.equals(medications, otherNoteDescriptor.medications)
                   && Objects.equals(appointments, otherNoteDescriptor.appointments);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("appointments", appointments)
                    .add("medications", medications)
                    .add("remarks", remarks)
                    .toString();
        }
    }
}
