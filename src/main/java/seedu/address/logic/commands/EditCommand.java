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
            + "Parameters: INDEX (must be a positive integer)\n"
            + " [" + PREFIX_NAME + "NAME]\n"
            + " [" + PREFIX_EMAIL + "EMAIL]\n"
            + " [" + PREFIX_GENDER + "GENDER]\n"
            + " [" + PREFIX_AGE + "AGE]\n"
            + " [" + PREFIX_DETAIL + "DETAIL]\n"
            + " [" + PREFIX_STUDY_GROUP_TAG + "STUDY-GROUP-TAG]...\n"
            + " [" + PREFIX_REMOVE_TAG + "TAG_TO_REMOVE]...\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + " " + PREFIX_EMAIL + "johndoe@example.com\n"
            + " " + PREFIX_REMOVE_TAG + "1A\n"
            + " " + PREFIX_REMOVE_TAG + "Control";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited successfully!\n"
            + "%s%s"
            + "Edited participant: %s";

    public static final String SUBMESSAGE_DUPLICATE_TAG = "You tried adding an already existing study group tag.\n";
    public static final String SUBMESSAGE_NONEXISTENT_TAG = "You tried removing a nonexistent study group tag.\n";

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

    /**
     * Executes the edit command, modifying the details of a person in the model's filtered person list.
     * Ensures that no duplicate persons exist and validates the tags being added or removed.
     *
     * @param model the {@code Model} that the command should operate on.
     * @return a {@code CommandResult} containing a success message with additional
     *     information about any duplicate or non-existent tags.
     * @throws CommandException if the index provided is out of bounds, the edited person would be a duplicate,
     *                          or if the tags specified have certain conflicts.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, lastShownList.size()));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        boolean isDuplicateTag = editPersonDescriptor.getStudyGroupTags().orElse(Collections.emptySet()).stream()
                .anyMatch(studyGroup -> personToEdit.getStudyGroupTags().contains(studyGroup));
        boolean isNonexistentTag = editPersonDescriptor.getTagsToRemove().orElse(Collections.emptySet()).stream()
                .anyMatch(studyGroup -> !personToEdit.getStudyGroupTags().contains(studyGroup));

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                isDuplicateTag ? SUBMESSAGE_DUPLICATE_TAG : "",
                isNonexistentTag ? SUBMESSAGE_NONEXISTENT_TAG : "",
                Messages.format(editedPerson).toString()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} edited with
     * {@code editPersonDescriptor}.
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
     * Stores the details to edit the person with. Each non-empty field value
     * will replace the corresponding field value
     * of the person.
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

        /**
         * Sets the name of the person.
         *
         * @param name the {@code Name} to set.
         */

        public void setName(Name name) {
            this.name = name;
        }

        /**
         * Retrieves the name of the person, if present.
         *
         * @return an {@code Optional} containing the name, or an empty {@code Optional} if the name is not set.
         */

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets the email of the person.
         *
         * @param email the {@code Email} to set.
         */

        public void setEmail(Email email) {
            this.email = email;
        }

        /**
         * Retrieves the email of the person, if present.
         *
         * @return an {@code Optional} containing the email, or an empty {@code Optional} if the email is not set.
         */

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets the gender of the person.
         *
         * @param gender the {@code Gender} to set.
         */
        public void setGender(Gender gender) {
            this.gender = gender;
        }

        /**
         * Retrieves the gender of the person, if present.
         *
         * @return an {@code Optional} containing the gender, or an empty {@code Optional} if the gender is not set.
         */
        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        /**
         * Sets the age of the person.
         *
         * @param age the {@code Age} to set.
         */
        public void setAge(Age age) {
            this.age = age;
        }

        /**
         * Retrieves the age of the person, if present.
         *
         * @return an {@code Optional} containing the age, or an empty {@code Optional} if the age is not set.
         */
        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        /**
         * Sets the detail of the person.
         *
         * @param detail the {@code Detail} to set, or {@code null} to clear the detail.
         */
        public void setDetail(Detail detail) {
            this.detail = (detail != null) ? detail : null;
        }

        /**
         * Retrieves the detail of the person, if present.
         *
         * @return an {@code Optional} containing the detail, or an empty {@code Optional} if the detail is not set.
         */
        public Optional<Detail> getDetail() {
            return Optional.ofNullable(detail);
        }

        /**
         * Sets this object's {@code studyGroupTags} to {@code studyGroupTags} . A defensive copy of
         * {@code studyGroupTags} is used internally.
         */
        public void setStudyGroupTags(Set<StudyGroupTag> studyGroupTags) {
            this.studyGroupTags = (studyGroupTags != null) ? new HashSet<>(studyGroupTags) : null;
        }

        /**
         * Returns an unmodifiable tag set of existing and added tags, which throws
         * {@code UnsupportedOperationException} if modification is attempted. Returns {@code Optional#empty()} if
         * {@code studyGroupTags} is null.
         */
        public Optional<Set<StudyGroupTag>> getStudyGroupTags() {
            return (studyGroupTags != null)
                    ? Optional.of(Collections.unmodifiableSet(studyGroupTags))
                    : Optional.empty();
        }

        /**
         * Sets this object's {@code tagsToRemove} to {@code tagsToRemove} . A defensive copy of {@code studyGroupTags}
         * is used internally.
         */
        public void setTagsToRemove(Set<StudyGroupTag> studyGroupTags) {
            this.tagsToRemove = (studyGroupTags != null) ? new HashSet<>(studyGroupTags) : null;
        }

        /**
         * Returns an unmodifiable tag set to remove, which throws {@code UnsupportedOperationException} if modification
         * is attempted. Returns {@code Optional#empty()} if {@code tagsToRemove} is null.
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
