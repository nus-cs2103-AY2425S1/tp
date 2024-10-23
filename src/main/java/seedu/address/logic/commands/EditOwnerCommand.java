package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;

/**
 * Edits the details of an existing owner in the address book.
 */
public class EditOwnerCommand extends EditCommand<Owner> {
    public static final String COMMAND_WORD = "edit o";

    public static final String MESSAGE_EDIT_OWNER_SUCCESS = "Edited Owner: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_OWNER = "This owner already exists in PawPatrol.";

    private final EditOwnerDescriptor editOwnerDescriptor;

    /**
     * Constructs an EditOwnerCommand to edit the specified owner.
     *
     * @param index Index of the owner in the filtered owner list to edit.
     * @param editOwnerDescriptor Details to edit the owner with.
     */
    public EditOwnerCommand(Index index, EditOwnerDescriptor editOwnerDescriptor) {
        super(index);
        requireNonNull(editOwnerDescriptor);

        this.editOwnerDescriptor = new EditOwnerDescriptor(editOwnerDescriptor);
    }

    /**
     * Executes the edit owner command.
     *
     * @param model The model in which the command should operate.
     * @return The result of the command execution.
     * @throws CommandException If the index is invalid or the edited owner is a duplicate.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Owner> lastShownList = model.getFilteredOwnerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX);
        }

        Owner ownerToEdit = lastShownList.get(index.getZeroBased());
        Owner editedOwner = createEditedOwner(ownerToEdit, editOwnerDescriptor);

        if (!ownerToEdit.isSameOwner(editedOwner) && model.hasOwner(editedOwner)) {
            throw new CommandException(MESSAGE_DUPLICATE_OWNER);
        }

        model.setOwner(ownerToEdit, editedOwner);
        model.updateFilteredOwnerList(Model.PREDICATE_SHOW_ALL_OWNERS);
        return new CommandResult(String.format(MESSAGE_EDIT_OWNER_SUCCESS, Messages.format(editedOwner)));
    }

    /**
     * Creates and returns a {@code Owner} with the details of {@code ownerToEdit}
     * edited with {@code editOwnerDescriptor}.
     */
    private static Owner createEditedOwner(Owner ownerToEdit, EditOwnerDescriptor editOwnerDescriptor) {
        assert ownerToEdit != null;

        IdentificationCardNumber updatedIdentificationNumber = editOwnerDescriptor.getIdentificationNumber()
            .orElse(ownerToEdit.getIdentificationNumber());
        Name updatedName = editOwnerDescriptor.getName().orElse(ownerToEdit.getName());
        Phone updatedPhone = editOwnerDescriptor.getPhone().orElse(ownerToEdit.getPhone());
        Email updatedEmail = editOwnerDescriptor.getEmail().orElse(ownerToEdit.getEmail());
        Address updatedAddress = editOwnerDescriptor.getAddress().orElse(ownerToEdit.getAddress());

        return new Owner(updatedIdentificationNumber, updatedName, updatedPhone, updatedEmail, updatedAddress);
    }

    /**
     * Executes the edit owner command.
     *
     * @return The result of the command execution.
     * @throws CommandException If the index is invalid or the edited owner is a duplicate.
     */
    public static class EditOwnerDescriptor {
        private IdentificationCardNumber identificationNumber;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;

        public EditOwnerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOwnerDescriptor(EditOwnerDescriptor toCopy) {
            setIdentificationNumber(toCopy.identificationNumber);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address);
        }

        public void setIdentificationNumber(IdentificationCardNumber identificationNumber) {
            this.identificationNumber = identificationNumber;
        }

        public Optional<IdentificationCardNumber> getIdentificationNumber() {
            return Optional.ofNullable(identificationNumber);
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

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOwnerDescriptor)) {
                return false;
            }

            EditOwnerDescriptor otherEditOwnerDescriptor = (EditOwnerDescriptor) other;
            return Objects.equals(name, otherEditOwnerDescriptor.name)
                && Objects.equals(phone, otherEditOwnerDescriptor.phone)
                && Objects.equals(email, otherEditOwnerDescriptor.email)
                && Objects.equals(address, otherEditOwnerDescriptor.address);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .toString();
        }
    }

}
