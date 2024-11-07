package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.academyassist.logic.Messages.MESSAGE_DUPLICATE_IC;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.academyassist.commons.util.CollectionUtil;
import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;

/**
 * Edits the details of an existing student in the management system.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the student id. Existing values will be overwritten by the input values.\n"
            + "Format: " + COMMAND_WORD + " STUDENT_ID FIELD/VALUE [FIELD/VALUE]...\n"
            + "(" + StudentId.MESSAGE_CONSTRAINTS + ")\n"
            + "Parameters Example:\n"
            + "- STUDENT_ID: S1234567A\n"
            + "- FIELD/VALUE: n/John\n"
            + "- [FIELD/VALUE: p/98765432]...\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Student %1$s (%2$s)’s details successfully updated.\n"
            + "Here is the updated information\nName: %3$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final StudentId studentId;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param studentId of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(StudentId studentId, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(studentId);
        requireNonNull(editPersonDescriptor);

        this.studentId = studentId;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPersonWithStudentId(studentId)) {
            throw new CommandException(Messages.MESSAGE_NO_STUDENT_FOUND);
        }

        Person personToEdit = model.getPersonWithStudentId(studentId);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        boolean hasIcModified = !personToEdit.getIc().equals(editedPerson.getIc());

        // If NRIC is edited, check if it causes duplicate (another student having the same IC)
        if (hasIcModified && model.hasPersonWithIc(editedPerson.getIc())) {
            throw new CommandException(MESSAGE_DUPLICATE_IC);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit.getName(), studentId,
                Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Ic updatedIc = editPersonDescriptor.getIc().orElse(personToEdit.getIc());
        YearGroup updatedYearGroup = editPersonDescriptor.getYearGroup().orElse(personToEdit.getYearGroup());
        StudentId studentId = personToEdit.getStudentId();
        Set<Subject> updatedSubject = editPersonDescriptor.getSubjects().orElse(personToEdit.getSubjects());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedIc, updatedYearGroup,
                studentId, updatedSubject);
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
        return studentId.equals(otherEditCommand.studentId)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Ic ic;
        private YearGroup yearGroup;
        private Set<Subject> subjects;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setIc(toCopy.ic);
            setYearGroup(toCopy.yearGroup);
            setSubjects(toCopy.subjects);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, ic, yearGroup, subjects);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setIc(Ic ic) {
            this.ic = ic;
        }

        public Optional<Ic> getIc() {
            return Optional.ofNullable(ic);
        }

        public void setYearGroup(YearGroup yearGroup) {
            this.yearGroup = yearGroup;
        }

        public Optional<YearGroup> getYearGroup() {
            return Optional.ofNullable(yearGroup);
        }

        /**
         * Sets {@code subjects} to this object's {@code subjects}.
         * A defensive copy of {@code subjects} is used internally.
         */
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(new HashSet<>(subjects)) : Optional.empty();
        }

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
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(ic, otherEditPersonDescriptor.ic)
                    && Objects.equals(yearGroup, otherEditPersonDescriptor.yearGroup)
                    && Objects.equals(subjects, otherEditPersonDescriptor.subjects);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("ic", ic)
                    .add("year group", yearGroup)
                    .add("subjects", subjects)
                    .toString();
        }
    }
}
