package seedu.sellsavvy.logic.commands.customercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_SIMILAR_NAME_WARNING;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.commons.util.CollectionUtil;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.customer.Address;
import seedu.sellsavvy.model.customer.Email;
import seedu.sellsavvy.model.customer.Name;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.Phone;
import seedu.sellsavvy.model.tag.Tag;

/**
 * Edits the details of an existing customer in the address book.
 */
public class EditCustomerCommand extends Command {

    public static final String COMMAND_WORD = "editcustomer";
    public static final String COMMAND_ALIAS = "editc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the customer identified "
            + "by the index number used in the displayed customer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CUSTOMER_INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CUSTOMER_SUCCESS = "Edited Customer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in the address book.";
    public static final String MESSAGE_SIMILAR_TAGS_WARNING = "Note: "
            + "This customer has 2 or more similar tags after editing tags, "
            + "verify if this is a mistake.\n";

    private final Index index;
    private final EditCustomerDescriptor editCustomerDescriptor;

    /**
     * @param index of the customer in the filtered customer list to edit
     * @param editCustomerDescriptor details to edit the customer with
     */
    public EditCustomerCommand(Index index, EditCustomerDescriptor editCustomerDescriptor) {
        requireNonNull(index);
        requireNonNull(editCustomerDescriptor);

        this.index = index;
        this.editCustomerDescriptor = new EditCustomerDescriptor(editCustomerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToEdit = lastShownList.get(index.getZeroBased());
        Customer editedCustomer = createEditedCustomer(customerToEdit, editCustomerDescriptor);

        if (!customerToEdit.isSameCustomer(editedCustomer) && model.hasCustomer(editedCustomer)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.setCustomer(customerToEdit, editedCustomer);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        if (model.isSelectedCustomer(customerToEdit)) {
            model.updateSelectedCustomer(editedCustomer);
        }

        String feedbackToUser = editCustomerDescriptor.isNameEdited() && model.hasSimilarCustomer(editedCustomer)
                ? MESSAGE_SIMILAR_NAME_WARNING
                : "";
        feedbackToUser += editCustomerDescriptor.isTagsEdited() && editedCustomer.hasSimilarTags()
                ? MESSAGE_SIMILAR_TAGS_WARNING
                : "";

        return new CommandResult(feedbackToUser
                + String.format(MESSAGE_EDIT_CUSTOMER_SUCCESS, Messages.format(editedCustomer)));
    }

    /**
     * Creates and returns a {@code Customer} with the details of {@code customerToEdit}
     * edited with {@code editCustomerDescriptor}.
     */
    private static Customer createEditedCustomer(Customer customerToEdit, EditCustomerDescriptor editCustomerDescriptor) {
        assert customerToEdit != null;

        Name updatedName = editCustomerDescriptor.getName().orElse(customerToEdit.getName());
        Phone updatedPhone = editCustomerDescriptor.getPhone().orElse(customerToEdit.getPhone());
        Email updatedEmail = editCustomerDescriptor.getEmail().orElse(customerToEdit.getEmail());
        Address updatedAddress = editCustomerDescriptor.getAddress().orElse(customerToEdit.getAddress());
        Set<Tag> updatedTags = editCustomerDescriptor.getTags().orElse(customerToEdit.getTags());
        // edit command does not allow editing customer's order list
        OrderList updatedOrderList = customerToEdit.getOrderList();

        return new Customer(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedOrderList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCustomerCommand)) {
            return false;
        }

        EditCustomerCommand otherEditCustomerCommand = (EditCustomerCommand) other;
        return index.equals(otherEditCustomerCommand.index)
                && editCustomerDescriptor.equals(otherEditCustomerCommand.editCustomerDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCustomerDescriptor", editCustomerDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the customer with. Each non-empty field value will replace the
     * corresponding field value of the customer.
     */
    public static class EditCustomerDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditCustomerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCustomerDescriptor(EditCustomerDescriptor toCopy) {
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

        public boolean isTagsEdited() {
            return tags != null;
        }

        public boolean isNameEdited() {
            return name != null;
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
            if (!(other instanceof EditCustomerDescriptor)) {
                return false;
            }

            EditCustomerDescriptor otherEditCustomerDescriptor = (EditCustomerDescriptor) other;
            return Objects.equals(name, otherEditCustomerDescriptor.name)
                    && Objects.equals(phone, otherEditCustomerDescriptor.phone)
                    && Objects.equals(email, otherEditCustomerDescriptor.email)
                    && Objects.equals(address, otherEditCustomerDescriptor.address)
                    && Objects.equals(tags, otherEditCustomerDescriptor.tags);
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
