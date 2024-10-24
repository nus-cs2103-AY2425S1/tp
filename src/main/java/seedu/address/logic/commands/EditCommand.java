package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "- Study group tags: Input values will be appended to the existing tag set.\n"
            + "   (to delete specific tags, use the special -t/ prefix followed by the name of "
            + "the tag you want to delete.)\n"
            + "- Other fields: Input values will overwrite existing values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_DETAIL + "DETAIL]"
            + "[" + PREFIX_STUDY_GROUP_TAG + "STUDY_GROUP_TAG]...\n"
            + "[" + PREFIX_REMOVE_TAG + "TAG_TO_REMOVE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited successfully! Edited participant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Provide at least one field to edit!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
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
        assert editPersonDescriptor != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        Age updatedAge = editPersonDescriptor.getAge().orElse(personToEdit.getAge());
        Detail updatedDetail = editPersonDescriptor.getDetail().orElse(personToEdit.getDetail());

        Set<StudyGroupTag> updatedStudyGroups = new HashSet<>();
        updatedStudyGroups.addAll(personToEdit.getStudyGroupTags());
        updatedStudyGroups.addAll(editPersonDescriptor.getStudyGroupTags().orElse(new HashSet<>()));
        editPersonDescriptor.getTagsToRemove().ifPresent(updatedStudyGroups::removeAll);

        return new Person(updatedName, updatedEmail, updatedGender, updatedAge, updatedDetail, updatedStudyGroups);
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
     * Stores the details to edit the person with. Each non-empty field value will
     * replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Email email;
        private Gender gender;
        private Age age;
        private Detail detail;
        private Set<StudyGroupTag> studyGroupTags;
        private Set<StudyGroupTag> tagsToRemove;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code studyGroupTags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            assert toCopy != null;

            setName(toCopy.name);
            setEmail(toCopy.email);
            setGender(toCopy.gender);
            setAge(toCopy.age);
            setDetail(toCopy.detail);
            setStudyGroupTags(toCopy.studyGroupTags);
            setTagsToRemove(toCopy.tagsToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, age, gender, detail, studyGroupTags, tagsToRemove);
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

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setDetail(Detail detail) {
            this.detail = (detail != null) ? detail : null;
        }

        public Optional<Detail> getDetail() {
            return Optional.ofNullable(detail);
        }

        /**
         * Sets this object's {@code studyGroupTags} to {@code studyGroupTags} . A defensive
         * copy of {@code studyGroupTags} is used internally.
         */
        public void setStudyGroupTags(Set<StudyGroupTag> studyGroupTags) {
            this.studyGroupTags = (studyGroupTags != null) ? new HashSet<>(studyGroupTags) : null;
        }

        /**
         * Returns an unmodifiable tag set of existing and added tags, which throws
         * {@code UnsupportedOperationException} if modification is attempted. Returns
         * {@code Optional#empty()} if {@code studyGroupTags} is null.
         */
        public Optional<Set<StudyGroupTag>> getStudyGroupTags() {
            return (studyGroupTags != null)
                    ? Optional.of(Collections.unmodifiableSet(studyGroupTags))
                    : Optional.empty();
        }

        /**
         * Sets this object's {@code tagsToRemove} to {@code tagsToRemove} . A defensive
         * copy of {@code studyGroupTags} is used internally.
         */
        public void setTagsToRemove(Set<StudyGroupTag> studyGroupTags) {
            this.tagsToRemove = (studyGroupTags != null) ? new HashSet<>(studyGroupTags) : null;
        }
        /**
         * Returns an unmodifiable tag set to remove, which throws
         * {@code UnsupportedOperationException} if modification is attempted. Returns
         * {@code Optional#empty()} if {@code tagsToRemove} is null.
         */
        public Optional<Set<StudyGroupTag>> getTagsToRemove() {
            return (tagsToRemove != null)
                    ? Optional.of(Collections.unmodifiableSet(tagsToRemove))
                    : Optional.empty();
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
                    && Objects.equals(gender, otherEditPersonDescriptor.gender)
                    && Objects.equals(age, otherEditPersonDescriptor.age)
                    && Objects.equals(detail, otherEditPersonDescriptor.detail)
                    && Objects.equals(studyGroupTags, otherEditPersonDescriptor.studyGroupTags)
                    && Objects.equals(tagsToRemove, otherEditPersonDescriptor.tagsToRemove);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("email", email)
                    .add("gender", gender)
                    .add("age", age)
                    .add("detail", detail)
                    .add("study groups", studyGroupTags)
                    .add("to remove", tagsToRemove)
                    .toString();
        }
    }
}
