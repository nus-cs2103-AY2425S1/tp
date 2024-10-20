package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.academyassist.logic.Messages.MESSAGE_DUPLICATE_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.collections.transformation.FilteredList;
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
import seedu.academyassist.model.person.StudentIdMatchesPredicate;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;
import seedu.academyassist.model.tag.Tag;

/**
 * Edits the details of an existing student in the management system.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the student id. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: Student ID (5-digit number) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_IC + "IC] "
            + "[" + PREFIX_IC + "YEARGROUP] "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 12345 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Student %1$s (%2$s)’s details successfully updated.\n"
            + "Here is the updated information %3$s";
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
        FilteredList<Person> filteredPersonList = new FilteredList<>(model.getFilteredPersonList());
        filteredPersonList.setPredicate(new StudentIdMatchesPredicate(studentId));

        if (filteredPersonList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_STUDENT_FOUND);
        }

        Person personToEdit = filteredPersonList.get(0);
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
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedIc, updatedYearGroup,
                studentId, updatedSubject, updatedTags);
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
                .add("NRIC", studentId)
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
        private Set<Tag> tags;

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
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, ic, yearGroup, subjects, tags);
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

        public void setSubjects(Set<Subject> subjects) {
            this.subjects = subjects;
        }

        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
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
                    && Objects.equals(subjects, otherEditPersonDescriptor.subjects)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
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
                    .add("tags", tags)
                    .toString();
        }
    }
}
