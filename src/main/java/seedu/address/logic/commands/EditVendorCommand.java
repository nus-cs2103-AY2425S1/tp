package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENDORS;

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
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Description;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.model.vendor.Vendor;

/**
 * Edits a vendor identified using it's displayed index from the address book.
 */
public class EditVendorCommand extends EditCommand {

    public static final String MESSAGE_EDIT_VENDOR_SUCCESS = "Edited Vendor: %1$s";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This vendor already exists in the address book.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the vendor identified "
            + "by the index number used in the displayed vendor list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_VENDOR + "INDEX" + " <other vendor parameters>\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_VENDOR + "1 "
            + PREFIX_NAME + "Adam's Bakery "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_DESCRIPTION + "Pastries and cakes, bake in a day "
            + PREFIX_TAG + "pastry "
            + PREFIX_TAG + "fast";

    private final EditVendorDescriptor editVendorDescriptor;

    /**
     * @param index                of the vendor in the filtered vendor list to edit
     * @param editVendorDescriptor details to edit the vendor with
     */
    public EditVendorCommand(Index index, EditVendorDescriptor editVendorDescriptor) {
        super(index);

        requireNonNull(editVendorDescriptor);
        this.editVendorDescriptor = new EditVendorDescriptor(editVendorDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Vendor> lastShownList = model.getFilteredVendorList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        Vendor vendorToEdit = lastShownList.get(index.getZeroBased());
        Vendor editedVendor = createEditedVendor(vendorToEdit, editVendorDescriptor);

        if (!vendorToEdit.isSameVendor(editedVendor) && model.hasVendor(editedVendor)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }

        model.setVendor(vendorToEdit, editedVendor);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor)));
    }

    /**
     * Creates and returns a {@code Vendor} with the details of {@code vendorToEdit}
     * edited with {@code editVendorDescriptor}.
     */
    private static Vendor createEditedVendor(Vendor vendorToEdit, EditVendorDescriptor editVendorDescriptor) {
        assert vendorToEdit != null;

        Name updatedName = editVendorDescriptor.getName().orElse(vendorToEdit.getName());
        Phone updatedPhone = editVendorDescriptor.getPhone().orElse(vendorToEdit.getPhone());
        Description updatedDescription = editVendorDescriptor.getDescription().orElse(vendorToEdit.getDescription());
        Set<Tag> updatedTags = editVendorDescriptor.getTags().orElse(vendorToEdit.getTags());

        return new Vendor(updatedName, updatedPhone, updatedDescription, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditVendorCommand)) {
            return false;
        }

        EditVendorCommand otherEditCommand = (EditVendorCommand) other;
        return index.equals(otherEditCommand.index)
                && editVendorDescriptor.equals(otherEditCommand.editVendorDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editVendorDescriptor", editVendorDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the vendor with. Each non-empty field value will
     * replace the
     * corresponding field value of the vendor.
     */
    public static class EditVendorDescriptor {
        private Name name;
        private Phone phone;
        private Description description;
        private Set<Tag> tags;

        public EditVendorDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditVendorDescriptor(EditVendorDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, description, tags);
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

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException}
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
            if (!(other instanceof EditVendorDescriptor)) {
                return false;
            }

            EditVendorDescriptor otherEditVendorDescriptor = (EditVendorDescriptor) other;
            return Objects.equals(name, otherEditVendorDescriptor.name)
                    && Objects.equals(phone, otherEditVendorDescriptor.phone)
                    && Objects.equals(description, otherEditVendorDescriptor.description)
                    && Objects.equals(tags, otherEditVendorDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("description", description)
                    .add("tags", tags)
                    .toString();
        }
    }
}
