package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPEND_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * Edits the details of an existing client in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE_OPTIONAL_PARAMETERS = "Optional Parameters: "
            + "[" + PREFIX_NAME + " NAME] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_ADDRESS + " ADDRESS] "
            + "[" + PREFIX_JOB + " JOB] "
            + "[" + PREFIX_INCOME + " INCOME] "
            + "[" + PREFIX_TIER + " TIER] "
            + "[" + PREFIX_NEW_REMARK + " NEW REMARK] "
            + "[" + PREFIX_APPEND_REMARK + " ADD-ON TO EXISTING REMARK] "
            + "[" + PREFIX_STATUS + " STATUS] \n"
            + "Example Usage: '" + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com'";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the client identified "
            + "by the index number used in the displayed client list. "
            + "Existing values will be overwritten by the input values. Any fields unspecified will not be modified.\n"
            + "Required Parameters: INDEX (must be a positive integer)\n"
            + MESSAGE_USAGE_OPTIONAL_PARAMETERS;

    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited Client: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in the address book.";

    private final Index index;
    private final EditClientDescriptor editClientDescriptor;

    /**
     * @param index of the client in the filtered client list to edit
     * @param editClientDescriptor details to edit the client with
     */
    public EditCommand(Index index, EditClientDescriptor editClientDescriptor) {
        requireNonNull(index);
        requireNonNull(editClientDescriptor);

        this.index = index;
        this.editClientDescriptor = new EditClientDescriptor(editClientDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        Client editedClient = createEditedClient(clientToEdit, editClientDescriptor);

        if (!clientToEdit.isSameClient(editedClient) && model.hasClient(editedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(model.getCurrentClientFilter());
        return new CommandResult(String.format(MESSAGE_EDIT_CLIENT_SUCCESS, Messages.format(editedClient)));
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code clientToEdit}
     * edited with {@code editClientDescriptor}.
     */
    private static Client createEditedClient(Client clientToEdit, EditClientDescriptor editClientDescriptor) {
        assert clientToEdit != null;

        Name updatedName = editClientDescriptor.getName().orElse(clientToEdit.getName());
        Phone updatedPhone = editClientDescriptor.getPhone().orElse(clientToEdit.getPhone());
        Email updatedEmail = editClientDescriptor.getEmail().orElse(clientToEdit.getEmail());
        Address updatedAddress = editClientDescriptor.getAddress().orElse(clientToEdit.getAddress());
        Job updatedJob = editClientDescriptor.getJob().orElse(clientToEdit.getJob());
        Income updatedIncome = editClientDescriptor.getIncome().orElse(clientToEdit.getIncome());
        Tier updatedTier = editClientDescriptor.getTier().orElse(clientToEdit.getTier());
        Remark updatedRemark;
        if (editClientDescriptor.getAppendedRemark().isPresent()) {
            updatedRemark = Remark.combineRemarks(clientToEdit.getRemark(),
                    editClientDescriptor.getAppendedRemark().get());
        } else {
            updatedRemark = editClientDescriptor.getNewRemark().orElse(clientToEdit.getRemark());
        }
        Status updatedStatus = editClientDescriptor.getStatus().orElse(clientToEdit.getStatus());
        return new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedJob, updatedIncome,
                updatedTier, updatedRemark, updatedStatus);
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
                && editClientDescriptor.equals(otherEditCommand.editClientDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editClientDescriptor", editClientDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the client with. Each non-empty field value will replace the
     * corresponding field value of the client.
     */
    public static class EditClientDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Job job;
        private Income income;
        private Tier tier;
        private Remark remark;
        private Remark appendedRemark;
        private Status status;
        public EditClientDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClientDescriptor(EditClientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setJob(toCopy.job);
            setIncome(toCopy.income);
            setTier(toCopy.tier);
            setNewRemark(toCopy.remark);
            setAppendedRemark(toCopy.appendedRemark);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, job, income, tier, remark, appendedRemark,
                    status);
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

        public void setJob(Job job) {
            this.job = job;
        }

        public Optional<Job> getJob() {
            return Optional.ofNullable(job);
        }

        public void setIncome(Income income) {
            this.income = income;
        }
        public Optional<Income> getIncome() {
            return Optional.ofNullable(income);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTier(Tier tier) {
            this.tier = tier;
        }

        /**
         * Returns an unmodifiable tier, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Tier> getTier() {
            return (tier != null) ? Optional.of(tier) : Optional.empty();
        }

        public void setNewRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getNewRemark() {
            return Optional.ofNullable(remark);
        }

        public void setAppendedRemark(Remark remark) {
            this.appendedRemark = remark;
        }

        public Optional<Remark> getAppendedRemark() {
            return Optional.ofNullable(appendedRemark);
        }
        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(this.status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditClientDescriptor)) {
                return false;
            }

            EditClientDescriptor otherEditClientDescriptor = (EditClientDescriptor) other;
            return Objects.equals(name, otherEditClientDescriptor.name)
                    && Objects.equals(phone, otherEditClientDescriptor.phone)
                    && Objects.equals(email, otherEditClientDescriptor.email)
                    && Objects.equals(address, otherEditClientDescriptor.address)
                    && Objects.equals(job, otherEditClientDescriptor.job)
                    && Objects.equals(income, otherEditClientDescriptor.income)
                    && Objects.equals(tier, otherEditClientDescriptor.tier)
                    && Objects.equals(remark, otherEditClientDescriptor.remark)
                    && Objects.equals(appendedRemark, otherEditClientDescriptor.appendedRemark)
                    && Objects.equals(status, otherEditClientDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("job", job)
                    .add("income", income)
                    .add("tier", tier)
                    .add("remark", remark)
                    .add("status", status)
                    .toString();
        }
    }
}
