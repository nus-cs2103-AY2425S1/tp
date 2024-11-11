package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.ddd.commons.core.index.Index;
import seedu.ddd.commons.util.CollectionUtil;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.Model;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.exceptions.DuplicateContactException;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;

/**
 * Edits the details of a contact in the address book.
 */
public class EditContactCommand extends EditCommand {

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";
    public static final String MESSAGE_EDIT_INVALID_PARAMETER = "%1$s is not an editable parameter for this contact.";

    private final Index index;
    private final EditContactDescriptor editContactDescriptor;

    /**
     * @param index of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Index index, EditContactDescriptor editContactDescriptor) {
        requireNonNull(editContactDescriptor);

        this.index = index;
        this.editContactDescriptor = editContactDescriptor.copy();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Contact contactToEdit = null;
        if (index != null) {
            ObservableList<Displayable> lastShownList = model.getDisplayedList();
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
            Displayable firstItem = lastShownList.get(index.getZeroBased());
            if (!(firstItem instanceof Contact)) {
                throw new CommandException(Messages.MESSAGE_NOT_DISPLAYING_CONTACTS);
            }
            assert firstItem instanceof Contact;
            contactToEdit = (Contact) firstItem;
        } else {
            Id targetContactId = editContactDescriptor.getId().get();
            assert targetContactId != null;

            for (Contact contact : model.getFilteredContactList()) {
                if (contact.getId().equals(targetContactId)) {
                    contactToEdit = contact;
                    break;
                }
            }
            if (contactToEdit == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);

            }
        }
        assert contactToEdit != null;

        if (contactToEdit instanceof Client && editContactDescriptor instanceof EditVendorDescriptor) {
            throw new CommandException(String.format(MESSAGE_EDIT_INVALID_PARAMETER, PREFIX_SERVICE));
        }
        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

        if (!contactToEdit.isSameContact(contactToEdit) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        try {
            model.setContact(contactToEdit, editedContact);

            for (Event event : editedContact.getEvents()) {
                event.removeContact(contactToEdit);
                if (editedContact instanceof Client) {
                    event.addClient((Client) editedContact);
                } else {
                    event.addVendor((Vendor) editedContact);
                }
            }
        } catch (DuplicateContactException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }
        model.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact)));
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;
        assert contactToEdit instanceof Client || contactToEdit instanceof Vendor;

        return contactToEdit instanceof Client
                ? createEditedClient((Client) contactToEdit, editContactDescriptor)
                : createEditedVendor((Vendor) contactToEdit, editContactDescriptor);
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Client createEditedClient(Client contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;
        assert contactToEdit instanceof Client;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Phone updatedPhone = editContactDescriptor.getPhone().orElse(contactToEdit.getPhone());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        Address updatedAddress = editContactDescriptor.getAddress().orElse(contactToEdit.getAddress());
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());

        // uneditable fields
        Set<Event> events = new HashSet<>(contactToEdit.getEvents());
        Id id = contactToEdit.getId();

        Client editedClient = new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, id);
        for (Event event : events) {
            editedClient.addEvent(event);
        }
        return editedClient;
    }

    /**
     * Creates and returns a {@code Vendor} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Vendor createEditedVendor(Vendor contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;
        assert contactToEdit instanceof Vendor;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Phone updatedPhone = editContactDescriptor.getPhone().orElse(contactToEdit.getPhone());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        Address updatedAddress = editContactDescriptor.getAddress().orElse(contactToEdit.getAddress());
        Service updatedService = editContactDescriptor instanceof EditVendorDescriptor
                ? ((EditVendorDescriptor) editContactDescriptor).getService().orElse(contactToEdit.getService())
                : contactToEdit.getService();
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());

        // uneditable fields
        Set<Event> events = new HashSet<>(contactToEdit.getEvents());
        Id id = contactToEdit.getId();

        Vendor editedVendor = new Vendor(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedService, updatedTags, id);
        for (Event event : events) {
            editedVendor.addEvent(event);
        }
        return editedVendor;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        EditContactCommand otherEditCommand = (EditContactCommand) other;
        return index.equals(otherEditCommand.index)
                && editContactDescriptor.equals(otherEditCommand.editContactDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editContactDescriptor", editContactDescriptor)
                .toString();
    }

    /**
     * Stores the details of the contact to edit. Each non-empty field value will replace the
     * corresponding field value of the contact. This class contains fields that are common
     * to all types of contacts.
     */
    public static class EditContactDescriptor {
        protected Name name;
        protected Phone phone;
        protected Email email;
        protected Address address;
        protected Set<Tag> tags;
        protected Id contactId;

        public EditContactDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setId(toCopy.contactId);
        }

        /**
         * Returns true if at least one field is edited. ID is not included as an editable field.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public final void setName(Name name) {
            this.name = name;
        }

        public final Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public final void setPhone(Phone phone) {
            this.phone = phone;
        }

        public final Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public final void setEmail(Email email) {
            this.email = email;
        }

        public final Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public final void setAddress(Address address) {
            this.address = address;
        }

        public final Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public final void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public final Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public final void setId(Id contactId) {
            this.contactId = contactId;
        }

        public final Optional<Id> getId() {
            return Optional.ofNullable(contactId);
        }

        /**
         * Creates a copy of this {@code EditContactDescriptor}.
         */
        public EditContactDescriptor copy() {
            return new EditContactDescriptor(this);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            EditContactDescriptor otherEditContactDescriptor = (EditContactDescriptor) other;
            return Objects.equals(name, otherEditContactDescriptor.name)
                    && Objects.equals(phone, otherEditContactDescriptor.phone)
                    && Objects.equals(email, otherEditContactDescriptor.email)
                    && Objects.equals(address, otherEditContactDescriptor.address)
                    && Objects.equals(tags, otherEditContactDescriptor.tags)
                    && Objects.equals(contactId, otherEditContactDescriptor.contactId);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("id", contactId)
                    .toString();
        }
    }

    /**
     * Stores the details of the vendor to edit. This class extends {@code EditContactDescriptor}
     * and also contains fields exclusive to {@code Vendor} (i.e. {@code Vendor.service}).
     */
    public static class EditVendorDescriptor extends EditContactDescriptor {
        private Service service;

        public EditVendorDescriptor() {
            super();
        }

        public EditVendorDescriptor(EditContactDescriptor toCopy) {
            super(toCopy);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return super.isAnyFieldEdited() || service != null;
        }

        public final void setService(Service service) {
            this.service = service;
        }

        public Optional<Service> getService() {
            return Optional.ofNullable(service);
        }

        @Override
        public EditVendorDescriptor copy() {
            EditVendorDescriptor copied = new EditVendorDescriptor(this);
            copied.setService(service);
            return copied;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditVendorDescriptor)) {
                return false;
            }

            EditVendorDescriptor otherEditVendorDescriptor = (EditVendorDescriptor) other;
            return Objects.equals(name, otherEditVendorDescriptor.name)
                    && Objects.equals(phone, otherEditVendorDescriptor.phone)
                    && Objects.equals(email, otherEditVendorDescriptor.email)
                    && Objects.equals(service, otherEditVendorDescriptor.service)
                    && Objects.equals(address, otherEditVendorDescriptor.address)
                    && Objects.equals(tags, otherEditVendorDescriptor.tags)
                    && Objects.equals(contactId, otherEditVendorDescriptor.contactId);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("service", service)
                    .add("tags", tags)
                    .add("id", contactId)
                    .toString();
        }
    }
}
