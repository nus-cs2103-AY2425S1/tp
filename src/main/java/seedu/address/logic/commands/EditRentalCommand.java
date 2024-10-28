package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
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
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing client in the address book.
 */
public class EditRentalCommand extends Command {
    public static final String COMMAND_WORD = "redit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the specified rental information of the client identified "
            + "by the index number used in the displayed client list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CLIENT_INDEX (must be a positive integer) "
            + "[" + PREFIX_RENTAL_INDEX + " RENTAL_INDEX (must be a positive integer)] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RENTAL_START_DATE + "RENTAL START DATE] "
            + "[" + PREFIX_RENTAL_END_DATE + "RENTAL END DATE] "
            + "[" + PREFIX_RENT_DUE_DATE + "RENT DUE DATE] "
            + "[" + PREFIX_MONTHLY_RENT + "MONTHLY RENT] "
            + "[" + PREFIX_DEPOSIT + "DEPOSIT] "
            + "[" + PREFIX_CUSTOMER_LIST + "CUSTOMER LIST]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RENTAL_INDEX + "1 "
            + PREFIX_ADDRESS + "12 Holland Street "
            + PREFIX_RENTAL_START_DATE + "01/01/2024 "
            + PREFIX_RENTAL_END_DATE + "31/12/2024 "
            + PREFIX_RENT_DUE_DATE + "10 "
            + PREFIX_MONTHLY_RENT + "3450 "
            + PREFIX_DEPOSIT + "9000 "
            + PREFIX_CUSTOMER_LIST + "Steven Ng;David Ng";

    public static final String MESSAGE_EDIT_RENTAL_SUCCESS = "Edited Client's Rental Information: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RENTAL = "This client already exists in the address book.";

    private final Index clientIndex;
    private final Index rentalIndex;
    private final EditRentalDescriptor editRentalDescriptor;

    /**
     * @param clientIndex Index of the client in the filtered client list to edit.
     * @param rentalIndex Index of the rental information for the client specified by clientIndex.
     * @param editRentalDescriptor details to edit the rental information with.
     */
    public EditRentalCommand(Index clientIndex, Index rentalIndex, EditRentalDescriptor editRentalDescriptor) {
        requireNonNull(clientIndex);
        requireNonNull(rentalIndex);
        requireNonNull(editRentalDescriptor);

        this.clientIndex = clientIndex;
        this.rentalIndex = rentalIndex;
        this.editRentalDescriptor = new EditRentalDescriptor(editRentalDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(clientIndex.getZeroBased());

        if (rentalIndex.getZeroBased() >= clientToEdit.getRentalInformation().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RENTAL_DISPLAYED_INDEX);
        }

        Client editedClient = createEditedPerson(rentalIndex, clientToEdit, editRentalDescriptor);

        if (!clientToEdit.isSamePerson(editedClient) && model.hasPerson(editedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_RENTAL);
        }

        model.setPerson(clientToEdit, editedClient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateVisibleRentalInformationList(editedClient.getRentalInformation());
        model.setLastViewedClient(editedClient);

        return new CommandResult(String.format(MESSAGE_EDIT_RENTAL_SUCCESS,
                Messages.formatRentalInformation(editedClient.getRentalInformation().get(rentalIndex.getZeroBased()))));
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code clientToEdit}
     * edited with {@code editRentalDescriptor}.
     */
    private static Client createEditedPerson(Index index, Client clientToEdit,
                                             EditRentalDescriptor editRentalDescriptor) {
        assert clientToEdit != null;

        Name clientName = clientToEdit.getName();
        Phone clientPhone = clientToEdit.getPhone();
        Email clientEmail = clientToEdit.getEmail();
        Set<Tag> clientTags = clientToEdit.getTags();
        List<RentalInformation> updatedRentalInformationList = EditRentalDescriptor.updateRentalInformationList(index,
                clientToEdit.getRentalInformation(), editRentalDescriptor);

        return new Client(clientName, clientPhone, clientEmail, clientTags, updatedRentalInformationList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRentalCommand)) {
            return false;
        }

        EditRentalCommand otherEditRentalCommand = (EditRentalCommand) other;
        return this.clientIndex.equals(otherEditRentalCommand.clientIndex)
                && this.rentalIndex.equals(otherEditRentalCommand.rentalIndex)
                && this.editRentalDescriptor.equals(otherEditRentalCommand.editRentalDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("client index", clientIndex)
                .add("rental index", rentalIndex)
                .add("editRentalDescriptor", editRentalDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the rental information with. Each non-empty field value will replace the
     * corresponding field value of the rental information.
     */
    public static class EditRentalDescriptor {
        private Address address;
        private RentalDate rentalStartDate;
        private RentalDate rentalEndDate;
        private RentDueDate rentDueDate;
        private MonthlyRent monthlyRent;
        private Deposit deposit;
        private CustomerList customerList;

        public EditRentalDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditRentalDescriptor(EditRentalDescriptor toCopy) {
            setAddress(toCopy.address);
            setRentalStartDate(toCopy.rentalStartDate);
            setRentalEndDate(toCopy.rentalEndDate);
            setRentDueDate(toCopy.rentDueDate);
            setMonthlyRent(toCopy.monthlyRent);
            setDeposit(toCopy.deposit);
            setCustomerList(toCopy.customerList);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(address, rentalStartDate, rentalEndDate, rentDueDate, monthlyRent,
                    deposit, customerList);
        }

        /**
         * Updates the list of rental information by replacing an existing rental
         * entry with the edited version specified by {@code EditRentalDescriptor}.
         *
         * @param index Index of the rental information to be updated in the current list.
         * @param currentList The current list of rental information to be updated.
         * @param editRentalDescriptor Contains the new details for the rental information to be updated.
         * @return A new list of rental information with the specified entry updated (if any).
         */
        public static List<RentalInformation> updateRentalInformationList(Index index,
                                                                         List<RentalInformation> currentList,
                                                                         EditRentalDescriptor editRentalDescriptor) {
            List<RentalInformation> newList = new ArrayList<>(currentList);
            RentalInformation targetRentalInformation = newList.get(index.getZeroBased());
            RentalInformation editRentalInformation = editRentalDescriptor.getRentalInformationEquivalent(
                    targetRentalInformation);

            if (!targetRentalInformation.equals(editRentalInformation)) {
                newList.set(index.getZeroBased(), editRentalInformation);
            }

            return newList;
        }

        /**
         * Creates a {@code RentalInformation} object that is equivalent to the target
         * rental information, but with updated fields that are present in this descriptor.
         *
         * @param targetRentalInformation The target rental information to be updated (if any).
         * @return A new {@code RentalInformation} object with updated values based on this
         *         descriptor, retaining values from the target if no updates present in this descriptor.
         */
        private RentalInformation getRentalInformationEquivalent(RentalInformation targetRentalInformation) {
            Address updatedAddress = this.getAddress().orElse(targetRentalInformation.getAddress());
            RentalDate updatedRentalStartDate = this.getRentalStartDate().orElse(
                    targetRentalInformation.getRentalStartDate());
            RentalDate updatedRentalEndDate = this.getRentalEndDate().orElse(
                    targetRentalInformation.getRentalEndDate());
            RentDueDate updatedRentDueDate = this.getRentDueDate().orElse(targetRentalInformation.getRentDueDate());
            MonthlyRent updatedMonthlyRent = this.getMonthlyRent().orElse(targetRentalInformation.getMonthlyRent());
            Deposit updatedDeposit = this.getDeposit().orElse(targetRentalInformation.getDeposit());
            CustomerList updatedCustomerList = this.getCustomerList().orElse(targetRentalInformation.getCustomerList());

            return new RentalInformation(updatedAddress, updatedRentalStartDate, updatedRentalEndDate,
                    updatedRentDueDate, updatedMonthlyRent, updatedDeposit, updatedCustomerList);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setRentalStartDate(RentalDate rentalStartDate) {
            this.rentalStartDate = rentalStartDate;
        }

        public Optional<RentalDate> getRentalStartDate() {
            return Optional.ofNullable(rentalStartDate);
        }

        public void setRentalEndDate(RentalDate rentalEndDate) {
            this.rentalEndDate = rentalEndDate;
        }

        public Optional<RentalDate> getRentalEndDate() {
            return Optional.ofNullable(rentalEndDate);
        }

        public void setRentDueDate(RentDueDate rentDueDate) {
            this.rentDueDate = rentDueDate;
        }

        public Optional<RentDueDate> getRentDueDate() {
            return Optional.ofNullable(rentDueDate);
        }

        public void setMonthlyRent(MonthlyRent monthlyRent) {
            this.monthlyRent = monthlyRent;
        }

        public Optional<MonthlyRent> getMonthlyRent() {
            return Optional.ofNullable(monthlyRent);
        }

        public void setDeposit(Deposit deposit) {
            this.deposit = deposit;
        }

        public Optional<Deposit> getDeposit() {
            return Optional.ofNullable(deposit);
        }

        public void setCustomerList(CustomerList customerList) {
            this.customerList = customerList;
        }

        public Optional<CustomerList> getCustomerList() {
            return Optional.ofNullable(customerList);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRentalDescriptor)) {
                return false;
            }

            EditRentalDescriptor otherEditRentalDescriptor = (EditRentalDescriptor) other;
            return Objects.equals(this.address, otherEditRentalDescriptor.address)
                    && Objects.equals(this.rentalStartDate, otherEditRentalDescriptor.rentalStartDate)
                    && Objects.equals(this.rentalEndDate, otherEditRentalDescriptor.rentalEndDate)
                    && Objects.equals(this.rentDueDate, otherEditRentalDescriptor.rentDueDate)
                    && Objects.equals(this.monthlyRent, otherEditRentalDescriptor.monthlyRent)
                    && Objects.equals(this.deposit, otherEditRentalDescriptor.deposit)
                    && Objects.equals(this.customerList, otherEditRentalDescriptor.customerList);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("address", address)
                    .add("rental start date", rentalStartDate)
                    .add("rental end date", rentalEndDate)
                    .add("rent due date", rentDueDate)
                    .add("monthly rent", monthlyRent)
                    .add("deposit", deposit)
                    .add("customer list", customerList)
                    .toString();
        }
    }
}
