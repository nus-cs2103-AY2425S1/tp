package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.IllegalPersonTypeException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_LESSON_TIME + "LESSON TIME] "
            + "[" + PREFIX_EDUCATION + "EDUCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_ADD_EDUCATION_TO_NON_STUDENT = "Unable to add education to non-student!";
    public static final String MESSAGE_ADD_LESSON_TIME_TO_NON_STUDENT = "Unable to add lesson time to non-student!";

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

        if (!(personToEdit instanceof Student) && editPersonDescriptor.containsEducationField()) {
            throw new CommandException(MESSAGE_ADD_EDUCATION_TO_NON_STUDENT);
        }

        if (!(personToEdit instanceof Student) && editPersonDescriptor.containsLessonTimeField()) {
            throw new CommandException(MESSAGE_ADD_LESSON_TIME_TO_NON_STUDENT);
        }

        Person editedPerson;

        if (personToEdit instanceof Student studentToEdit) {
            editedPerson = createEditedPerson(studentToEdit, editPersonDescriptor);
        } else if (personToEdit instanceof Parent parentToEdit) {
            editedPerson = createEditedPerson(parentToEdit, editPersonDescriptor);
        } else {
            throw new IllegalPersonTypeException(personToEdit);
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);

        editLinks(editedPerson, personToEdit, model);

        model.updateFilteredPersonList(PREDICATE_SHOW_UNARCHIVED_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    private static Parent createEditedPerson(Parent parentToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert parentToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(parentToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(parentToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(parentToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(parentToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(parentToEdit.getTags());
        Set<Name> updatedChildrensNames = parentToEdit.getChildrensNames(); // command does not allow editing child name
        boolean updatedIsPinned = parentToEdit.isPinned(); // edit command does not allow editing is pinned
        boolean updatedIsArchived = parentToEdit.isArchived(); // edit command does not allow editing is archived

        return new Parent(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedChildrensNames, updatedTags,
                updatedIsPinned, updatedIsArchived);
    }

    private static Student createEditedPerson(Student studentToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(studentToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(studentToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(studentToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(studentToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(studentToEdit.getTags());
        LessonTime updatedLessonTime = editPersonDescriptor.getLessonTime().orElse(studentToEdit.getLessonTime());
        Education updatedEducation = editPersonDescriptor.getEducation().orElse(studentToEdit.getEducation());
        Grade updatedGrade = studentToEdit.getGrade(); // edit command does not allow editing grade
        Name updatedParentName = studentToEdit.getParentName(); // edit command does not allow editing parent name
        boolean updatedIsPinned = studentToEdit.isPinned(); // edit command does not allow editing is pinned
        boolean updatedIsArchived = studentToEdit.isArchived(); // edit command does not allow editing is archived

        return new Student(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedLessonTime, updatedEducation,
                updatedGrade, updatedParentName, updatedTags, updatedIsPinned, updatedIsArchived);
    }

    private void editLinks(Person editedPerson, Person personToEdit, Model model) throws CommandException {
        if (editedPerson instanceof Student) {
            editLinksForStudent((Student) editedPerson, (Student) personToEdit, model);
            return;
        }

        if (editedPerson instanceof Parent) {
            editLinksForParent((Parent) editedPerson, model);
            return;
        }

        throw new IllegalPersonTypeException(editedPerson);
    }

    private void editLinksForStudent(Student student, Student studentToEdit, Model model) throws CommandException {
        Name parentName = student.getParentName();
        if (parentName == null) {
            return; // Student has no parent in AddressBook
        }

        try {
            Person link = model.personFromName(parentName);
            if (!(link instanceof Parent parent)) {
                throw new CommandException("Invalid linked parent found: " + Messages.format(link));
            }
            Parent editedParent = createEditedParent(parent, student, studentToEdit);
            model.setPerson(parent, editedParent);
        } catch (PersonNotFoundException e) {
            // Not supposed to reach here
        }
    }

    private void editLinksForParent(Parent parent, Model model) throws CommandException {
        Set<Name> childrenNames = parent.getChildrensNames();
        Set<Student> children = new HashSet<>();

        for (Name childName : childrenNames) {
            try {
                Person link = model.personFromName(childName);
                if (!(link instanceof Student child)) {
                    throw new CommandException("Invalid linked child found: " + Messages.format(link));
                }
                children.add(child);
            } catch (PersonNotFoundException e) {
                // Not supposed to reach here
            }
        }

        for (Student child : children) {
            Student editedChild = createEditedChild(child, parent);
            model.setPerson(child, editedChild);
        }
    }

    private Student createEditedChild(Student child, Parent parent) {
        Name name = child.getName();
        Phone phone = child.getPhone();
        Email email = child.getEmail();
        Address address = child.getAddress();
        LessonTime lessonTime = child.getLessonTime();
        Education education = child.getEducation();
        Grade grade = child.getGrade();
        Name parentName = parent.getName();
        Set<Tag> tags = child.getTags();
        boolean isPinned = child.isPinned();
        boolean isArchived = child.isArchived();

        return new Student(name, phone, email, address, lessonTime, education, grade, parentName, tags, isPinned,
                isArchived);
    }

    private Parent createEditedParent(Parent parent, Student student, Student studentToEdit) {
        Name parentName = parent.getName();
        Phone parentPhone = parent.getPhone();
        Email parentEmail = parent.getEmail();
        Address parentAddress = parent.getAddress();
        Set<Name> childrenNames = parent.getChildrensNames().stream().filter(name -> name != studentToEdit.getName())
                .collect(Collectors.toSet());
        childrenNames.add(student.getName());
        Set<Tag> parentTags = parent.getTags();
        boolean isPinned = parent.isPinned();
        boolean isArchived = parent.isArchived();

        return new Parent(parentName, parentPhone, parentEmail, parentAddress, childrenNames, parentTags, isPinned,
                isArchived);
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
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private LessonTime lessonTime;
        private Education education;

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
            setTags(toCopy.tags);
            setLessonTime(toCopy.lessonTime);
            setEducation(toCopy.education);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, lessonTime, education);
        }

        public boolean containsLessonTimeField() {
            return lessonTime != null;
        }
        public boolean containsEducationField() {
            return education != null;
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

        public void setLessonTime(LessonTime lessonTime) {
            this.lessonTime = lessonTime;
        }

        public Optional<LessonTime> getLessonTime() {
            return Optional.ofNullable(lessonTime);
        }

        public void setEducation(Education education) {
            this.education = education;
        }

        public Optional<Education> getEducation() {
            return Optional.ofNullable(education);
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
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(lessonTime, otherEditPersonDescriptor.lessonTime)
                    && Objects.equals(education, otherEditPersonDescriptor.education);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("lesson time", lessonTime)
                    .add("education", education)
                    .add("tags", tags)
                    .toString();
        }
    }
}
