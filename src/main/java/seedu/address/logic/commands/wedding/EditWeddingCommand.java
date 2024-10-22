package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

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
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Edits the details of an existing wedding in the address book.
 */
public class EditWeddingCommand extends Command {

    public static final String COMMAND_WORD = "edit-wedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the wedding identified "
            + "by the index number used in the displayed wedding list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters (optional parameters in square brackets): INDEX (must be a positive integer) "
            + "[" + PREFIX_WEDDING + "WEDDING]"
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WEDDING + "Mr and Mrs John Tan "
            + PREFIX_ADDRESS + "12 College Ave West";

    public static final String MESSAGE_EDIT_WEDDING_SUCCESS = "Edited Wedding: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book.";

    private final Index index;
    private final EditWeddingDescriptor editWeddingDescriptor;

    /**
     * @param index of the wedding in the filtered wedding list to edit
     * @param editWeddingDescriptor details to edit the wedding with
     */
    public EditWeddingCommand(Index index, EditWeddingDescriptor editWeddingDescriptor) {
        requireNonNull(index);
        requireNonNull(editWeddingDescriptor);

        this.index = index;
        this.editWeddingDescriptor = new EditWeddingDescriptor(editWeddingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }


        Wedding weddingToEdit = lastShownList.get(index.getZeroBased());
        Wedding editedWedding = createEditedWedding(weddingToEdit, editWeddingDescriptor);

        if (!weddingToEdit.isSameWedding(editedWedding) && model.hasWedding(editedWedding)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.setWedding(weddingToEdit, editedWedding);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding)));
    }

    /**
     * Creates and returns a {@code Wedding} with the details of {@code weddingToEdit}
     * edited with {@code editWeddingDescriptor}.
     */
    private static Wedding createEditedWedding(Wedding weddingToEdit, EditWeddingDescriptor editWeddingDescriptor) {
        assert weddingToEdit != null;

        WeddingName updatedWeddingName = editWeddingDescriptor.getWeddingName().orElse(weddingToEdit.getWeddingName());
        Address updatedAddress = editWeddingDescriptor.getAddress().orElse(weddingToEdit.getAddress());

        return new Wedding(updatedWeddingName); //, updatedAddress);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditWeddingCommand)) {
            return false;
        }

        EditWeddingCommand otherEditCommand = (EditWeddingCommand) other;
        return index.equals(otherEditCommand.index)
                && editWeddingDescriptor.equals(otherEditCommand.editWeddingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editWeddingDescriptor", editWeddingDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the wedding with. Each non-empty field value will replace the
     * corresponding field value of the wedding.
     */
    public static class EditWeddingDescriptor {
        private WeddingName weddingName;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Wedding> weddings;

        public EditWeddingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditWeddingDescriptor(EditWeddingDescriptor toCopy) {
            setName(toCopy.weddingName);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setWeddings(toCopy.weddings);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(weddingName, phone, email, address, tags, weddings);
        }

        public void setName(WeddingName weddingName) {
            this.weddingName = weddingName;
        }

        public Optional<WeddingName> getWeddingName() {
            return Optional.ofNullable(weddingName);
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

        /**
         * Sets {@code weddings} to this object's {@code weddings}.
         * A defensive copy of {@code weddings} is used internally.
         */
        public void setWeddings(Set<Wedding> weddings) {
            this.weddings = (weddings != null) ? new HashSet<>(weddings) : null;
        }

        /**
         * Returns an unmodifiable weddings set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code weddings} is null.
         */
        public Optional<Set<Wedding>> getWeddings() {
            return (weddings != null) ? Optional.of(Collections.unmodifiableSet(weddings)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditWeddingDescriptor)) {
                return false;
            }

            EditWeddingDescriptor otherEditWeddingDescriptor = (EditWeddingDescriptor) other;
            return Objects.equals(weddingName, otherEditWeddingDescriptor.weddingName)
                    && Objects.equals(phone, otherEditWeddingDescriptor.phone)
                    && Objects.equals(email, otherEditWeddingDescriptor.email)
                    && Objects.equals(address, otherEditWeddingDescriptor.address)
                    && Objects.equals(tags, otherEditWeddingDescriptor.tags)
                    && Objects.equals(weddings, otherEditWeddingDescriptor.weddings);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("weddingName", weddingName)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("weddings", weddings)
                    .toString();
        }
    }
}
