package seedu.address.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_STUDENT_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditStudentCommand extends Command {

    public static final String COMMAND_WORD = "edit_s";
    public static final String COMMAND_WORD_ALIAS = "es";


    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Edits the student based on the index when listing all students. "
        + "Fields including group and student number cannot be modified.\n"
        + "Parameters: "
        + PREFIX_INDEX + "INDEX "
        + "[" + PREFIX_STUDENT_NAME + "NAME] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_TAG + "TAG]... \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDEX + "1 "
        + PREFIX_STUDENT_NAME + "John Leow Doe "
        + PREFIX_EMAIL + "newjohn@u.nus.edu "
        + PREFIX_TAG + "goodatAI";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "No changes made to the existing tag(s): [%1$s].";
    public static final String MESSAGE_DUPLICATE_EMAIL = "Student email [%1$s] is not edited.";
    public static final String MESSAGE_DUPLICATE_NAME = "Student name [%1$s] is not edited.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "The given student number is not found in the address book.";
    public static final String MESSAGE_INVALID_FIELD_GROUP_NAME =
        "Group assignment should not be changed via edit student function.";
    public static final String MESSAGE_INVALID_FIELD_STUDENT_NUMBER = "Student number should not be changed.";
    public static final String MESSAGE_DUPLICATE_FIELD_WARNING =
        "ATTENTION: No changes detected in the following fields:";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the student in the filtered student list to edit
     * @param editPersonDescriptor details to edit the student with
     */
    public EditStudentCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getAddressBook().getStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedPerson(studentToEdit, editPersonDescriptor);
        boolean isSameName = editPersonDescriptor.getName().isPresent()
            && studentToEdit.getName().equals(editedStudent.getName());
        boolean isSameEmail = editPersonDescriptor.getEmail().isPresent()
            && studentToEdit.getEmail().equals(editedStudent.getEmail());
        boolean hasDuplicatedTags = editPersonDescriptor.getTags().isPresent()
            && !Collections.disjoint(studentToEdit.getTags(), editedStudent.getTags());
        List<String> errorMessages = new ArrayList<>();

        if (studentToEdit.equals(editedStudent)) {
            errorMessages.add(MESSAGE_DUPLICATE_PERSON);
        }
        if (isSameName) {
            errorMessages.add(String.format(MESSAGE_DUPLICATE_NAME, editedStudent.getName()));
        }
        if (isSameEmail) {
            errorMessages.add(String.format(MESSAGE_DUPLICATE_EMAIL, editedStudent.getEmail()));
        }
        if (hasDuplicatedTags) {
            String duplicatedTags = getDuplicatedTags(studentToEdit.getTags(), editedStudent.getTags());
            errorMessages.add(String.format(MESSAGE_DUPLICATE_TAG, duplicatedTags));
        }
        if (studentToEdit.equals(editedStudent)) {
            String errorMessage = String.join("\n", errorMessages);
            throw new CommandException(errorMessage);
        }
        model.setPerson(studentToEdit, editedStudent);
        if (studentToEdit.getGroupName().isPresent()) {
            Group originalStudentGroup = model.getGroupByName(studentToEdit.getGroupName().get());
            originalStudentGroup.delete(studentToEdit);
            model.addPersonToGroup(editedStudent, originalStudentGroup);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (!errorMessages.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStudent)) + "\n"
                + MESSAGE_DUPLICATE_FIELD_WARNING + "\n" + String.join("\n", errorMessages),
                LIST_STUDENT_MARKER);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStudent)),
            LIST_STUDENT_MARKER);
    }

    private String getDuplicatedTags(Set<Tag> currentTags, Set<Tag> newTags) {
        StringBuilder duplicatedTags = new StringBuilder();
        Set<Tag> intersection = new HashSet<>(currentTags);
        intersection.retainAll(newTags);
        if (!intersection.isEmpty()) {
            for (Tag tag : intersection) {
                duplicatedTags.append(tag.toString()).append(", ");
            }
            duplicatedTags.setLength(duplicatedTags.length() - 2);
        }
        return duplicatedTags.toString();
    }


    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Student createEditedPerson(Student studentToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(studentToEdit.getName());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(studentToEdit.getEmail());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(studentToEdit.getTags());
        StudentNumber studentNumber = studentToEdit.getStudentNumber();
        return new Student(updatedName, updatedEmail, updatedTags, studentNumber);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStudentCommand)) {
            return false;
        }

        EditStudentCommand otherEditStudentCommand = (EditStudentCommand) other;
        return index.equals(otherEditStudentCommand.index)
            && editPersonDescriptor.equals(otherEditStudentCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("editPersonDescriptor", editPersonDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Email email;
        private Set<Tag> tags;
        private StudentNumber studentNumber;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
            setStudentNumber(toCopy.studentNumber);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setStudentNumber(StudentNumber studentNumber) {
            this.studentNumber = studentNumber;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public Optional<StudentNumber> getStudentNumber() {
            return Optional.ofNullable(studentNumber);
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
                && Objects.equals(email, otherEditPersonDescriptor.email)
                && Objects.equals(tags, otherEditPersonDescriptor.tags)
                && Objects.equals(studentNumber, otherEditPersonDescriptor.studentNumber);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("email", email)
                .add("tags", tags)
                .add("student number", studentNumber)
                .toString();
        }
    }
}
