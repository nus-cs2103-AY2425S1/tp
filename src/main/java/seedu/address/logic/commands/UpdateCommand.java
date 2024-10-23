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
import seedu.address.model.person.Address;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.task.TaskList;
import seedu.address.ui.Ui.UiState;

/**
 * Updates the details of an existing person in the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
            + "by their full name in the displayed person list. "
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

    public static final String MESSAGE_UPDATE_PERSON_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to be updated must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Name name;
    private final UpdatePersonDescriptor updatePersonDescriptor;

    /**
     * @param name of the person in the filtered person list to update
     * @param updatePersonDescriptor details to update the person with
     */
    public UpdateCommand(Name name, UpdatePersonDescriptor updatePersonDescriptor) {
        requireNonNull(name);
        requireNonNull(updatePersonDescriptor);

        this.name = name;
        this.updatePersonDescriptor = new UpdatePersonDescriptor(updatePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (name.fullName.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_UPDATE);
        }

        Person personToUpdate = lastShownList.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_PERSON_UPDATE));
        Person updatedPerson = createUpdatedPerson(personToUpdate, updatePersonDescriptor);

        if (!personToUpdate.isSamePerson(updatedPerson) && model.hasPerson(updatedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(updatedPerson)),
                UiState.DETAILS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdate}
     * updated with {@code updatePersonDescriptor}.
     */
    private static Person createUpdatedPerson(Person personToUpdate, UpdatePersonDescriptor updatePersonDescriptor) {
        assert personToUpdate != null;

        Name updatedName = updatePersonDescriptor.getName().orElse(personToUpdate.getName());
        Phone updatedPhone = updatePersonDescriptor.getPhone().orElse(personToUpdate.getPhone());
        EmergencyContact updatedEmergencyContact = updatePersonDescriptor.getEmergencyContact()
                .orElse(personToUpdate.getEmergencyContact());
        Address updatedAddress = updatePersonDescriptor.getAddress().orElse(personToUpdate.getAddress());
        Note updatedNote = updatePersonDescriptor.getNote().orElse(personToUpdate.getNote());
        Level updatedLevel = updatePersonDescriptor.getLevel().orElse(personToUpdate.getLevel());
        if (updatedLevel != null && updatePersonDescriptor.getSubjects().isPresent()) {
            checkArgument(
                    Subject.isValidSubjectsByLevel(updatedLevel,
                            updatePersonDescriptor
                                    .getSubjects()
                                    .get()),
                    Subject.MESSAGE_LEVEL_NEEDED);
        }
        Set<Subject> updatedSubjects = updatePersonDescriptor.getSubjects().orElse(personToUpdate.getSubjects());
        TaskList updatedTaskList = updatePersonDescriptor.getTaskList().orElse(personToUpdate.getTaskList());
        Set<LessonTime> updatedLessonTimes = updatePersonDescriptor.getLessonTimes()
                .orElse(personToUpdate.getLessonTimes());
        return new Person(updatedName, updatedPhone, updatedEmergencyContact,
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
                && updatePersonDescriptor.equals(otherUpdateCommand.updatePersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("updatePersonDescriptor", updatePersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UpdatePersonDescriptor {
        private Name name;
        private Phone phone;
        private EmergencyContact emergencyContact;
        private Address address;
        private Note note;
        private Set<Subject> subjects;
        private Level level;
        private TaskList taskList;
        private Set<LessonTime> lessonTimes;

        public UpdatePersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code subjects} is used internally.
         */
        public UpdatePersonDescriptor(UpdatePersonDescriptor toCopy) {
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
            if (!(other instanceof UpdatePersonDescriptor)) {
                return false;
            }

            UpdatePersonDescriptor otherUpdatePersonDescriptor = (UpdatePersonDescriptor) other;
            return Objects.equals(name, otherUpdatePersonDescriptor.name)
                    && Objects.equals(phone, otherUpdatePersonDescriptor.phone)
                    && Objects.equals(emergencyContact, otherUpdatePersonDescriptor.emergencyContact)
                    && Objects.equals(address, otherUpdatePersonDescriptor.address)
                    && Objects.equals(note, otherUpdatePersonDescriptor.note)
                    && Objects.equals(subjects, otherUpdatePersonDescriptor.subjects)
                    && Objects.equals(level, otherUpdatePersonDescriptor.level)
                    && Objects.equals(taskList, otherUpdatePersonDescriptor.taskList)
                    && Objects.equals(lessonTimes, otherUpdatePersonDescriptor.lessonTimes);
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
