package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Adds a rental information (for a client) to the address book.
 */
public class AddRentalCommand extends Command {
    public static final String COMMAND_WORD = "radd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a rental information to a client. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RENTAL_START_DATE + "RENTAL START DATE] "
            + "[" + PREFIX_RENTAL_END_DATE + "RENTAL END DATE] "
            + "[" + PREFIX_RENT_DUE_DATE + "RENT DUE DATE] "
            + "[" + PREFIX_MONTHLY_RENT + "MONTHLY RENT] "
            + "[" + PREFIX_DEPOSIT + "DEPOSIT] "
            + "[" + PREFIX_CUSTOMER_LIST + "CUSTOMER LIST] "
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADDRESS + "BLK 1 Bishan "
            + PREFIX_RENTAL_START_DATE + "01/01/2024 "
            + PREFIX_RENTAL_END_DATE + "31/12/2024 "
            + PREFIX_RENT_DUE_DATE + "15 "
            + PREFIX_MONTHLY_RENT + "2700 "
            + PREFIX_DEPOSIT + "8100 "
            + PREFIX_CUSTOMER_LIST + "Steven;David ";
    public static final String MESSAGE_SUCCESS = "New rental information is added to the client: %1$s";
    public static final String MESSAGE_DUPLICATE_RENTAL_INFORMATION =
            "This rental information already exists in the address book";
    public static final String MESSAGE_REQUIRE_ADDRESS = "Address field (" + PREFIX_ADDRESS + ") must be provided";

    private final Index index;
    private final RentalInformation toAdd;

    /**
     * Creates an AddRentalCommand to add the specified {@code RentalInformation} (for a {@code Client})
     */
    public AddRentalCommand(Index index, RentalInformation rentalInformation) {
        requireNonNull(index);
        requireNonNull(rentalInformation);

        this.index = index;
        toAdd = rentalInformation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Client> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        if (model.hasRentalInformation(clientToEdit, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RENTAL_INFORMATION);
        }

        List<RentalInformation> updatedRentalInformationList = new ArrayList<>(clientToEdit.getRentalInformation());
        updatedRentalInformationList.add(toAdd);
        Client updatedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getTags(), updatedRentalInformationList);

        model.setPerson(clientToEdit, updatedClient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateVisibleRentalInformationList(updatedRentalInformationList);
        model.setLastViewedClient(updatedClient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatRentalInformation(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRentalCommand)) {
            return false;
        }

        AddRentalCommand otherAddRentalCommand = (AddRentalCommand) other;
        return this.toAdd.equals(otherAddRentalCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }

    /**
     * Stores the details to edit the rental information with. Each non-empty field value will replace the
     * corresponding field value of the rental information.
     */
    public static class AddRentalDescriptor {
        private Address address;
        private RentalDate rentalStartDate;
        private RentalDate rentalEndDate;
        private RentDueDate rentDueDate;
        private MonthlyRent monthlyRent;
        private Deposit deposit;
        private CustomerList customerList;

        public AddRentalDescriptor() {}

        /**
         * Copy constructor.
         */
        public AddRentalDescriptor(AddRentalDescriptor toCopy) {
            setAddress(toCopy.address);
            setRentalStartDate(toCopy.rentalStartDate);
            setRentalEndDate(toCopy.rentalEndDate);
            setRentDueDate(toCopy.rentDueDate);
            setMonthlyRent(toCopy.monthlyRent);
            setDeposit(toCopy.deposit);
            setCustomerList(toCopy.customerList);
        }

        /**
         * Returns true if address field is edited.
         */
        public boolean isAddressFieldEdited() {
            return address != null;
        }

        /**
         * Creates a {@code RentalInformation} object using this descriptor.
         *
         * @return A new {@code RentalInformation} object with the values based on this descriptor.
         */
        public RentalInformation getRentalInformationEquivalentWithNull() {
            Address newAddress = address;
            RentalDate newRentalStartDate = rentalStartDate == null ? new RentalDate() : rentalStartDate;
            RentalDate newRentalEndDate = rentalEndDate == null ? new RentalDate() : rentalEndDate;
            RentDueDate newRentDueDate = rentDueDate == null ? new RentDueDate() : rentDueDate;
            MonthlyRent newMonthlyRent = monthlyRent == null ? new MonthlyRent() : monthlyRent;
            Deposit newDeposit = deposit == null ? new Deposit() : deposit;
            CustomerList newCustomerList = customerList == null ? new CustomerList() : customerList;

            return new RentalInformation(newAddress, newRentalStartDate, newRentalEndDate, newRentDueDate,
                    newMonthlyRent, newDeposit, newCustomerList);
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
            if (!(other instanceof AddRentalDescriptor)) {
                return false;
            }

            AddRentalDescriptor otherAddRentalDescriptor = (AddRentalDescriptor) other;
            return Objects.equals(this.address, otherAddRentalDescriptor.address)
                    && Objects.equals(this.rentalStartDate, otherAddRentalDescriptor.rentalStartDate)
                    && Objects.equals(this.rentalEndDate, otherAddRentalDescriptor.rentalEndDate)
                    && Objects.equals(this.rentDueDate, otherAddRentalDescriptor.rentDueDate)
                    && Objects.equals(this.monthlyRent, otherAddRentalDescriptor.monthlyRent)
                    && Objects.equals(this.deposit, otherAddRentalDescriptor.deposit)
                    && Objects.equals(this.customerList, otherAddRentalDescriptor.customerList);
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

