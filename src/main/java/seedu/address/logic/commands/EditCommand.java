package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIREDROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
import seedu.address.model.person.Address;
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Email;
import seedu.address.model.person.Experience;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skills;
import seedu.address.model.person.Status;
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
        + "[" + PREFIX_DESIREDROLE + "DESIREDROLE] "
        + "[" + PREFIX_SKILLS + "SKILLS] "
        + "[" + PREFIX_EXPERIENCE + "EXPERIENCE] "
        + "[" + PREFIX_STATUS + "STATUS] "
        + "[" + PREFIX_NOTE + "NOTE] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com "
        + PREFIX_DESIREDROLE + "Project Manager "
        + PREFIX_SKILLS + "Java, Python "
        + PREFIX_EXPERIENCE + "Software Engineer at ABC Corp 2015-2020 "
        + PREFIX_STATUS + "Interviewed "
        + PREFIX_NOTE + "Confident "
        + PREFIX_TAG + "friends";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        assert index.getOneBased() > 0 : "Index must be a positive integer";

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Check if the index is out of bounds
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

        assert editedPerson != null : "Edited person should not be null";

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null : "Person to edit cannot be null.";
        assert editPersonDescriptor != null : "EditPersonDescriptor cannot be null.";

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        DesiredRole updatedDesiredRole = editPersonDescriptor.getDesiredRole().orElse(personToEdit.getDesiredRole());
        Skills updatedSkills = editPersonDescriptor.getSkills().orElse(personToEdit.getSkills());
        Experience updatedExperience = editPersonDescriptor.getExperience().orElse(personToEdit.getExperience());
        Status updatedStatus = editPersonDescriptor.getStatus().orElse(personToEdit.getStatus());
        Note updatedNote = editPersonDescriptor.getNote().orElse(personToEdit.getNote());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedDesiredRole, updatedSkills,
            updatedExperience, updatedStatus, updatedNote, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EditCommand // instanceof handles nulls
            && index.equals(((EditCommand) other).index)
            && editPersonDescriptor.equals(((EditCommand) other).editPersonDescriptor));
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
        private DesiredRole desiredRole;
        private Skills skills;
        private Experience experience;
        private Status status;
        private Note note;
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setDesiredRole(toCopy.desiredRole);
            setSkills(toCopy.skills);
            setExperience(toCopy.experience);
            setStatus(toCopy.status);
            setNote(toCopy.note);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, desiredRole,
                skills, experience, status, note, tags);
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

        public void setDesiredRole(DesiredRole desiredRole) {
            this.desiredRole = desiredRole;
        }

        public Optional<DesiredRole> getDesiredRole() {
            return Optional.ofNullable(desiredRole);
        }

        public void setSkills(Skills skills) {
            this.skills = skills;
        }

        public Optional<Skills> getSkills() {
            return Optional.ofNullable(skills);
        }

        public void setExperience(Experience experience) {
            this.experience = experience;
        }

        public Optional<Experience> getExperience() {
            return Optional.ofNullable(experience);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
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

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return Objects.equals(name, e.name)
                && Objects.equals(phone, e.phone)
                && Objects.equals(email, e.email)
                && Objects.equals(address, e.address)
                && Objects.equals(desiredRole, e.desiredRole)
                && Objects.equals(skills, e.skills)
                && Objects.equals(experience, e.experience)
                && Objects.equals(status, e.status)
                && Objects.equals(note, e.note)
                && Objects.equals(tags, e.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("desiredRole", desiredRole)
                .add("skills", skills)
                .add("experience", experience)
                .add("status", status)
                .add("note", note)
                .add("tags", tags)
                .toString();
        }
    }
}
