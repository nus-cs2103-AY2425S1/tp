package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

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
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.model.student.task.TaskList;
import seedu.address.ui.Ui.UiState;

/**
 * Updates the details of an existing student in the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the student identified "
            + "by their full name in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NAME (full name) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMERGENCY_CONTACT + "EMERGENCY_PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_LEVEL + "LEVEL] "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... "
            + "[" + PREFIX_LESSON_TIME + "LESSON_TIMING]...\n"
            + "Example: " + COMMAND_WORD + " Cristiano Ronaldo "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_UPDATE_STUDENT_SUCCESS = "Updated Student: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to be updated must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Name name;
    private final UpdateStudentDescriptor updateStudentDescriptor;

    /**
     * @param name of the student in the filtered student list to update
     * @param updateStudentDescriptor details to update the student with
     */
    public UpdateCommand(Name name, UpdateStudentDescriptor updateStudentDescriptor) {
        requireNonNull(name);
        requireNonNull(updateStudentDescriptor);

        this.name = name;
        this.updateStudentDescriptor = new UpdateStudentDescriptor(updateStudentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();
        if (name.fullName.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_UPDATE);
        }

        Student studentToUpdate = lastShownList.stream()
                .filter(student -> student.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_STUDENT_UPDATE));
        Student updatedStudent = createUpdatedStudent(studentToUpdate, updateStudentDescriptor);

        if (!studentToUpdate.isSameStudent(updatedStudent) && model.hasStudent(updatedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.setStudent(studentToUpdate, updatedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent)),
                UiState.DETAILS);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToUpdate}
     * updated with {@code updateStudentDescriptor}.
     */
    private static Student createUpdatedStudent(Student studentToUpdate,
                                                UpdateStudentDescriptor updateStudentDescriptor) {
        assert studentToUpdate != null;

        Name updatedName = updateStudentDescriptor.getName().orElse(studentToUpdate.getName());
        Phone updatedPhone = updateStudentDescriptor.getPhone().orElse(studentToUpdate.getPhone());
        EmergencyContact updatedEmergencyContact = updateStudentDescriptor.getEmergencyContact()
                .orElse(studentToUpdate.getEmergencyContact());
        Address updatedAddress = updateStudentDescriptor.getAddress().orElse(studentToUpdate.getAddress());
        Note updatedNote = updateStudentDescriptor.getNote().orElse(studentToUpdate.getNote());
        Level updatedLevel = updateStudentDescriptor.getLevel().orElse(studentToUpdate.getLevel());
        if (updatedLevel != null && updateStudentDescriptor.getSubjects().isPresent()) {
            checkArgument(
                    Subject.isValidSubjectsByLevel(updatedLevel,
                            updateStudentDescriptor
                                    .getSubjects()
                                    .get()),
                    Subject.MESSAGE_LEVEL_NEEDED);
        }
        Set<Subject> updatedSubjects = updateStudentDescriptor.getSubjects().orElse(studentToUpdate.getSubjects());
        TaskList updatedTaskList = updateStudentDescriptor.getTaskList().orElse(studentToUpdate.getTaskList());
        Set<LessonTime> updatedLessonTimes = updateStudentDescriptor.getLessonTimes()
                .orElse(studentToUpdate.getLessonTimes());
        return new Student(updatedName, updatedPhone, updatedEmergencyContact,
                updatedAddress, updatedNote, updatedSubjects, updatedLevel, updatedTaskList, updatedLessonTimes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;
        return name.equals(otherUpdateCommand.name)
                && updateStudentDescriptor.equals(otherUpdateCommand.updateStudentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("updateStudentDescriptor", updateStudentDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class UpdateStudentDescriptor {
        private Name name;
        private Phone phone;
        private EmergencyContact emergencyContact;
        private Address address;
        private Note note;
        private Set<Subject> subjects;
        private Level level;
        private TaskList taskList;
        private Set<LessonTime> lessonTimes;

        public UpdateStudentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code subjects} is used internally.
         */
        public UpdateStudentDescriptor(UpdateStudentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmergencyContact(toCopy.emergencyContact);
            setAddress(toCopy.address);
            setNote(toCopy.note);
            setSubjects(toCopy.subjects);
            setLevel(toCopy.level);
            setTaskList(toCopy.taskList);
            setLessonTimes(toCopy.lessonTimes);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(name, phone, emergencyContact, address, note, subjects,
                    level, taskList, lessonTimes);
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

        public void setEmergencyContact(EmergencyContact emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Optional<EmergencyContact> getEmergencyContact() {
            return Optional.ofNullable(emergencyContact);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        public Optional<Level> getLevel() {
            return Optional.ofNullable(level);
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
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
        }

        public void setTaskList(TaskList taskList) {
            this.taskList = taskList;
        }

        public Optional<TaskList> getTaskList() {
            return Optional.ofNullable(taskList);
        }

        /**
         * Sets {@code lessonTimes} to this object's {@code lessonTimes}.
         * A defensive copy of {@code lessonTimes} is used internally.
         */
        public void setLessonTimes(Set<LessonTime> lessonTimes) {
            this.lessonTimes = (lessonTimes != null) ? new HashSet<>(lessonTimes) : null;
        }

        /**
         * Returns an unmodifiable lesson time set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code lesson time} is null.
         */
        public Optional<Set<LessonTime>> getLessonTimes() {
            return (lessonTimes != null) ? Optional.of(Collections.unmodifiableSet(lessonTimes)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateStudentDescriptor)) {
                return false;
            }

            UpdateStudentDescriptor otherUpdateStudentDescriptor = (UpdateStudentDescriptor) other;
            return Objects.equals(name, otherUpdateStudentDescriptor.name)
                    && Objects.equals(phone, otherUpdateStudentDescriptor.phone)
                    && Objects.equals(emergencyContact, otherUpdateStudentDescriptor.emergencyContact)
                    && Objects.equals(address, otherUpdateStudentDescriptor.address)
                    && Objects.equals(note, otherUpdateStudentDescriptor.note)
                    && Objects.equals(subjects, otherUpdateStudentDescriptor.subjects)
                    && Objects.equals(level, otherUpdateStudentDescriptor.level)
                    && Objects.equals(taskList, otherUpdateStudentDescriptor.taskList)
                    && Objects.equals(lessonTimes, otherUpdateStudentDescriptor.lessonTimes);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("emergencyContact", emergencyContact)
                    .add("address", address)
                    .add("note", note)
                    .add("level", level)
                    .add("subjects", subjects)
                    .add("task list", taskList)
                    .add("lesson times", lessonTimes)
                    .toString();
        }
    }
}
