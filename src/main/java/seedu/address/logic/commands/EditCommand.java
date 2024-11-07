package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
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

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Edits the details of an existing person in VolunTier.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + " NAME] "
            + "[" + PREFIX_PHONE + " PHONE_NUMBER] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_ADDRESS + " ADDRESS] "
            + "[" + PREFIX_HOURS + " HOURS] "
            + "[" + PREFIX_SUBJECT + " SUBJECT]… \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + " 91234567 "
            + PREFIX_EMAIL + " johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: \n %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in VolunTier.";
    public static final String MESSAGE_PERSON_HAS_LESSON = "This person has an existing lesson for the subject you’re " +
            "about to edit in the address book. Please delete the lesson before proceeding with the subject edit.";

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
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Verify if person to be edited has a lesson already in the address book
        Set<Subject> subjects = editPersonDescriptor.getSubjectsOp().orElse(null);
        ObservableList<Lesson> lessonList = model.getAddressBook().getLessonList();
        Set<Subject> minSet = lessonList.stream()
                .filter(lesson -> lesson.getTutor().equals(lastShownList.get(index.getZeroBased()))
                        || lesson.getTutee().equals(lastShownList.get(index.getZeroBased())))
                .map(Lesson::getSubject)
                .collect(HashSet::new, Set::add, Set::addAll);
        if (subjects != null) {
            if (!subjects.containsAll(minSet)) {
                throw new CommandException(MESSAGE_PERSON_HAS_LESSON);
            }
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        for (Lesson l : model.getAssociatedLessons(personToEdit)) {
            model.deleteLesson(l);
            if (editedPerson.isTutor()) {
                model.addLesson(new Lesson((Tutor) editedPerson, l.getTutee(), l.getSubject()));
            } else {
                model.addLesson(new Lesson(l.getTutor(), (Tutee) editedPerson, l.getSubject()));
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        int updatedId = editPersonDescriptor.getId().orElse(personToEdit.getId());
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Hours updatedHours = editPersonDescriptor.getHours().orElse(personToEdit.getHours());
        Set<Subject> updatedSubjects = editPersonDescriptor.getSubjectsOp().orElse(personToEdit.getSubjects());

        if (personToEdit instanceof Tutor) {
            return new Tutor(updatedId, updatedName, updatedPhone, updatedEmail, updatedAddress, updatedHours,
                    updatedSubjects);
        } else {
            return new Tutee(updatedId, updatedName, updatedPhone, updatedEmail, updatedAddress, updatedHours,
                    updatedSubjects);
        }

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
        private Integer id;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Hours hours;
        private Set<Subject> subjects;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setId(toCopy.id);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setHours(toCopy.hours);
            setSubjects(toCopy.subjects);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, hours, subjects);
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
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

        public void setHours(Hours hours) {
            this.hours = hours;
        }

        public Optional<Hours> getHours() {
            return Optional.ofNullable(hours);
        }

        //subjects that are the same will only be stored once with HashSet
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        public Optional<Set<Subject>> getSubjectsOp() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
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
                    && Objects.equals(hours, otherEditPersonDescriptor.hours)
                    && Objects.equals(subjects, otherEditPersonDescriptor.subjects);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("hours", hours)
                    .add("subjects", subjects)
                    .toString();
        }
    }
}
