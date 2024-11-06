package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderTracker;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archive the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_ARCHIVED = "At least one field to archive must be provided.";

    private final Index index;
    private final ArchivePersonDescriptor archivePersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param archivePersonDescriptor details to edit the person with
     */
    public ArchiveCommand(Index index, ArchivePersonDescriptor archivePersonDescriptor) {
        requireNonNull(index);
        requireNonNull(archivePersonDescriptor);

        this.index = index;
        this.archivePersonDescriptor = new ArchivePersonDescriptor(archivePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToArchive = lastShownList.get(index.getZeroBased());
        Person archivedPerson = createArchivedPerson(personToArchive, archivePersonDescriptor);

        if (!personToArchive.isSamePerson(archivedPerson) && model.hasPerson(archivedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.setPerson(personToArchive, archivedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(archivedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createArchivedPerson(Person personToEdit, ArchivePersonDescriptor archivePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = archivePersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = archivePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = archivePersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = archivePersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        PostalCode updatedPostalCode = archivePersonDescriptor.getPostalCode().orElse(personToEdit.getPostalCode());
        Set<Tag> updatedTags = archivePersonDescriptor.getTags().orElse(personToEdit.getTags());
        Boolean updatedIsArchive = archivePersonDescriptor.getIsArchived().orElse(personToEdit.isArchived());
        OrderTracker updatedTracker = personToEdit.getOrderTracker();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPostalCode, updatedTags,
                updatedTracker, updatedIsArchive);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return index.equals(otherArchiveCommand.index)
                && archivePersonDescriptor.equals(otherArchiveCommand.archivePersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("archivePersonDescriptor", archivePersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to archive the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class ArchivePersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private PostalCode postalCode;
        private Set<Tag> tags;
        private Boolean isArchived;
        private OrderTracker tracker;

        public ArchivePersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public ArchivePersonDescriptor(ArchivePersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setPostalCode(toCopy.postalCode);
            setTags(toCopy.tags);
            setIsArchived(toCopy.isArchived);
            setTracker(toCopy.tracker);
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

        public void setPostalCode(PostalCode postalCode) {
            this.postalCode = postalCode;
        }

        public Optional<PostalCode> getPostalCode() {
            return Optional.ofNullable(postalCode);
        }

        public void setTracker(OrderTracker tracker) {
            if (tracker != null) {
                this.tracker = new OrderTracker();
                this.tracker.add(tracker.get());
            } else {
                this.tracker = null;
            }
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

        public void setIsArchived(Boolean isArchived) {
            this.isArchived = isArchived;
        }

        public Optional<Boolean> getIsArchived() {
            return Optional.ofNullable(isArchived);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ArchivePersonDescriptor)) {
                return false;
            }

            ArchiveCommand.ArchivePersonDescriptor otherArchivePersonDescriptor =
                    (ArchiveCommand.ArchivePersonDescriptor) other;
            return Objects.equals(name, otherArchivePersonDescriptor.name)
                    && Objects.equals(phone, otherArchivePersonDescriptor.phone)
                    && Objects.equals(email, otherArchivePersonDescriptor.email)
                    && Objects.equals(address, otherArchivePersonDescriptor.address)
                    && Objects.equals(postalCode, otherArchivePersonDescriptor.postalCode)
                    && Objects.equals(tags, otherArchivePersonDescriptor.tags)
                    && Objects.equals(isArchived, otherArchivePersonDescriptor.isArchived);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("postalCode", postalCode)
                    .add("tags", tags)
                    .add("isArchived", isArchived)
                    .toString();
        }
    }
}

