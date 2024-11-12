package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.PublicAddressesComposition;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class AbstractEditCommand extends Command {

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AbstractEditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
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
        PublicAddressesComposition updatedPublicAddresses =
            editPersonDescriptor.getPublicAddresses().orElse(personToEdit.getPublicAddressesComposition());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPublicAddresses, updatedTags);
    }

    private Person getPersonToEdit(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    private CommandResult saveEditedPerson(
        Model model,
        Person personToEdit,
        Person editedPerson,
        String successMessage
    ) throws CommandException {
        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(successMessage, Messages.format(editedPerson)));
    }

    /**
     * Executes the edit command with the given model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult with the success message.
     * @throws CommandException If the index is invalid or the person to edit does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToEdit = getPersonToEdit(model);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        return saveEditedPerson(model, personToEdit, editedPerson, MESSAGE_EDIT_PERSON_SUCCESS);
    }

    /**
     * Executes the edit command with the given function to create the edited person.
     *
     * @param model            {@code Model} which the command should operate on.
     * @param makeEditedPerson Function to create the edited person.
     * @return CommandResult with the success message.
     * @throws CommandException If the index is invalid or the person to edit does not exist.
     */
    CommandResult execute(
        Model model,
        BiFunction<? super Person, ? super EditPersonDescriptor, ? extends Person> makeEditedPerson,
        String successMessage
    ) throws CommandException {
        Person personToEdit = getPersonToEdit(model);
        Person editedPerson = makeEditedPerson.apply(personToEdit, editPersonDescriptor);
        return saveEditedPerson(model, personToEdit, editedPerson, successMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AbstractEditCommand otherEditCommand)) {
            return false;
        }

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
        private PublicAddressesComposition publicAddresses;
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
            setPublicAddresses(toCopy.publicAddresses);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, publicAddresses);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        /**
         * Returns an unmodifiable network-public addresses map, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code publicAddresses} is null.
         */
        public Optional<PublicAddressesComposition> getPublicAddresses() {
            return (publicAddresses != null)
                       ? Optional.of(publicAddresses) : Optional.empty();
        }

        /**
         * Sets {@code publicAddresses} to this object's {@code publicAddresses}.
         * A defensive copy of {@code publicAddresses} is used internally.
         */
        public void setPublicAddresses(PublicAddressesComposition publicAddressesComposition) {
            if (publicAddressesComposition != null) {
                this.publicAddresses = publicAddressesComposition.copy();
            } else {
                this.publicAddresses = null;
            }
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor otherEditPersonDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherEditPersonDescriptor.name)
                       && Objects.equals(phone, otherEditPersonDescriptor.phone)
                       && Objects.equals(email, otherEditPersonDescriptor.email)
                       && Objects.equals(address, otherEditPersonDescriptor.address)
                       && Objects.equals(publicAddresses, otherEditPersonDescriptor.publicAddresses)
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
