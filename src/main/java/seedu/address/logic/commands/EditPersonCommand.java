package seedu.address.logic.commands;

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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditPersonCommand extends EditCommand {
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    /**
     * Creates an EditPersonCommand to add the specified {@code Person}
     */
    public EditPersonCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
    }

    @Override
    protected boolean hasEntity(Model model, Object entity) throws CommandException {
        return model.hasPerson((Person) entity);
    }

    @Override
    protected boolean isSameEntity(Model model, Object editedEntity, Object entityToEdit)
            throws CommandException {
        Person entityToEditCasted = (Person) entityToEdit;
        return !(entityToEditCasted.isSamePerson((Person) editedEntity));
    }

    @Override
    protected List<Person> getFilteredList(Model model) {
        return model.getFilteredPersonList();
    }

    @Override
    protected void editEntity(Model model, Object editedPerson, Object personToEdit) throws CommandException {
        model.setPerson((Person) personToEdit, (Person) editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    @Override
    protected Object createEditedEntity(Model model, Object personToEdit,
                                                 EditEntityDescriptor editPersonDescriptor) throws CommandException {
        assert personToEdit != null;
        EditPersonDescriptor editPersonDescriptorCasted = (EditPersonDescriptor) editPersonDescriptor;
        Person personToEditCasted = (Person) personToEdit;

        int personId = personToEditCasted.getPersonId();
        Name updatedName = editPersonDescriptorCasted.getName().orElse(personToEditCasted.getName());
        Phone updatedPhone = editPersonDescriptorCasted.getPhone().orElse(personToEditCasted.getPhone());
        Email updatedEmail = editPersonDescriptorCasted.getEmail().orElse(personToEditCasted.getEmail());
        Address updatedAddress = editPersonDescriptorCasted.getAddress().orElse(personToEditCasted.getAddress());
        Set<Tag> updatedTags = editPersonDescriptorCasted.getTags().orElse(personToEditCasted.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, personId);
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_EDIT_PERSON_SUCCESS;
    }

    @Override
    protected String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_PERSON;
    }

    @Override
    protected String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    }

    @Override
    protected String formatEntity(Object entity) {
        assert entity instanceof Person;
        return Messages.formatPerson((Person) entity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editPersonDescriptor", editEntityDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor extends EditCommand.EditEntityDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            super(toCopy);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
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
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}